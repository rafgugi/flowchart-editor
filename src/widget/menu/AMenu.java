package widget.menu;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import interfaces.IMenu;
import interfaces.IMenuItem;

public abstract class AMenu extends MenuItem implements IMenu {

	private String name;
	private List<IMenuItem> menuItems;
	private Menu dropDown;

	public AMenu(MenuBar parent, int style) {
		super(parent, style);
		
		dropDown = new Menu(parent.getShell(), SWT.DROP_DOWN);
		super.setMenu(dropDown);
		
		menuItems = new ArrayList<>();
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
	public List<IMenuItem> getItems() {
		return menuItems;
	}

	@Override
	public void setItems(List<IMenuItem> items) {
		this.menuItems = items;
	}

	public Menu getDropDown() {
		return dropDown;
	}

	public void setDropDown(Menu dropDown) {
		this.dropDown = dropDown;
	}

	@Override
	public String getTitle() {
		return name;
	}

	@Override
	public void setTitle(String name) {
		this.name = name;
		super.setText(name);
	}

	@Override
	public void checkSubclass() {
	}

}
