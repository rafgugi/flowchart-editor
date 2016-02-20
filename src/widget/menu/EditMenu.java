package widget.menu;

import widget.menu.menuitem.DeleteMenuItem;
import widget.menu.menuitem.SelectAllMenuItem;

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
