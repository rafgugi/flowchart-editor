package command;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import diagram.element.Line;
import diagram.element.TwoDimensional;
import diagram.flowchart.*;
import diagram.flowchart.Process;
import diagram.flowchart.type.*;
import diagram.pad.*;
import exception.GenerateCodeException;
import interfaces.FlowChartElement;
import interfaces.ICommand;
import interfaces.IElement;
import main.Main;
import widget.window.MainWindow;

/**
 * This command purpose is generating source code from flowchart. There are
 * some algorithms to process this command:
 *     1. Validate the flowchart
 *     2. Generate code for each flowchart node
 *     3. Convert coded flowchart to PAD
 *     4. Convert PAD to raw source code
 *     5. Convert raw source code to source code
 */
public class GenerateCodeCommand implements ICommand {

	private Stack<Judgment> stackOfJudgment = new Stack<>();
	private int codeCounter;
	private int doWhileCounter;

	@Override
	public void execute() {
		ValidateDiagramCommand validator = new ValidateDiagramCommand();
		validator.execute();
		if (validator.isError()) {
			return;
		}

		List<IElement> elements;
		elements = MainWindow.getInstance().getEditor().getActiveSubEditor().getElements();

		/* Get start element */
		IElement currentElem = null;
		for (IElement e : elements) {
			if (e instanceof FlowChartElement) {
				((FlowChartElement) e).prepare();
			}
			if (e instanceof Terminator) {
				if (((Terminator) e).getText().equals(Terminator.START)) {
					currentElem = e;
				}
			}
		}

		/* Begin code flowchartElement, send father, his son, and new code */
		boolean status = false;
		String blockcode = "";
		try {
			/* "Code Algorithm" Method */
			Terminator father = (Terminator) currentElem;
			FlowChartElement son = (FlowChartElement) father.getFlow().getDstElement();
			NodeCode currCode = new NodeCode();
			father.setNodeCode(currCode);
			codeCounter = 0;
			doWhileCounter = 0;
			codeAlgorithm(father, son, father.getNodeCode().createSibling());

			/* "Convert to PAD" method */
			BlockContainer fatherBlock = new BlockContainer();
			convertToPAD(currCode, fatherBlock);

			/* Convert PAD to source code */
			blockcode = fatherBlock.generate();
			Main.log("Start");
			Main.log(blockcode);
			Main.log("End");

			status = true;
		} catch (GenerateCodeException ex) {
			Main.log("Error: ");
			Main.log(ex.getMessage());
			MainWindow.getInstance().setStatus(ex.getMessage());
		} finally {
			if (status) {
				MainWindow.getInstance().setStatus("Finish generating code.");

				FileDialog dialog = new FileDialog(MainWindow.getInstance(), SWT.SAVE);
				dialog.setFilterNames(new String[] { "*.txt", "*.*" });
				dialog.setFileName("output.txt");
				String filepath = dialog.open();
				if (filepath == null) {
					throw new GenerateCodeException("Save canceled.");
				}
				Main.log("Save to " + filepath);

				PrintWriter writer;
				try {
					writer = new PrintWriter(filepath, "UTF-8");
					writer.println(blockcode);
					writer.close();
				} catch (FileNotFoundException | UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Generate a code for every node according to the coding strategy.
	 * 
	 * @param fatherNode
	 * @param currentNode
	 * @param currentCode
	 */
	public void codeAlgorithm(FlowChartElement father, FlowChartElement currElem, NodeCode currCode) {
		Main.log("codeAlgorithm (" + (codeCounter++) + ")");
		Main.log("\t" + father);
		Main.log("\t" + currElem);
		Main.log("\t" + currCode);

		if (currElem instanceof Terminator) {
			returnCode();
			return; // exit point of recursion
		}
		if (currElem instanceof Process) {
			Process currNode = (Process) currElem;
			/* Generate the code for CurrentNode */
			NodeCode codeOfSon = currCode.createSibling();
			if (!currNode.hasBeenTraversed() || currNode.getDoWhileCounter() < doWhileCounter) { //!!!
				String again = "";
				if (currNode.getDoWhileCounter() < doWhileCounter) {
					currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1); //!!!
					again = " again";
				}
				Main.log("\tProcess hasn't been traversed" + again);
				currNode.setNodeCode(currCode);
				currNode.traverse();
				FlowChartElement son = (FlowChartElement) currNode.getFlow().getDstElement();
				codeAlgorithm(currNode, son, codeOfSon);
			} else {
				/*
				 * Father is recognized as a do-while structure, mark the
				 * Judgment and link it with his Father, include do-while and
				 * nested do-while
				 */
				if (father instanceof Judgment && father.getType() == null || currNode.getDoWhileCounter() < doWhileCounter) {
					if (currNode.getDoWhileCounter() < doWhileCounter) {
						currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1); //!!!
					}
					Main.log("\tProcess father is do-while.");
					NodeCode thisCode = currNode.getNodeCode();
					father.setNodeCode(thisCode);
					father.setType(DoWhileType.get());
					((Judgment) father).setDoWhileNode(currNode);
					// currNode.setNodeCode(thisCode.createChild());
					doWhileCounter++;
					father.setDoWhileCounter(father.getDoWhileCounter() + 1);
					thisCode.resetChildren();
					codeAlgorithm(father, currNode, thisCode.createChild());
					doWhileCounter--;
				}
			}
		}
		if (currElem instanceof Judgment) {
			Judgment currNode = (Judgment) currElem;
			if (!currNode.hasBeenTraversed() || currNode.getDoWhileCounter() < doWhileCounter) { //!!!
				String again = "";
				if (currNode.getDoWhileCounter() < doWhileCounter) {
					currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1); //!!!
					again = " again";
				}
				if (currNode.getDirectConvergence() == null) {
					Main.log("\tPush into stackOfJudgment, to find convergence." + again);
					stackOfJudgment.push(currNode);
				}
				Main.log("\tJudgment hasn't been traversed" + again);
				currNode.traverse();
				currNode.setNodeCode(currCode);

				/* Get direct convergence and process other children */
				ArrayList<Convergence> convergenceSons = new ArrayList<>();
				for (TwoDimensional son : currNode.getChildren()) {
					if (son instanceof Convergence) {
						convergenceSons.add((Convergence) son);
						continue;
					}
					NodeCode sonCode = currCode.createChild();
					Main.log("\tGo to judgment's child.");
					codeAlgorithm(currNode, (FlowChartElement) son, sonCode);
				}
				for (Convergence convergenceson : convergenceSons) {
					Main.log("\tGo to judgment's convergence direct child.");
					codeAlgorithm(currNode, convergenceson, null);
				}

				/* loop structures have been recognized, the left is selections */
				if (currNode.getType() == null) {
					/*
					 * Kan sebelumnya udah ngakses children nya. Kalau sampe udah
					 * keluar dari children dan node ini belum ketahuan tipenya,
					 * berarti ini selection.
					 */
					currNode.setType(SelectionType.get());
				}
				/* Continue to process the nodes behind Convergence. */
				Convergence conv = currNode.getDirectConvergence();
				if (conv == null) {
					throw new GenerateCodeException("Direct convergence is null.");
				}
				NodeCode sonCode = conv.getNodeCode().createSibling();
				FlowChartElement sonNode = (FlowChartElement) conv.getFlow().getDstElement();
				Main.log("\tGo to judgment's direct convergence.");
				codeAlgorithm(conv, sonNode, sonCode);
			}
			else { // been traversed.
				if (currNode.getType() == null) {
					currNode.setType(WhileType.get());
				}
				else { // and been recognized
					if (father instanceof Judgment && father.getType() == null
							|| currNode.getDoWhileCounter() < doWhileCounter) {
						if (currNode.getDoWhileCounter() < doWhileCounter) {
							currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1); //!!!
						}
						Main.log("\tJudgment father is do-while.");
						NodeCode thisCode = currNode.getNodeCode();
						father.setNodeCode(thisCode);
						father.setType(DoWhileType.get());
						((Judgment) father).setDoWhileNode(currNode);
						// currNode.setNodeCode(thisCode.createChild());
						doWhileCounter++;
						father.setDoWhileCounter(father.getDoWhileCounter() + 1);
						thisCode.resetChildren();
						codeAlgorithm(father, currNode, thisCode.createChild());
						doWhileCounter--;
					}
				}
			}
		}
		if (currElem instanceof Convergence) {
			Convergence currNode = (Convergence) currElem;
			/* match a judgment node and a convergence node */
			if (!currNode.hasBeenTraversed() || currNode.getDoWhileCounter() < doWhileCounter) { //!!!
				String again = "";
				if (currNode.getDoWhileCounter() < doWhileCounter) {
					currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1); //!!!
					again = " again";
				}
				Main.log("\tConvergence hasn't been traversed" + again);
				currNode.traverse();
				/*
				 * as a judgment and a convergence is exist geminate, so the top
				 * judgment in stack must be able to match the current
				 * convergence
				 */
				if (currNode.getDirectJudgment() == null) {
					Main.log("\tConnect convergence with judgment.");
					if (stackOfJudgment.isEmpty()) {
						throw new GenerateCodeException("Empty stackOfJudgment.");
					}
					Judgment tempJudgment = stackOfJudgment.pop();
					currNode.setDirectJudgment(tempJudgment);
					tempJudgment.setDirectConvergence(currNode);
				}
				/* Set this convergence node code from direct judgment */
				currNode.setNodeCode(currNode.getDirectJudgment().getNodeCode());
			} else {
				Main.log("\tConvergence has been traversed.");
				currNode.setNodeCode(currNode.getDirectJudgment().getNodeCode());
				returnCode();
				return;
			}
		}
		returnCode();
	}

	public void returnCode() {
		Main.log("return " + --codeCounter);
	}

	/**
	 * Convert the coded flowchart into PAD.
	 * 
	 * @param currCode
	 * @param fatherBlock
	 */
	public void convertToPAD(NodeCode currCode, BlockContainer fatherBlock) {
		if (currCode == null) {
			return;
		}
		FlowChartElement currElem = currCode.getElement();
		if (currElem == null) {
			return;
		}
		Main.log("ConvertToPAD enter " + currElem);
		if (currElem.getType() instanceof ProcessType) {
			Sequence element = new Sequence();
			element.setText(currElem.getText());
			fatherBlock.addElement(element);
		}
		if (currElem.getType() instanceof SelectionType) {
			Selection element = new Selection();
			element.setText(currElem.getText());
			
			/* Create new layer, for each selection children */
			Main.log("Go to each child flow which is not convergence.");
			for (FlowLine flow : ((Judgment) currElem).getFlows()) {
				FlowChartElement nextFlow = (FlowChartElement) flow.getDstElement();
				if (nextFlow instanceof Convergence) {
					continue;
				}
				BlockContainer subBlock = new BlockContainer();
				if (flow.getText().equals(Line.YES)) {
					element.setYesChild(subBlock);
				} else if (flow.getText().equals(Line.NO)) {
					element.setNoChild(subBlock);
				} else {
					throw new GenerateCodeException("Judgment flow type isn't defined.");
				}
				convertToPAD(nextFlow.getNodeCode(), subBlock);
			}
			fatherBlock.addElement(element);
		}
		if (currElem.getType() instanceof LoopType) {
			Loop element;
			if (currElem.getType() instanceof WhileType) {
				element = new While();
			} else {
				element = new DoWhile();
			}
			element.setText(currElem.getText());
			BlockContainer subContainer = new BlockContainer();
			NodeCode childCode = currCode.getChildren().get(0);
			element.setChild(subContainer);
			fatherBlock.addElement(element);
			convertToPAD(childCode, subContainer);
		}
		/* Go to next code from the same layer */
		currCode = currCode.getSibling();
		convertToPAD(currCode, fatherBlock);
	}

}
