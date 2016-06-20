package command;

import diagram.element.TwoDimensional;
import exception.ElementNotFoundException;
import interfaces.ICommand;
import interfaces.IElement;
import interfaces.ISubEditor;
import widget.window.MainWindow;

public class DeleteElementsCommand implements ICommand {

	@Override
	public void execute() {
		ISubEditor subEditor;
		subEditor = MainWindow.getInstance().getEditor().getActiveSubEditor();
		if (subEditor.getSelectedElements().isEmpty()) {
			throw new ElementNotFoundException("No element selected.");
		}
		for (IElement element : subEditor.getSelectedElements()) {
			for (IElement connected : element.getConnectedElements()) {
				connected.disconnect(element);
				if (!(connected instanceof TwoDimensional)) {
					subEditor.removeElement(connected);
				}
			}
			subEditor.removeElement(element);
		}
	}

}
