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

	private List<IMenu> menus;

	public MenuBar(IMainWindow parent, int style) {
		super((MainWindow) parent, style);
		parent.setBar(this);
		menus = new ArrayList<IMenu>();
		initialize();
	}

	public MenuBar(IMainWindow parent) {
		this(parent, SWT.BAR);
	}

	@Override
	public void initialize() {
		addItem(new FileMenu(this));
		addItem(new EditMenu(this));
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
	public void setMenus(List<IMenu> items) {
		this.menus = items;
	}

	@Override
	public void checkSubclass() {
	}
}
