package widget.menu.menuitem;

import command.SelectAllElementsCommand;
import widget.menu.AMenu;

public class SelectAllMenuItem extends AMenuItem {

	public SelectAllMenuItem(AMenu parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setTitle("Select &All\tCtrl+A");
		super.setShortcut('A');
	}

	@Override
	public void execute() {
		new SelectAllElementsCommand().execute();
	}

}
