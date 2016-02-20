package widget.menu.menuitem;

import command.DeleteElementsCommand;
import widget.menu.AMenu;

public class DeleteMenuItem extends AMenuItem {

	public DeleteMenuItem(AMenu parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setTitle("&Delete\tCtrl+D");
		super.setShortcut('D');
	}

	@Override
	public void execute() {
		new DeleteElementsCommand().execute();
	}

}
