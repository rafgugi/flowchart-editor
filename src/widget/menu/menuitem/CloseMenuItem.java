package widget.menu.menuitem;

import command.CloseTabCommand;
import widget.menu.AMenu;

public class CloseMenuItem extends AMenuItem {

	public CloseMenuItem(AMenu parent) {
		super(parent);
	}

	protected void initialize() {
		setTitle("Close\t\tCtrl+W");
		super.setShortcut('W');
	}

	@Override
	public void execute() {
		new CloseTabCommand().execute();
	}

}
