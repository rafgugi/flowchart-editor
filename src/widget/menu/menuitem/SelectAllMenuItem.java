package widget.menu.menuitem;

import widget.menu.AMenu;
import widget.window.MainWindow;

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
		MainWindow.getInstance().setStatus("Select All");
	}

}
