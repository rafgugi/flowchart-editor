package widget.menu;

import widget.menu.menuitem.*;

public class FileMenu extends AMenu {

	public FileMenu(MenuBar parent) {
		super(parent);
	}

	protected void initialize() {
		setTitle("File");
		addItem(new NewMenuItem(this));
		addItem(new OpenMenuItem(this));
		addItem(new SaveMenuItem(this));
		addItem(new CloseMenuItem(this));
	}

}
