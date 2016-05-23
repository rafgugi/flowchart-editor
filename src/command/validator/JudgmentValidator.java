package command.validator;

import diagram.flowchart.FlowLine;
import diagram.flowchart.Judgment;
import interfaces.IElement;
import widget.validation.ValidationItem;

/**
 * 1. Judgment doesn't have exactly 2 children.
 * 2. Flow line of judgment type isn't defined.
 */
public class JudgmentValidator extends AValidator {

	@Override
	public void execute() {
		for (IElement element : getAllElements()) {
			if (element instanceof Judgment) {
				Judgment judgment = (Judgment) element;
				if (judgment.getFlows().size() != 2) {
					ValidationItem item = new ValidationItem();
					String name;
					if (judgment.getText().equals("")) {
						name = judgment.getText();
					} else {
						name = judgment.getClass().getSimpleName();
					}
					item.setTitle(name + " harus memiliki 2 cabang");
					item.addProblem(element);
					addValidationItem(item);
				}
				for (FlowLine flow : judgment.getFlows()) {
					if (flow.getText().equals(FlowLine.NONE)) {
						ValidationItem item = new ValidationItem();
						item.setTitle("Tipe Flowline belum terdefinisi");
						item.addProblem(flow);
						addValidationItem(item);
					}
				}
			}
		}
	}

}
