package widget.menu;

import widget.menu.menuitem.*;

public class FileMenu extends AMenu {

	public FileMenu(MenuBar parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setTitle("File");
		addItem(new NewMenuItem(this));
		addItem(new CloseMenuItem(this));
	}

}
