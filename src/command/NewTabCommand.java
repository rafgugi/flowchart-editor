package command;

import interfaces.ICommand;
import widget.window.MainWindow;

public class NewTabCommand implements ICommand {

	@Override
	public void execute() {
		MainWindow.getInstance().getEditor().newSubEditor();
	}

}
