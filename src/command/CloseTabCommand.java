package command;

import interfaces.ICommand;
import widget.window.MainWindow;

public class CloseTabCommand implements ICommand {

	@Override
	public void execute() {
		MainWindow.getInstance().getEditor().close();
	}

}
