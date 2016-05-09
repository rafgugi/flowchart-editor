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
		FlowChartElement father = (FlowChartElement) currentElem;
		FlowChartElement son = (FlowChartElement) ((TwoDimensional) father).getChildren().get(0);
		try {
			father.setNodeCode(new NodeCode());
			codeAlgorithm(father, son, father.getNodeCode().createSibling());
		} catch (GenerateCodeException ex) {
			System.out.println(" Error: ");
			System.out.println(ex.getMessage());
		}
		MainWindow.getInstance().getEditor().getActiveSubEditor().clearCanvas();
		MainWindow.getInstance().getEditor().getActiveSubEditor().draw();
	}

	/**
	 * Generate a code for every node according to the coding strategy.
	 * 
	 * @param father node
	 * @param current node
	 * @param current code
	 */
	public void codeAlgorithm(FlowChartElement father, FlowChartElement currElem, NodeCode currCode) {
		System.out.println("codeAlgorithm:");
		System.out.println("\t" + father);
		System.out.println("\t" + currElem);
		System.out.println("\t" + currCode);

		if (currElem instanceof Terminator) {
			System.out.println("\t\t<15>");
			System.out.println("return");
			return; // exit point of recursion /* [15] */
		}
		if (currElem instanceof Process) { /* [1] */
			System.out.println("\t\t<1>");
			Process currNode = (Process) currElem;
			/* Generate the code for CurrentNode */
			System.out.println("\t\t<1-1>");
			currNode.setNodeCode(currCode); /* [1-1] */
			NodeCode codeOfSon = currCode.createSibling();
			if (!currNode.hasBeenTraversed()) {
				currNode.traverse();
				FlowChartElement son = (FlowChartElement) currNode.getFlow().getDstElement();
				System.out.println("\t\t<2>");
				codeAlgorithm(currNode, son, codeOfSon); /* [2] */
			} else {
				/* Father is recognized as a do-while structure, mark the Judgment and
				 * link it with his Father, include do-while and nested do-while */
				if (father instanceof Decision && father.getType() == null) { /* [3] */
					System.out.println("\t\t<3>");
					father.setType(DoWhileType.get());
					currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1); // original value is zero
					father.setDoWhileNode(currNode);
					father.setDoWhileCounter(currNode.getDoWhileCounter());
					/* used for recode ??? */
					currNode.setDoWhileCounter(currNode.getDoWhileCounter());
				}
				if (currNode.getDoWhileCounter() > 0) {
					System.out.println("\t\t<4>");
					recode(currNode, null); /* [4] */
				}
			}
		}
		if (currElem instanceof Decision) { /* [5] */
			System.out.println("\t\t<5>");
			Decision currNode = (Decision) currElem;
			if (!currNode.hasBeenTraversed()) { /* [5-2] */
				System.out.println("\t\t<5-2>");
				currNode.traverse();
				System.out.println("\t\t<5-1>");
				currNode.setNodeCode(currCode); /* [5-1] */
				System.out.println("\t\t<6>");
				stackOfJudgment.push(currNode); /* [6] */

				ArrayList<Convergence> convergenceSons = new ArrayList<>();
				System.out.println("\t\t<7>");
				for (TwoDimensional son : currNode.getChildren()) { /* [7] */
					if (son instanceof Convergence) {
						convergenceSons.add((Convergence) son);
						continue;
					}
					NodeCode sonCode = currCode.createChild();
					System.out.println("\t\t<7-1>");
					codeAlgorithm(currNode, (FlowChartElement) son, sonCode); /* [7-1] */
				}
				for (Convergence convergenceson : convergenceSons) { /* [7-2] */
					System.out.println("\t\t<7-2>");
					System.out.println("\t\t<8>");
					codeAlgorithm(currNode, convergenceson, null); /* [8] */
				}
				/* loop structures have been recognized, the left is selections */
				if (currNode.getType() == null) {
					/* according to the condition of judgment, the detailed 
					 * structures of if-else/if/case can be recognized also. */
					currNode.setType(SelectionType.get());
				}
				/* Continue to process the nodes behind Convergence. */
				Convergence conv = currNode.getDirectConvergence();
				NodeCode sonCode = currCode.createSibling();
				FlowChartElement sonNode = (FlowChartElement) conv.getFlow().getDstElement();
				System.out.println("\t\t<9>");
				codeAlgorithm(conv, sonNode, sonCode); /* [9] */
			}
			// been traversed.
			else {
				if (currNode.getType() == null) { /* [10] */
					System.out.println("\t\t<10>");
					currNode.setType(WhileType.get());
				} else {
					// and been recognized
					if (father instanceof Decision && father.getType() == null) { /* [11] */
						System.out.println("\t\t<11>");
						father.setType(DoWhileType.get());
						currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1);
						father.setDoWhileNode(currNode);
						father.setDoWhileCounter(currNode.getDoWhileCounter());
						currNode.setRecodeDoWhileCounter(currNode.getDoWhileCounter());
					}
					if (currNode.getDoWhileCounter() > 0) {
						System.out.println("\t\t<12>");
						recode(currNode, null); /* [12] */
					}
				}
			}
		}
		if (currElem instanceof Convergence) {
			Convergence currNode = (Convergence) currElem;
			/* match a judgment node and a convergence node */
			if (!currNode.hasBeenTraversed()) { /* [13] */
				System.out.println("\t\t<13>");
				currNode.traverse();
				/* as a judgment and a convergence is exist geminate, so the top
				 * judgment in stack must be able to match the current convergence */
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
				System.out.println("\t\t<14>");
				System.out.println("return");
				return; /* [14] */
			}
		}
		System.out.println("return");
	}

	/**
	 * Recode do-while. When first enter, the first parameter is the first node
	 * in do-while and the second is null.
	 * 
	 * @param currNode
	 * @param currCode
	 */
	public void recode(FlowChartElement currNode, NodeCode currCode) {
		System.out.println("recode:");
		System.out.println("    " + currNode);
		System.out.println("    " + currCode);

		if (currCode != null) { /* [R1] ??????? */
			System.out.println("\t\t<R1>");
			if (currNode.getType() instanceof LoopType) {
				System.out.println("\t\t<R2>");
				stackOfLoopReturn.push(currNode); /* [R2] */
			} else { /* [R3] */
				System.out.println("\t\t<R3>");
				if (stackOfLoopReturn.isEmpty()) {
					throw new GenerateCodeException("Empty stackOfLoopReturn.");
				}
				stackOfLoopReturn.pop();
				return;
			}
		}
		if (currCode != null) {
			System.out.println("\t\t<R4>");
			currNode.setNodeCode(currCode); /* [R4] */
		}
		/* Ini coba coba aja sumpah. hapus aja klo salah *
		else {
			currCode = currNode.getNodeCode().createChild();
		}
		//*/
		if (currNode.getRecodeDoWhileCounter() > 0) {
			/* [R5] ??????????? */
			System.out.println("\t\t<R5>");
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
				currNode.setDoWhileCounter(currNode.getDoWhileCounter() - 1);
				/* Let the code of father be the code of CurrentNode */
				System.out.println("\t\t<R6>");
				recode(father, currNode.getNodeCode()); /* [R6] */
			}
			if (currNode.getRecodeDoWhileCounter() == 0) { /* [R7] */
				System.out.println("\t\t<R7>");
				currNode.setRecodeDoWhileCounter(currNode.getDoWhileCounter());
			}
		}
		if (currNode.getType() instanceof ProcessType) { /* [R8] */
			System.out.println("\t\t<R8>");
			NodeCode newCode = currCode.createSibling();
			recode(currNode, newCode); /* [R8-1] */
		} else if (currNode.getType() instanceof SelectionType) {
			Decision specific = (Decision) currNode;
			System.out.println("\t\t<R9>");
			for (TwoDimensional son : specific.getChildren()) { /* [R9] */
				if (son instanceof Convergence) {
					continue;
				}
				NodeCode newCode = currCode.createChild();
				recode((FlowChartElement) son, newCode); //?????????
			}
			IElement convergSon = specific.getDirectConvergence().getFlow().getDstElement(); 
			System.out.println("\t\t<R10>");
			recode((FlowChartElement) convergSon, currCode); /* [R10] */
		} else if (currNode.getType() instanceof LoopType) { /* [R11] */
			System.out.println("\t\t<R11>");
			Decision specific = (Decision) currNode;
			NodeCode newCode = currCode.createChild();
			/* CurrentNode.son is not the convergence */
			System.out.println("\t\t<R11-1>");
			recode(currNode, newCode); /* [R11-1] */
			if (stackOfLoopReturn.isEmpty()) {
				System.out.println("\t\t<R13>");
				return; // return to codealgorithm /* [R13] */
			}
			newCode = currCode.createSibling();
			IElement convergSon = specific.getDirectConvergence().getFlow().getDstElement(); 
			recode((FlowChartElement) convergSon, newCode);
		} else {
			System.out.println("\t\t<R12>");
			return; /* [R12] */
		}
	}

}
