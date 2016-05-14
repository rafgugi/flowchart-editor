package command;

import java.util.ArrayList;

import diagram.rules.*;
import interfaces.ICommand;
import interfaces.IDiagramRule;
import interfaces.IElement;
import interfaces.IValidationList;
import main.Main;
import widget.validation.ValidationItem;
import widget.window.MainWindow;

public class ValidateDiagramCommand implements ICommand {

	private ArrayList<IDiagramRule> rules = new ArrayList<>();

	private IValidationList validationList;

	public ValidateDiagramCommand() {
		validationList = MainWindow.getInstance().getValidationList();
		validationList.reset();

		rules.add(new IncompleteTerminatorRule());
		rules.add(new MoreThanOneTerminatorRule());
		rules.add(new NotConnectedElementRule());
		rules.add(new InvalidExpressionRule());
	}

	@Override
	public void execute() {
		Main.log("Begin validation");
		for (IDiagramRule rule : rules) {
			ArrayList<IElement> invalid = rule.validate();
			if (invalid != null) {
				Main.log("Validation error: " + rule.getDescription());
				ValidationItem item = new ValidationItem();
				item.setTitle(rule.getDescription());
				for (IElement element : invalid) {
					item.addProblems(element);
				}
				validationList.addItem(item);
			}
		}
	}

	public void showMessage() {
		if (validationList.getValidationItems().isEmpty()) {
			MainWindow.getInstance().setStatus("Validation success");
		} else {
			MainWindow.getInstance().setStatus("Validation fail");
		}
	}

	public boolean isError() {
		return false;
	}

}