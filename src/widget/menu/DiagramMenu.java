package widget.menu;

import widget.menu.menuitem.*;

public class DiagramMenu extends AMenu {

	public DiagramMenu(MenuBar parent) {
		super(parent);
	}

	protected void initialize() {
		setTitle("Diagram");
		addItem(new PropertiesMenuItem(this));
		addItem(new PackMenuItem(this));
		addItem(new ValidateMenuItem(this));
		addItem(new GenerateCodeMenuItem(this));
		addItem(new ShowPADMenuItem(this));
	}

}
