package widget.menu;

import widget.menu.menuitem.PackMenuItem;
import widget.menu.menuitem.PropertiesMenuItem;
import widget.menu.menuitem.ValidateMenuItem;

public class DiagramMenu extends AMenu {

	public DiagramMenu(MenuBar parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setTitle("Diagram");
		addItem(new PackMenuItem(this));
		addItem(new ValidateMenuItem(this));
		addItem(new PropertiesMenuItem(this));
	}

}
