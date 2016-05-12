package command;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import diagram.element.TwoDimensional;
import diagram.flowchart.*;
import diagram.flowchart.Process;
import diagram.flowchart.type.*;
import exception.GenerateCodeException;
import interfaces.FlowChartElement;
import interfaces.ICommand;
import interfaces.IElement;
import main.Main;
import widget.window.MainWindow;

public class GenerateCodeCommand implements ICommand {

	private Stack<Decision> stackOfJudgment = new Stack<>();
	// private Stack<FlowChartElement> stackOfLoopReturn = new Stack<>();
	// private int recodeCounter;
	private int codeCounter;
	private int doWhileCounter;

	@Override
	public void execute() {
		ValidateDiagramCommand validator = new ValidateDiagramCommand();
		validator.execute();
		if (validator.isError()) {
			Main.log("Masih Error");
			return;
		}
		if (validator instanceof IElement) { // always fail
			FileDialog dialog = new FileDialog(MainWindow.getInstance(), SWT.SAVE);
			dialog.setFilterNames(new String[] { "*.txt", "*.*" });
			dialog.setFileName("output.txt");
			String filepath = dialog.open();
			Main.log("Save to " + filepath);

			PrintWriter writer;
			try {
				writer = new PrintWriter(filepath, "UTF-8");
				writer.println("The first line");
				writer.println("The second line");
				writer.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		List<IElement> elements;
		elements = MainWindow.getInstance().getEditor().getActiveSubEditor().getElements();

		/* Get start element */
		IElement currentElem = null;
		for (IElement e : elements) {
			if (e instanceof FlowChartElement) {
				((FlowChartElement) e).prepare();
				;
			}
			if (e instanceof Terminator) {
				if (((Terminator) e).getText().equals(Terminator.START)) {
					currentElem = e;
				}
			}
		}

		/* Begin code flowchartElement, send father, his son, and new code */
		FlowChartElement father = (FlowChartElement) currentElem;
		FlowChartElement son = (FlowChartElement) ((TwoDimensional) father).getChildren().get(0);
		try {
			father.setNodeCode(new NodeCode());
			codeCounter = 0;
			doWhileCounter = 0;
			// recodeCounter = 0;
			codeAlgorithm(father, son, father.getNodeCode().createSibling());
		} catch (GenerateCodeException ex) {
			Main.log(" Error: ");
			Main.log(ex.getMessage());
		}
		MainWindow.getInstance().getEditor().getActiveSubEditor().clearCanvas();
		MainWindow.getInstance().getEditor().getActiveSubEditor().draw();
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
				if (father instanceof Decision && father.getType() == null || currNode.getDoWhileCounter() < doWhileCounter) {
					if (currNode.getDoWhileCounter() < doWhileCounter) {
						currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1); //!!!
					}
					Main.log("\tProcess father is do-while.");
					NodeCode thisCode = currNode.getNodeCode();
					father.setNodeCode(thisCode);
					father.setType(DoWhileType.get());
					((Decision) father).setDoWhileNode(currNode);
					// currNode.setNodeCode(thisCode.createChild());
					doWhileCounter++;
					father.setDoWhileCounter(father.getDoWhileCounter() + 1);
					codeAlgorithm(father, currNode, thisCode.createChild());
					doWhileCounter--;
				}
			}
		}
		if (currElem instanceof Decision) {
			Decision currNode = (Decision) currElem;
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
				Main.log("\tDecision hasn't been traversed" + again);
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
					Main.log("\tGo to decision's child.");
					codeAlgorithm(currNode, (FlowChartElement) son, sonCode);
				}
				for (Convergence convergenceson : convergenceSons) {
					Main.log("\tGo to decision's convergence direct child.");
					codeAlgorithm(currNode, convergenceson, null);
				}

				/* loop structures have been recognized, the left is selections */
				if (currNode.getType() == null) {
					/*
					 * according to the condition of judgment, the detailed
					 * structures of if-else/if/case can be recognized also.
					 * Approved by gugik :D
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
				Main.log("\tGo to decision's direct convergence.");
				codeAlgorithm(conv, sonNode, sonCode);
			}
			else { // been traversed.
				if (currNode.getType() == null) {
					currNode.setType(WhileType.get());
				}
				else { // and been recognized
					if (father instanceof Decision && father.getType() == null || currNode.getDoWhileCounter() < doWhileCounter) {
						if (currNode.getDoWhileCounter() < doWhileCounter) {
							currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1); //!!!
						}
						Main.log("\tDecision father is do-while.");
						NodeCode thisCode = currNode.getNodeCode();
						father.setNodeCode(thisCode);
						father.setType(DoWhileType.get());
						((Decision) father).setDoWhileNode(currNode);
						// currNode.setNodeCode(thisCode.createChild());
						doWhileCounter++;
						father.setDoWhileCounter(father.getDoWhileCounter() + 1);
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
					Main.log("\tConnect convergence with decision.");
					if (stackOfJudgment.isEmpty()) {
						throw new GenerateCodeException("Empty stackOfJudgment.");
					}
					Decision tempDecision = stackOfJudgment.pop();
					currNode.setDirectJudgment(tempDecision);
					tempDecision.setDirectConvergence(currNode);
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

}
