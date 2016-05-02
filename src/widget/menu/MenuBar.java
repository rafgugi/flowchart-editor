package widget.menu;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;

import interfaces.IMainWindow;
import interfaces.IMenu;
import interfaces.IMenuBar;
import widget.window.MainWindow;

public class MenuBar extends Menu implements IMenuBar {

	private List<IMenu> menus = new ArrayList<IMenu>();

	public MenuBar(IMainWindow parent, int style) {
		super((MainWindow) parent, style);
		initialize();
	}

	public MenuBar(IMainWindow parent) {
		this(parent, SWT.BAR);
	}

	@Override
	public void initialize() {
		addItem(new FileMenu(this));
		addItem(new EditMenu(this));
		addItem(new DiagramMenu(this));
	}

	@Override
	public void addItem(IMenu item) {
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
