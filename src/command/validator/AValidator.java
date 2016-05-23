package command.validator;

import java.util.List;

import interfaces.IDiagramValidator;
import interfaces.IElement;
import interfaces.IValidationItem;
import widget.window.MainWindow;

public abstract class AValidator implements IDiagramValidator {

	/**
	 * Helps get all elements in active sub editor.
	 * 
	 * @return elements
	 */
	protected List<IElement> getAllElements() {
		return MainWindow.getInstance().getEditor().getActiveSubEditor().getElements();
	}

	/**
	 * Helps add a validation item
	 * 
	 * @param validationi item
	 */
	protected void addValidationItem(IValidationItem item) {
		MainWindow.getInstance().getValidationList().addItem(item);
	}

}
