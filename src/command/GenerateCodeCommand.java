package command;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Stack;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import diagram.element.TwoDimensional;
import diagram.flowchart.*;
import diagram.flowchart.Process;
import diagram.flowchart.type.*;
import diagram.pad.*;
import exception.InvalidFlowChartException;
import interfaces.FlowChartElement;
import interfaces.ICommand;
import interfaces.IElement;
import widget.window.MainWindow;

public class GenerateCodeCommand implements ICommand {
	
	private Stack<Decision> stackOfJudgment = new Stack<>();

	@Override
	public void execute() {
		ValidateDiagramCommand validator = new ValidateDiagramCommand();
		validator.execute();
		if (validator.isError()) {
			System.out.println("Masih Error");
		} else {
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
				initializeElement((FlowChartElement) e);
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

	public final void initializeElement(FlowChartElement e) {
		e.setType(null);
		e.setNodeCode(null);
		e.setDoWhileCounter(0);
		e.setRecodeDoWhileCounter(0);
	}

	public final void codeAlgorithm(FlowChartElement father, FlowChartElement currElem, NodeCode currCode) {
		if (currElem instanceof Terminator) {
			return; // exit point of recursion
		}
		if (currElem instanceof Process) {
			Process currNode = (Process) currElem;
			currNode.setNodeCode(currCode);
			NodeCode codeOfSon = currCode.createSibling();
			if (!currNode.hasBeenTraversed()) {
				currNode.traverse();
				FlowChartElement son = (FlowChartElement) currNode.getFlow().getDstElement();
				codeAlgorithm(currNode, son, codeOfSon);
			} else {
				if (father instanceof Decision && father.getType() == null) {
					father.setType(DoWhileType.get());
					currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1);
					father.setDoWhileNode(currNode);
					father.setDoWhileCounter(currNode.getDoWhileCounter());
					/* used for recode ??? */
					currNode.setDoWhileCounter(currNode.getDoWhileCounter());
				}
				if (currNode.getDoWhileCounter() > 0) {
					recode(currNode, null);
				}
			}
		}
		if (currElem instanceof Decision) {
			Decision currNode = (Decision) currElem;
			if (!currNode.hasBeenTraversed()) {
				currNode.setNodeCode(currCode);
				stackOfJudgment.push(currNode);

				for (TwoDimensional son : currNode.getChildren()) { // except convergence pls
					NodeCode sonCode = currCode.createChild();
					codeAlgorithm(currNode, (FlowChartElement) son, sonCode);
				}
				// if CurrentNode has a son of convergence( as convergenceSon ) [7-2]
				// {
				// 	CodeAlgorithm(CurrentNode, convergenceSon, null); [8]
				// }
				if (currNode.getType() == null) {
					currNode.setType(SelectionType.get());
				}
				// /*Continue to process the nodes behind Convergence. */
				// CurrentNode=CurrentNode.directJudgmentNode;
				// CodeofSon = Increase YY of CurrentCode by one
				// CodeAlgorithm(CurrentNode, CurrentNode.Son, CodeofSon); [9]
			} else {
				if (currNode.getType() == null) {
					currNode.setType(WhileType.get());
				} else {
					if (father instanceof Decision && father.getType() == null) {
						father.setType(DoWhileType.get());
						currNode.setDoWhileCounter(currNode.getDoWhileCounter() + 1);
						father.setDoWhileNode(currNode);
						father.setDoWhileCounter(currNode.getDoWhileCounter());
						currNode.setRecodeDoWhileCounter(currNode.getDoWhileCounter());
					}
					if (currNode.getDoWhileCounter() > 0) {
						recode(currNode, null);
					}
				}
			}
		}
	}
	
	public final void recode(FlowChartElement currNode, NodeCode currCode) {
		
	}

	public final ElementContainer generatePad() {
		Stack<IElement> flowstack = new Stack<>();
		Stack<ElementContainer> padstack = new Stack<>();

		ElementContainer currentPad = new ElementContainer();
		padstack.push(currentPad);

		List<IElement> elements;
		elements = MainWindow.getInstance().getEditor().getActiveSubEditor().getElements();

		/* Get start element */
		IElement currentElem = null;
		for (IElement e : elements) {
			if (e instanceof Terminator) {
				if (((Terminator) e).getText().equals(Terminator.START)) {
					currentElem = e;
				}
			}
		}

		if (currentElem == null) {
			throw new InvalidFlowChartException("Start element not found.");
		}

		flowstack.push(currentElem);

		while (!flowstack.isEmpty()) {
			currentElem = flowstack.pop();

			/* Case when the element is terminator */
			if (currentElem instanceof Terminator) {
				Terminator term = (Terminator) currentElem;
				if (term.getText().equals(Terminator.START)) {
					flowstack.push(term.getFlow().getDstElement());
				}
			}

			/* Case when the element is process */
			if (currentElem instanceof Process) {
				Process proc = (Process) currentElem;
				flowstack.push(proc.getFlow().getDstElement());
			}
		}

		return null;
	}

	public final String generateCode() {
		return "";
	}

}
