package command.validator;

import java.util.Stack;

import diagram.flowchart.*;
import diagram.flowchart.Process;
import diagram.flowchart.Terminator;
import exception.ValidationException;
import interfaces.FlowChartElement;
import interfaces.IElement;
import widget.validation.ValidationItem;
import widget.window.MainWindow;

/**
 * 1. Judgment doesn't have convergence
 * 2. Too much convergence
 */
public class ConvergenceValidator extends AValidator {

	private Stack<Judgment> judgmentStack;
	private int maxloop;

	@Override
	public void execute() {
		/* we can validate if flowchart is not complete */
		if (!MainWindow.getInstance().getValidationList().getValidationItems().isEmpty()) {
			return;
		}

		Terminator start = null;
		for (IElement e : getAllElements()) {
			if (e instanceof FlowChartElement) {
				((FlowChartElement) e).prepare();
			}
			if (e instanceof Terminator) {
				if (((Terminator) e).getText().equals(Terminator.START)) {
					start = (Terminator) e;
				}
			}
		}
		/* Dari atas, klo ketemu judgment masukin ke stack.
		 * klo ketemu convergence, pasangkan stack.pop. Jika
		 * stack.pop error, brarti convergence kbanyakan. Jika sampe
		 * akhir masih ada isi stack, brarti kurang convergence.
		 */
		FlowChartElement son = (FlowChartElement) start.getFlow().getDstElement();
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
			item.setTitle(j + " tidak punya pasangan");
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
		if (currElem.hasBeenTraversed()) {
			maxloop++;
			return;
		}
		currElem.traverse();
		if (currElem instanceof Process) {
			Process currNode = (Process) currElem;
			check(currNode, (FlowChartElement) currNode.getFlow().getDstElement());
		}
		if (currElem instanceof Judgment) {
			Judgment currNode = (Judgment) currElem;
			judgmentStack.push(currNode);
			for (FlowLine f : currNode.getFlows()) {
				check(currNode, (FlowChartElement) f.getDstElement());
			}
			if (currNode.getDirectConvergence() != null) {
				Convergence conv = currNode.getDirectConvergence();
				FlowChartElement son = (FlowChartElement) conv.getFlow().getDstElement();
				check(conv, son);
			}
		}
		if (currElem instanceof Convergence) {
			Convergence currNode = (Convergence) currElem;
			if (judgmentStack.isEmpty()) {
				ValidationItem item = new ValidationItem();
				item.addProblem(currNode);
				item.setTitle(currNode + " tidak punya pasangan");
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
