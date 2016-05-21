package widget.menu.menuitem;

import command.SaveCommand;
import widget.menu.AMenu;

public class SaveMenuItem extends AMenuItem {

	public SaveMenuItem(AMenu parent) {
		super(parent);
	}

	protected void initialize() {
		setTitle("Save\t\tCtrl+S");
		super.setShortcut('S');
	}

	@Override
	public void execute() {
		new SaveCommand().execute();
	}

}
