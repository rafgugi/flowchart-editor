package widget.menu.menuitem;

import command.OpenCommand;
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
		new OpenCommand().execute();
	}

}
