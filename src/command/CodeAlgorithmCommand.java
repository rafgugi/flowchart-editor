package command;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

public class CodeAlgorithmCommand implements ICommand {

	private Stack<Judgment> stackOfJudgment = new Stack<>();
	private int codeCounter;
	private int doWhileCounter;
	private NodeCode newCode;

	@Override
	public void execute() {
		/* Get start element */
		List<IElement> elements;
		elements = MainWindow.getInstance().getEditor().getActiveSubEditor().getElements();
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
		Terminator father = (Terminator) currentElem;
		FlowChartElement son = (FlowChartElement) father.getFlow().getDstElement();
		newCode = new NodeCode();
		father.setNodeCode(newCode);
		codeCounter = 0;
		doWhileCounter = 0;
		codeAlgorithm(father, son, father.getNodeCode().createSibling());
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
			if (!currNode.hasBeenTraversed() || currNode.getDoWhileCounter() < doWhileCounter) {
				String again = "";
				if (currNode.getDoWhileCounter() < doWhileCounter) {
					/* This process is still surrounded by do-while */
					currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1);
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
				if (father instanceof Judgment && father.getType() == null
						|| currNode.getDoWhileCounter() < doWhileCounter) {
					if (currNode.getDoWhileCounter() < doWhileCounter) {
						/* This process is still surrounded by do-while */
						currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1);
					}
					Main.log("\tProcess father is do-while.");
					father.setType(DoWhileType.get());
					NodeCode thisCode = currNode.getNodeCode();
					father.setNodeCode(thisCode); // do-while take over child code
					thisCode.resetChildren(); // and reset the child of the code

					Main.log("\tBegining to recode do-while child.");
					doWhileCounter++; // increase do-while stack
					father.setDoWhileCounter(father.getDoWhileCounter() + 1);
					codeAlgorithm(father, currNode, thisCode.createChild());
					doWhileCounter--; // reset do-while stack after finish recode
				}
			}
		}
		if (currElem instanceof Judgment) {
			Judgment currNode = (Judgment) currElem;
			if (!currNode.hasBeenTraversed() || currNode.getDoWhileCounter() < doWhileCounter) {
				String again = "";
				if (currNode.getDoWhileCounter() < doWhileCounter) {
					/* This process is still surrounded by do-while */
					currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1);
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

				/* Process direct convergence, judgment and convergence will be connected */
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
							/* This process is still surrounded by do-while */
							currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1);
						}
						Main.log("\tJudgment father is do-while.");
						father.setType(DoWhileType.get());
						NodeCode thisCode = currNode.getNodeCode();
						father.setNodeCode(thisCode); // do-while take over child code
						thisCode.resetChildren(); // and reset the child of the code

						Main.log("\tBegining to recode do-while child.");
						doWhileCounter++; // increase do-while stack
						father.setDoWhileCounter(father.getDoWhileCounter() + 1);
						codeAlgorithm(father, currNode, thisCode.createChild());
						doWhileCounter--; // reset do-while stack after finish recode
					}
				}
			}
		}
		if (currElem instanceof Convergence) {
			Convergence currNode = (Convergence) currElem;
			/* match a judgment node and a convergence node */
			if (!currNode.hasBeenTraversed() || currNode.getDoWhileCounter() < doWhileCounter) {
				String again = "";
				if (currNode.getDoWhileCounter() < doWhileCounter) {
					/* This process is still surrounded by do-while */
					currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1);
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
	
	public NodeCode getFirstCode() {
		return newCode;
	}

}
