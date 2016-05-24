package widget.menu;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;

import interfaces.IMenu;
import interfaces.IMenuBar;
import widget.window.MainWindow;

public class MenuBar extends Menu implements IMenuBar {

	private List<IMenu> menus = new ArrayList<IMenu>();

	public MenuBar(MainWindow parent) {
		super(parent, SWT.BAR);
		initialize();
	}

	protected void initialize() {
		addMenu(new FileMenu(this));
		addMenu(new EditMenu(this));
		addMenu(new DiagramMenu(this));
	}

	@Override
	public void addMenu(IMenu item) {
		menus.add(item);
	}

	@Override
	public List<IMenu> getMenus() {
		return menus;
	}

	@Override
	public void checkSubclass() {
	}
}
