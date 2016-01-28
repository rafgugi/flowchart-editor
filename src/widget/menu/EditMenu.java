package widget.menu;

import widget.menu.menuitem.SelectAll;

public class EditMenu extends AMenu {

	public EditMenu(MenuBar parent) {
		super(parent);
		System.out.println("Constructornya EditMenu");
	}

	@Override
	public void initialize() {
		System.out.println("Initialize EditMenu");
		setTitle("Edit");
		addItem(new SelectAll(this));
	}

}
