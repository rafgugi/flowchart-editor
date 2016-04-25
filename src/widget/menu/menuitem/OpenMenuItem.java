package widget.menu.menuitem;

import command.CloseTabCommand;
import widget.menu.AMenu;

public class OpenMenuItem extends AMenuItem {

	public OpenMenuItem(AMenu parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setTitle("&Open\t\tCtrl+O");
		super.setShortcut('O');
	}

	@Override
	public void execute() {
		new CloseTabCommand().execute();
	}

}
