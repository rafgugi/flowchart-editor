package widget.menu.menuitem;

import widget.menu.AMenu;

public class SelectAll extends AMenuItem {

	public SelectAll(AMenu parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setTitle("Select &All\tCtrl+A");
		super.setShortcut('A');
	}

	@Override
	public void execute() {
		System.out.println("Select All");
	}

}
