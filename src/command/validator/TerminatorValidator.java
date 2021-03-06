package command.validator;

import java.util.ArrayList;

import diagram.flowchart.Terminator;
import interfaces.IElement;
import widget.validation.ValidationItem;

/**
 * 1. Terminator start end doesn't exist.
 * 2. Terminator start or end is more than one.
 */
public class TerminatorValidator extends AValidator {

	@Override
	public void execute() {
		Terminator start = null;
		Terminator end = null;
		ArrayList<Terminator> overTerminator = new ArrayList<>();
		for (IElement elem : getAllElements()) {
			if (elem instanceof Terminator) {
				Terminator ele = (Terminator) elem;
				if (ele.getText().equals(Terminator.START)) {
					if (start == null) {
						start = ele;
					} else {
						overTerminator.add(ele);
					}
				} else if (ele.getText().equals(Terminator.END)) {
					if (end == null) {
						end = ele;
					} else {
						overTerminator.add(ele);
					}
				}
			}
		}

		if (start == null) {
			ValidationItem item = new ValidationItem();
			item.setTitle("Tidak ada terminator " + Terminator.START);
			addValidationItem(item);
		}
		if (end == null) {
			ValidationItem item = new ValidationItem();
			item.setTitle("Tidak ada terminator " + Terminator.END);
			addValidationItem(item);
		}
		for (Terminator element : overTerminator) {
			ValidationItem item = new ValidationItem();
			item.setTitle("Terminator " + element.getText() + " lebih dari satu");
			item.addProblem(element);
			addValidationItem(item);
		}
	}

}
