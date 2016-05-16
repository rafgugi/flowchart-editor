package widget.menu.menuitem;

import command.ShowPADCommand;
import widget.menu.AMenu;

public class ShowPADMenuItem extends AMenuItem {

	public ShowPADMenuItem(AMenu parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setTitle("Show PA&D\tCtrl+D");
		super.setShortcut('D');
	}

	@Override
	public void execute() {
		new ShowPADCommand().execute();
	}

}
