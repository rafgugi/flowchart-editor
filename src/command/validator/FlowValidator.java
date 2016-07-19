package command.validator;

import java.util.Stack;

import diagram.flowchart.*;
import diagram.flowchart.Process;
import diagram.flowchart.Terminator;
import exception.ValidationException;
import interfaces.FlowChartElement;
import interfaces.IElement;
import main.Main;
import widget.validation.ValidationItem;
import widget.window.MainWindow;

/**
 * 1. Judgment doesn't have convergence
 * 2. Too much convergence
 * 3. Invalid flow
 * 4. Stack Overflow
 */
public class FlowValidator extends AValidator {

	private Stack<Judgment> judgmentStack;
	private int maxloop;

	@Override
	public void execute() {
		Terminator start = null;
		for (IElement e : getAllElements()) {
			if (e instanceof FlowChartElement) {
				((FlowChartElement) e).prepare();
				
				/* Cek apkah elemen2 ini harus punya next */
				FlowChartElement problem = null;
				if (e instanceof Process) {
					if (((Process) e).getFlow() == null) {
						problem = (FlowChartElement) e;
					}
				} else if (e instanceof Convergence) {
					if (((Convergence) e).getFlow() == null) {
						problem = (FlowChartElement) e;
					}
				}
				if (problem != null) {
					ValidationItem item = new ValidationItem();
					item.addProblem(problem);
					item.setTitle(problem + " tidak memiliki tujuan");
					addValidationItem(item);
				}
			}
			if (e instanceof Terminator) {
				if (((Terminator) e).getText().equals(Terminator.START)) {
					start = (Terminator) e;
				}
			}
		}

		/* we can validate if flowchart is not complete */
		if (!MainWindow.getInstance().getValidationList().getValidationItems().isEmpty()) {
			return;
		}

		/* Dari atas, klo ketemu judgment masukin ke stack.
		 * klo ketemu convergence, pasangkan stack.pop. Jika
		 * stack.pop error, brarti convergence kbanyakan. Jika sampe
		 * akhir masih ada isi stack, brarti kurang convergence.
		 */
		FlowChartElement son = start.getFlow().getSon();
		judgmentStack = new Stack<>();
		maxloop = getAllElements().size() * 2;
		try {
			check(start, son);
		} catch (ValidationException ex) {
			ValidationItem item = new ValidationItem();
			item.setTitle(ex.getMessage());
			addValidationItem(item);
		}

		while (!judgmentStack.isEmpty()) {
			Judgment j = judgmentStack.pop();
			ValidationItem item = new ValidationItem();
			item.addProblem(j);
			item.setTitle(j + " tidak punya pasangan \"Convergence\"");
			addValidationItem(item);
		}
	}

	private void check(FlowChartElement parent, FlowChartElement currElem) {
		if (maxloop < 0) {
			throw new ValidationException("Stack overflow");
		}
		maxloop--;
		if (currElem instanceof Terminator) {
			maxloop++;
			return; // exit point of recursion
		}

		if (currElem instanceof Process) {
			if (!currElem.isTraversed()) {
				currElem.traverse();
			} else {
				if (parent instanceof Process) {
					ValidationItem item = new ValidationItem();
					item.addProblem(currElem);
					item.addProblem(parent);
					item.setTitle("Perulangan harus dimulai atau diakhiri \"Judgment\"");
					addValidationItem(item);
				}
			}
			Process currNode = (Process) currElem;
			try {
				check(currNode, currNode.getFlow().getSon());
			} catch (NullPointerException ex) {
				Main.log("Null pointer ex: " + ex.getMessage());
			}
		}
		if (currElem instanceof Judgment) {
			if (currElem.isTraversed()) {
				maxloop++;
				return;
			}
			if (!currElem.isTraversed()) {
				currElem.traverse();
			}
			Judgment currNode = (Judgment) currElem;
			judgmentStack.push(currNode);
			for (FlowLine f : currNode.getFlows()) {
				check(currNode, f.getSon());
			}
			if (currNode.getDirectConvergence() != null) {
				Convergence conv = currNode.getDirectConvergence();
				FlowChartElement son = conv.getFlow().getSon();
				check(conv, son);
			}
		}
		if (currElem instanceof Convergence) {
			if (currElem.isTraversed()) {
				maxloop++;
				return;
			}
			if (!currElem.isTraversed()) {
				currElem.traverse();
			}
			Convergence currNode = (Convergence) currElem;
			if (judgmentStack.isEmpty()) {
				ValidationItem item = new ValidationItem();
				item.addProblem(currNode);
				item.setTitle(currNode + " tidak punya pasangan \"Judgment\"");
				addValidationItem(item);
			} else {
				Judgment j = judgmentStack.pop();
				currNode.setDirectJudgment(j);
				j.setDirectConvergence(currNode);
			}
		}
		maxloop++;
	}
}
