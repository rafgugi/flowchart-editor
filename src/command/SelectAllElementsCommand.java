package command;

import exception.EmptySubEditorException;
import interfaces.ICommand;
import interfaces.IElement;
import interfaces.ISubEditor;
import widget.window.MainWindow;

public class SelectAllElementsCommand implements ICommand {

	@Override
	public void execute() {
		ISubEditor subEditor;
		subEditor = MainWindow.getInstance().getEditor().getActiveSubEditor();
		for (IElement element : subEditor.getElements()) {
			element.select();
		}
		subEditor.draw();
	}

}
