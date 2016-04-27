package widget.menu;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import interfaces.IMenu;
import interfaces.IMenuItem;

public abstract class AMenu extends Menu implements IMenu {

	private String name;
	private List<IMenuItem> menuItems = new ArrayList<>();
	private MenuItem menu;

	public AMenu(MenuBar parent, int style) {
		super(parent.getShell(), SWT.DROP_DOWN);

		menu = new MenuItem(parent, style);
		menu.setMenu(this);
		initialize();
	}

	public AMenu(MenuBar parent) {
		this(parent, SWT.CASCADE);
	}

	@Override
	public abstract void initialize();

	@Override
	public void addItem(IMenuItem item) {
		menuItems.add(item);
	}

	@Override
	public List<IMenuItem> getMenuItems() {
		return menuItems;
	}

	@Override
	public String getTitle() {
		return name;
	}

	@Override
	public void setTitle(String name) {
		this.name = name;
		menu.setText(name);
	}

	@Override
	public void checkSubclass() {
	}

}
