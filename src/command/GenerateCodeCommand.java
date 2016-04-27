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
import diagram.pad.*;
import exception.InvalidFlowChartException;
import interfaces.ICommand;
import interfaces.IElement;
import widget.window.MainWindow;

public class GenerateCodeCommand implements ICommand {

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
		e.setType("");
		e.setNodeCode(null);
	}

	public final void codeAlgorithm(FlowChartElement father, FlowChartElement currElem, NodeCode currCode) {
		if (currElem instanceof Terminator) {
			return; // exit point of recursion
		}
		if (currElem instanceof Decision) {
			// if current node has been transversed. wtf is tranversed?
		}
		if (currElem instanceof Process) {
			currElem.setNodeCode(currCode);
			NodeCode codeOfSon = currCode.createSibling();
		}
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
