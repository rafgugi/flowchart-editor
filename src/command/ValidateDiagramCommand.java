package command;

import java.util.ArrayList;
import java.util.Stack;

import diagram.flowchart.Terminator;
import interfaces.ICommand;
import interfaces.IElement;
import interfaces.ISubEditor;
import interfaces.IValidationItem;
import interfaces.IValidationList;
import main.Main;
import widget.window.MainWindow;

public class ValidateDiagramCommand implements ICommand {

	public static final int MAX_ERROR_KINDS = 3;
	public static final int INCOMPLETE_TERMINATOR = 0;
	public static final int MORE_THAN_ONE_TERMINATOR = 1;
	public static final int NOT_CONNECTED_ELEMENT = 2;
	private boolean[] errors;

	private IValidationList validationList;
	
	public ValidateDiagramCommand() {
		validationList = MainWindow.getInstance().getValidationList();
		validationList.reset();
	}

	public String getMessage(int code) {
		switch (code) {
		case INCOMPLETE_TERMINATOR:
			return "Terminator tidak lengkap";
		case MORE_THAN_ONE_TERMINATOR:
			return "Terminator lebih dari satu";
		case NOT_CONNECTED_ELEMENT:
			return "Ada elemen yang tidak terkoneksi";
		default:
			return "";
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		errors = new boolean[MAX_ERROR_KINDS];
		ISubEditor editor = MainWindow.getInstance().getEditor().getActiveSubEditor();

		IElement start = null;
		IElement end = null;
		ArrayList<IElement> overTerminator = new ArrayList<>();
		for (IElement elem : editor.getElements()) {
			if (elem instanceof Terminator) {
				Terminator ele = (Terminator) elem;
				if (ele.getText().equals(Terminator.START)) {
					if (start == null) {
						start = ele;
					} else {
						errors[MORE_THAN_ONE_TERMINATOR] = true;
						overTerminator.add(elem);
					}
				} else if (ele.getText().equals(Terminator.END)) {
					if (end == null) {
						end = ele;
					} else {
						errors[MORE_THAN_ONE_TERMINATOR] = true;
						overTerminator.add(elem);
					}
				}
			}
		}
		
		if (!overTerminator.isEmpty()) {
			IValidationItem item = validationList.newItem();
			for (IElement elem : overTerminator) {
				item.addProblems(elem);
			}
			item.setTitle(getMessage(MORE_THAN_ONE_TERMINATOR));
			validationList.addItem(item);			
		}

		if (start == null || end == null) {
			errors[INCOMPLETE_TERMINATOR] = true;
			IValidationItem item = validationList.newItem();
			item.setTitle(getMessage(INCOMPLETE_TERMINATOR));
			validationList.addItem(item);
		}

		if (!(errors[INCOMPLETE_TERMINATOR] || errors[MORE_THAN_ONE_TERMINATOR])) {
			ArrayList<IElement> elements = (ArrayList<IElement>) editor.getElements();
			elements = (ArrayList<IElement>) elements.clone();
			Stack<IElement> stack = new Stack<>();
			stack.push(start);
			elements.remove(start);

			while (!stack.isEmpty()) {
				IElement popped = stack.pop();
				for (IElement elem : popped.getConnectedElements()) {
					if (elements.remove(elem)) {
						stack.push(elem);
					}
				}
			}
			if (!elements.isEmpty()) {
				errors[NOT_CONNECTED_ELEMENT] = true;
				IValidationItem item = validationList.newItem();
				for (IElement e : elements) {
					item.addProblems(e);	
				}
				item.setTitle(getMessage(NOT_CONNECTED_ELEMENT));
				validationList.addItem(item);
			}
		}

		for (int i = 0; i < MAX_ERROR_KINDS; i++) {
			Main.log(i + (errors[i] ? " true" : " false"));
		}
		showMessage();
	}

	public void showMessage() {
		for (int i = 0; i < MAX_ERROR_KINDS; i++) {
			if (errors[i]) {
				MainWindow.getInstance().setStatus("Validation fails");
				return;
			}
		}
		MainWindow.getInstance().setStatus("Validation success");
	}

	public boolean isError() {
		for (int i = 0; i < MAX_ERROR_KINDS; i++) {
			if (errors[i]) {
				return true;
			}
		}
		return false;
	}

}