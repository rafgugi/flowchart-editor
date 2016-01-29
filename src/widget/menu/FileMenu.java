package widget.menu;

import widget.menu.menuitem.*;

public class FileMenu extends AMenu {

	public FileMenu(MenuBar parent) {
		super(parent);
		System.out.println("Constructornya FileMenu");
	}

	@Override
	public void initialize() {
		System.out.println("Initialize FileMenu");
		setTitle("File");
		addItem(new New(this));
		addItem(new Close(this));
	}

}
