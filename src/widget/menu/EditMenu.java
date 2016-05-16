package widget.menu;

import widget.menu.menuitem.*;

public class EditMenu extends AMenu {

	public EditMenu(MenuBar parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setTitle("Edit");
		addItem(new SelectAllMenuItem(this));
		addItem(new DeleteMenuItem(this));
	}

}
