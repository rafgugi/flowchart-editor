package widget.menu.menuitem;

import command.NewTabCommand;
import widget.menu.AMenu;

public class NewMenuItem extends AMenuItem {

	public NewMenuItem(AMenu parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setTitle("&New\t\tCtrl+N");
		super.setShortcut('N');
	}

	@Override
	public void execute() {
		new NewTabCommand().execute();
	}

}
