package widget.menu.menuitem;

import command.ElementPropertiesCommand;
import widget.menu.AMenu;

public class PropertiesMenuItem extends AMenuItem {

	public PropertiesMenuItem(AMenu parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setTitle("Properties\t\tCtrl+L");
		super.setShortcut('L');
	}

	@Override
	public void execute() {
		new ElementPropertiesCommand().execute();
	}

}
