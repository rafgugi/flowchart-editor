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
	private Stack<FlowChartElement> stackOfLoopReturn = new Stack<>();
	private int recodeCounter;
	private int codeCounter;

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
			recodeCounter = 0;
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
			Main.log("\t\t<15>");
			returnCode();
			return; // exit point of recursion /* [15] */
		}
		if (currElem instanceof Process) { /* [1] */
			Main.log("\t\t<1>");
			Process currNode = (Process) currElem;
			/* Generate the code for CurrentNode */
			Main.log("\t\t<1-1>");
			currNode.setNodeCode(currCode); /* [1-1] */
			NodeCode codeOfSon = currCode.createSibling();
			if (!currNode.hasBeenTraversed()) {
				currNode.traverse();
				FlowChartElement son = (FlowChartElement) currNode.getFlow().getDstElement();
				Main.log("\t\t<2>");
				codeAlgorithm(currNode, son, codeOfSon); /* [2] */
			} else {
				/*
				 * Father is recognized as a do-while structure, mark the
				 * Judgment and link it with his Father, include do-while and
				 * nested do-while
				 */
				if (father instanceof Decision && father.getType() == null) { /* [3] */
					Main.log("\t\t<3>");
					father.setType(DoWhileType.get());
					currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1); // original
																					// value
																					// is
																					// zero
					father.setDoWhileNode(currNode);
					father.setDoWhileCounter(currNode.getDoWhileCounter());
					/* used for recode */
					currNode.setRecodeDoWhileCounter(currNode.getDoWhileCounter());
				}
				if (currNode.getDoWhileCounter() > 0) {
					Main.log("\t\t<4>");
					recode(currNode, null); /* [4] */
				}
			}
		}
		if (currElem instanceof Decision) { /* [5] */
			Main.log("\t\t<5>");
			Decision currNode = (Decision) currElem;
			if (!currNode.hasBeenTraversed()) { /* [5-2] */
				Main.log("\t\t<5-2>");
				currNode.traverse();
				Main.log("\t\t<5-1>");
				currNode.setNodeCode(currCode); /* [5-1] */
				Main.log("\t\t<6>");
				stackOfJudgment.push(currNode); /* [6] */

				ArrayList<Convergence> convergenceSons = new ArrayList<>();
				Main.log("\t\t<7>");
				for (TwoDimensional son : currNode.getChildren()) { /* [7] */
					if (son instanceof Convergence) {
						convergenceSons.add((Convergence) son);
						continue;
					}
					NodeCode sonCode = currCode.createChild();
					Main.log("\t\t<7-1>");
					codeAlgorithm(currNode, (FlowChartElement) son,
							sonCode); /* [7-1] */
				}
				for (Convergence convergenceson : convergenceSons) { /* [7-2] */
					Main.log("\t\t<7-2>");
					Main.log("\t\t<8>");
					codeAlgorithm(currNode, convergenceson, null); /* [8] */
				}
				/*
				 * loop structures have been recognized, the left is selections
				 */
				if (currNode.getType() == null) {
					/*
					 * according to the condition of judgment, the detailed
					 * structures of if-else/if/case can be recognized also.
					 */
					currNode.setType(SelectionType.get());
				}
				/* Continue to process the nodes behind Convergence. */
				Convergence conv = currNode.getDirectConvergence();
				NodeCode sonCode = currCode.createSibling();
				FlowChartElement sonNode = (FlowChartElement) conv.getFlow().getDstElement();
				Main.log("\t\t<9>");
				codeAlgorithm(conv, sonNode, sonCode); /* [9] */
			}
			// been traversed.
			else {
				if (currNode.getType() == null) { /* [10] */
					Main.log("\t\t<10>");
					currNode.setType(WhileType.get());
				} else {
					// and been recognized
					if (father instanceof Decision && father.getType() == null) { /* [11] */
						Main.log("\t\t<11>");
						father.setType(DoWhileType.get());
						currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1);
						father.setDoWhileNode(currNode);
						father.setDoWhileCounter(currNode.getDoWhileCounter());
						/* used for recode */
						currNode.setRecodeDoWhileCounter(currNode.getDoWhileCounter());
					}
					if (currNode.getDoWhileCounter() > 0) {
						Main.log("\t\t<12>");
						recode(currNode, null); /* [12] */
					}
				}
			}
		}
		if (currElem instanceof Convergence) {
			Convergence currNode = (Convergence) currElem;
			/* match a judgment node and a convergence node */
			if (!currNode.hasBeenTraversed()) { /* [13] */
				Main.log("\t\t<13>");
				currNode.traverse();
				/*
				 * as a judgment and a convergence is exist geminate, so the top
				 * judgment in stack must be able to match the current
				 * convergence
				 */
				if (currNode.getDirectJudgment() == null) {
					if (stackOfJudgment.isEmpty()) {
						throw new GenerateCodeException("Empty stackOfJudgment.");
					}
					Decision tempDecision = stackOfJudgment.pop();
					currNode.setDirectJudgment(tempDecision);
					tempDecision.setDirectConvergence(currNode);
					currNode.setNodeCode(tempDecision.getNodeCode());
				}
			} else {
				Main.log("\t\t<14>");
				returnCode();
				return; /* [14] */
			}
		}
		returnCode();
	}

	/**
	 * Recode do-while. When first enter, the first parameter is the first node
	 * in do-while and the second is null.
	 * 
	 * @param currNode
	 * @param currCode
	 */
	public void recode(FlowChartElement currNode, NodeCode currCode) {
		if (currNode instanceof IElement) {
			 // throw new GenerateCodeException("Stop at recode!");
		}
		Main.log("recode (" + (recodeCounter++) + ")");
		Main.log("    " + currNode);
		Main.log("    " + currCode);

		if (currCode != null) { /* [R1] ??????? */
			if (currNode.getType() instanceof LoopType) {
				Main.log("\t\t<R1>");
				if (stackOfLoopReturn.isEmpty() || stackOfLoopReturn.peek() != currNode) {
					Main.log("\t\t<R2>");
					stackOfLoopReturn.push(currNode); /* [R2] */
				} else { /* [R3] */
					Main.log("\t\t<R3>");
					if (stackOfLoopReturn.isEmpty()) {
						throw new GenerateCodeException("Empty stackOfLoopReturn.");
					}
					stackOfLoopReturn.pop();
					returnRecode();
					return;
				}
			}
		}
		if (currCode != null) {
			Main.log("\t\t<R4>");
			currNode.setNodeCode(currCode); /* [R4] */
		}
		/*
		 * Ini coba coba aja sumpah. hapus aja klo salah * else { currCode =
		 * currNode.getNodeCode().createChild(); } //
		 */
		if (currNode.getRecodeDoWhileCounter() > 0) {
			/* [R5] ??????????? */
			Main.log("\t\t<R5>");
			TwoDimensional currElem = (TwoDimensional) currNode;
			for (TwoDimensional parent : currElem.getParents()) {
				FlowChartElement father = (FlowChartElement) parent;
				if (father.getDoWhileCounter() != currNode.getRecodeDoWhileCounter()) {
					continue;
				}
				if (father.getDoWhileNode() != currNode) {
					continue;
				}
				(father).setDoWhileCounter(currNode.getRecodeDoWhileCounter());
				(father).setDoWhileNode(currNode);
				currNode.setRecodeDoWhileCounter(currNode.getRecodeDoWhileCounter() - 1);
				/* Let the code of father be the code of CurrentNode */
				Main.log("\t\t<R6>");
				recode(father, currNode.getNodeCode()); /* [R6] */
			}
			if (currNode.getRecodeDoWhileCounter() == 0) { /* [R7] */
				Main.log("\t\t<R7>");
				currNode.setRecodeDoWhileCounter(currNode.getDoWhileCounter());
			}
		}
		if (currNode.getType() instanceof ProcessType) { /* [R8] */
			Main.log("\t\t<R8>");
			if (currCode == null) {
				// throw new GenerateCodeException("Current code never existed");
//				Main.log("\t\tCurrent code never existed, hard return");
//				returnRecode();
//				return;
				Main.log("\t\tMaksa pake currNode.getCode");
				currCode = currNode.getNodeCode();
			}
			NodeCode newCode = currCode.createSibling();
			Main.log("\t\t<R8-1>");
			TwoDimensional son = ((Process) currNode).getChildren().get(0);
			recode((FlowChartElement) son, newCode); /* [R8-1] */
		} else if (currNode.getType() instanceof SelectionType) {
			Decision specific = (Decision) currNode;
			Main.log("\t\t<R9>");
			for (TwoDimensional son : specific.getChildren()) { /* [R9] */
				if (son instanceof Convergence) {
					continue;
				}
				NodeCode newCode = currCode.createChild();
				recode((FlowChartElement) son, newCode); // ?????????
			}
			IElement convergSon = specific.getDirectConvergence().getFlow().getDstElement();
			Main.log("\t\t<R10>");
			recode((FlowChartElement) convergSon, currCode); /* [R10] */
		} else if (currNode.getType() instanceof LoopType) { /* [R11] */
			Main.log("\t\t<R11>");
			Decision specific = (Decision) currNode;

			/* Get son that is not convergence */
			FlowChartElement notConv = null;
			for (IElement child : ((TwoDimensional) currNode).getChildren()) {
				if (!(child instanceof Convergence)) {
					notConv = (FlowChartElement) child;
					break;
				}
			}
			if (currCode == null) {
				// throw new GenerateCodeException("Current code never existed");
//				Main.log("\t\tCurrent code never existed, hard return");
//				returnRecode();
//				return;
				Main.log("\t\tMaksa pake currNode.getCode");
				currCode = currNode.getNodeCode();
			}
			NodeCode newCode = currCode.createChild();
			Main.log("\t\t<R11-1>");
			if (notConv == null) {
				throw new GenerateCodeException("This decision has no normal child");
			}
			recode(notConv, newCode); /* [R11-1] */
			if (stackOfLoopReturn.isEmpty()) {
				Main.log("\t\t<R13>");
				returnRecode();
				return; // return to codealgorithm /* [R13] */
			}
			newCode = currCode.createSibling();
			Convergence directConv = specific.getDirectConvergence();
			if (directConv == null) {
				Main.log("\t\tDirectConvergence is null, hard return");
				returnRecode();
				return; /* [--] */
				// throw new GenerateCodeException("DirectConvergence is null");
			}
			IElement convergSon = directConv.getFlow().getDstElement();
			Main.log("\t\t<R11-2>");
			recode((FlowChartElement) convergSon, newCode); /* [R11-2] */
		} else {
			Main.log("\t\t<R12>");
			returnRecode();
			return; /* [R12] */
		}
		returnRecode();
	}

	public void returnCode() {
		Main.log("return " + --codeCounter);
	}

	public void returnRecode() {
		Main.log("return " + --recodeCounter);
	}

}
