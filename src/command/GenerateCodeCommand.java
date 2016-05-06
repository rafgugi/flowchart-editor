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
import interfaces.FlowChartElement;
import interfaces.ICommand;
import interfaces.IElement;
import widget.window.MainWindow;

public class GenerateCodeCommand implements ICommand {

	private Stack<Decision> stackOfJudgment = new Stack<>();
	private Stack<FlowChartElement> stackOfLoopReturn = new Stack<>();

	@Override
	public void execute() {
		ValidateDiagramCommand validator = new ValidateDiagramCommand();
		validator.execute();
		if (validator.isError()) {
			System.out.println("Masih Error");
			return;
		}
		if (validator instanceof IElement) { // always fail
			FileDialog dialog = new FileDialog(MainWindow.getInstance(), SWT.SAVE);
			dialog.setFilterNames(new String[] { "*.txt", "*.*" });
			dialog.setFileName("output.txt");
			String filepath = dialog.open();
			System.out.println("Save to " + filepath);

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
		TwoDimensional father = (TwoDimensional) currentElem;
		TwoDimensional son = father.getChildren().get(0);
		codeAlgorithm((FlowChartElement) father, (FlowChartElement) son, new NodeCode());
	}

	/**
	 * Generate a code for every node according to the coding strategy.
	 * 
	 * @param father node
	 * @param current node
	 * @param current code
	 */
	public final void codeAlgorithm(FlowChartElement father, FlowChartElement currElem, NodeCode currCode) {
		System.out.println("codeAlgorithm:");
		System.out.println("    " + father);
		System.out.println("    " + currElem);
		System.out.println("    " + currCode);

		if (currElem instanceof Terminator) {
			return; // exit point of recursion /* [15] */
		}
		if (currElem instanceof Process) { /* [1] */
			Process currNode = (Process) currElem;
			currNode.setNodeCode(currCode); /* [1-1] */
			NodeCode codeOfSon = currCode.createSibling();
			if (!currNode.hasBeenTraversed()) {
				currNode.traverse();
				FlowChartElement son = (FlowChartElement) currNode.getFlow().getDstElement();
				codeAlgorithm(currNode, son, codeOfSon); /* [2] */
			} else {
				if (father instanceof Decision && father.getType() == null) { /* [3] */
					father.setType(DoWhileType.get());
					currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1);
					father.setDoWhileNode(currNode);
					father.setDoWhileCounter(currNode.getDoWhileCounter());
					/* used for recode ??? */
					currNode.setDoWhileCounter(currNode.getDoWhileCounter());
				}
				if (currNode.getDoWhileCounter() > 0) {
					recode(currNode, null); /* [4] */
				}
			}
		}
		if (currElem instanceof Decision) { /* [5] */
			Decision currNode = (Decision) currElem;
			if (!currNode.hasBeenTraversed()) { /* [5-2] */
				currNode.setNodeCode(currCode); /* [5-1] */
				stackOfJudgment.push(currNode); /* [6] */

				ArrayList<Convergence> convergenceSons = new ArrayList<>();
				for (TwoDimensional son : currNode.getChildren()) { /* [7] */
					if (son instanceof Convergence) {
						convergenceSons.add((Convergence) son);
						continue;
					}
					NodeCode sonCode = currCode.createChild();
					codeAlgorithm(currNode, (FlowChartElement) son, sonCode); /* [7-2] */
				}
				for (Convergence convergenceson : convergenceSons) {
					codeAlgorithm(currNode, convergenceson, null); /* [8] */
				}
				if (currNode.getType() == null) {
					currNode.setType(SelectionType.get());
				}
				// /*Continue to process the nodes behind Convergence. */
				// CurrentNode=CurrentNode.directJudgmentNode;
				// CodeofSon = Increase YY of CurrentCode by one
				// CodeAlgorithm(CurrentNode, CurrentNode.Son, CodeofSon); [9]
			} else {
				if (currNode.getType() == null) { /* [10] */
					currNode.setType(WhileType.get());
				} else {
					if (father instanceof Decision && father.getType() == null) { /* [11] */
						father.setType(DoWhileType.get());
						currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1);
						father.setDoWhileNode(currNode);
						father.setDoWhileCounter(currNode.getDoWhileCounter());
						currNode.setRecodeDoWhileCounter(currNode.getDoWhileCounter());
					}
					if (currNode.getDoWhileCounter() > 0) {
						recode(currNode, null); /* [12] */
					}
				}
			}
		}
		if (currElem instanceof Convergence) {
			Convergence currNode = (Convergence) currElem;
			if (!currNode.hasBeenTraversed()) { /* [13] */
				Decision tempDecision = stackOfJudgment.pop();
				currNode.setDirectJudgment(tempDecision);
				tempDecision.setDirectConvergence(currNode);
				currNode.setNodeCode(tempDecision.getNodeCode());
			} else {
				return; /* [14] */
			}
		}
	}

	/**
	 * Recode do-while. When first enter, the first parameter is the first node
	 * in do-while and the second is null.
	 * 
	 * @param currNode
	 * @param currCode
	 */
	public final void recode(FlowChartElement currNode, NodeCode currCode) {
		System.out.println("recode:");
		System.out.println("    " + currNode);
		System.out.println("    " + currCode);

		if (currCode != null) { /* [R1] ??????? */
			if (currNode.getType() instanceof LoopType) {
				stackOfLoopReturn.push(currNode); /* [R2] */
			} else { /* [R3] */
				stackOfLoopReturn.pop();
				return;
			}
		}
		if (currCode != null) {
			currNode.setNodeCode(currCode); /* [R4] */
		}
		if (currNode.getRecodeDoWhileCounter() > 0) {
			/* [R5] ??????????? */
			TwoDimensional currElem = (TwoDimensional) currNode;
			for (TwoDimensional parent : currElem.getParents()) {
				FlowChartElement father = (FlowChartElement) parent;
				(father).setDoWhileCounter(currNode.getRecodeDoWhileCounter());
				(father).setDoWhileNode(currNode);
				currNode.setDoWhileCounter(currNode.getDoWhileCounter() - 1);
				recode(father, currNode.getNodeCode()); /* [R6] */
			}
			if (currNode.getRecodeDoWhileCounter() == 0) { /* [R7] */
				currNode.setRecodeDoWhileCounter(currNode.getDoWhileCounter());
			}
		}
		if (currNode.getType() instanceof ProcessType) { /* [R8] */
			NodeCode newCode = currCode.createSibling();
			recode(currNode, newCode); /* [R8-1] */
		} else if (currNode.getType() instanceof SelectionType) {
			Decision specific = (Decision) currNode;
			for (TwoDimensional son : specific.getChildren()) { /* [R9] */
				if (son instanceof Convergence) {
					continue;
				}
				NodeCode newCode = currCode.createChild();
				recode((FlowChartElement) son, newCode); //?????????
			}
			IElement convergSon = specific.getDirectConvergence().getFlow().getDstElement(); 
			recode((FlowChartElement) convergSon, currCode); /* [R10] */
		} else if (currNode.getType() instanceof LoopType) { /* [R11] */
			Decision specific = (Decision) currNode;
			NodeCode newCode = currCode.createChild();
			recode(currNode, newCode); /* [R11-1] */
			if (stackOfLoopReturn.isEmpty()) {
				return; // return to codealgorithm /* [R13] */
			}
			newCode = currCode.createSibling();
			IElement convergSon = specific.getDirectConvergence().getFlow().getDstElement(); 
			recode((FlowChartElement) convergSon, newCode);
		} else {
			return; /* [R12] */
		}
	}

}
