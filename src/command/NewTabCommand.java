package command;

import diagram.Flowchart;
import interfaces.ICommand;
import interfaces.ISubEditor;
import widget.window.MainWindow;

public class NewTabCommand implements ICommand {

	@Override
	public void execute() {
		MainWindow.getInstance().getEditor().newSubEditor();
		ISubEditor editor = MainWindow.getInstance().getEditor().getActiveSubEditor();
		editor.setDiagram(Flowchart.get());
	}

}
