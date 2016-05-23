package command;

import java.util.ArrayList;

import command.validator.*;
import interfaces.ICommand;
import interfaces.IDiagramValidator;
import interfaces.IValidationList;
import main.Main;
import widget.window.MainWindow;

public class ValidateDiagramCommand implements ICommand {

	private ArrayList<IDiagramValidator> validators = new ArrayList<>();

	private IValidationList validationList;

	public ValidateDiagramCommand() {
		validationList = MainWindow.getInstance().getValidationList();
		validationList.reset();

		validators.add(new TerminatorValidator());
		validators.add(new ElementsConnectionValidator());
		validators.add(new JudgmentValidator());
		validators.add(new SyntaxValidator());
	}

	@Override
	public void execute() {
		Main.log("Begin validation");
		for (IDiagramValidator validator : validators) {
			validator.execute();
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