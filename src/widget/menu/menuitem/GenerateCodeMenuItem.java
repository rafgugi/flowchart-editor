package widget.menu.menuitem;

import command.GenerateCodeCommand;
import widget.menu.AMenu;

public class GenerateCodeMenuItem extends AMenuItem {

	public GenerateCodeMenuItem(AMenu parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setTitle("&Generate Code\t\tCtrl+G");
		super.setShortcut('G');
	}

	@Override
	public void execute() {
		new GenerateCodeCommand().execute();
	}

}
