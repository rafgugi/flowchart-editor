package command;

import java.util.ArrayList;
import java.util.Stack;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import diagram.element.RoundedRectangle;
import interfaces.ICommand;
import interfaces.IElement;
import interfaces.ISubEditor;
import widget.window.MainWindow;

public class ValidateDiagramCommand implements ICommand {

	public static final int MAX_ERROR_KINDS = 3;
	public static final int INCOMPLETE_TERMINATOR = 0;
	public static final int MORE_THAN_ONE_TERMINATOR = 1;
	public static final int NOT_CONNECTED_ELEMENT = 2;
	private boolean[] errors;

	public String getMessage(int code) {
		switch (code) {
		case INCOMPLETE_TERMINATOR:
			return "Terminator tidak lengkap.";
		case MORE_THAN_ONE_TERMINATOR:
			return "Terminator lebih dari satu.";
		case NOT_CONNECTED_ELEMENT:
			return "Ada elemen yang tidak terkoneksi.";
		default:
			return "";
		}
	}

	@Override
	public void execute() {
		errors = new boolean[MAX_ERROR_KINDS];
		ISubEditor editor = MainWindow.getInstance().getEditor().getActiveSubEditor();

		IElement start = null;
		IElement end = null;
		for (IElement elem : editor.getElements()) {
			if (elem instanceof RoundedRectangle) {
				RoundedRectangle ele = (RoundedRectangle) elem;
				if (ele.getText().equals(RoundedRectangle.START)) {
					if (start == null) {
						start = ele;
					} else {
						errors[MORE_THAN_ONE_TERMINATOR] = true;
					}
				} else if (ele.getText().equals(RoundedRectangle.END)) {
					if (end == null) {
						end = ele;
					} else {
						errors[MORE_THAN_ONE_TERMINATOR] = true;
					}
				}
			}
		}

		if (start == null || end == null) {
			errors[INCOMPLETE_TERMINATOR] = true;
		}

		if (!(errors[INCOMPLETE_TERMINATOR] || errors[MORE_THAN_ONE_TERMINATOR])) {
			ArrayList<IElement> elements = (ArrayList<IElement>) editor.getElements();
			elements = (ArrayList<IElement>) elements.clone();
			Stack<IElement> stack = new Stack<IElement>();
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
			}
		}

		for (int i = 0; i < MAX_ERROR_KINDS; i++) {
			System.out.println(i + (errors[i] ? " true" : " false"));
		}
		showMessage();
	}

	public void showMessage() {
		for (int i = 0; i < MAX_ERROR_KINDS; i++) {
			if (errors[i]) {
				MessageBox dialog = new MessageBox(MainWindow.getInstance(), SWT.OK);
				dialog.setText("Validation");
				dialog.setMessage(getMessage(i));

				dialog.open();
				return;
			}
		}
		MessageBox dialog = new MessageBox(MainWindow.getInstance(), SWT.OK);
		dialog.setText("Validation");
		dialog.setMessage("Validation success");

		dialog.open();
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
