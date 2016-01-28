package widget.menu;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Menu;

import interfaces.IMenu;
import interfaces.IMenuBar;
import interfaces.IWindow;

public class MenuBar extends Menu implements IMenuBar {

	private String name;
	private List<IMenu> menus;

	public MenuBar(IWindow parent, int style) {
		super((Decorations) parent, style);
		parent.setBar(this);
		menus = new ArrayList<IMenu>();
		System.out.println("Constructornya MenuBar");
		initialize();
	}

	public MenuBar(IWindow parent) {
		this(parent, SWT.BAR);
	}

	@Override
	public void initialize() {
		System.out.println("Initialize MenuBar");
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
	public String getTitle() {
		return name;
	}

	@Override
	public void setTitle(String name) {
		this.name = name;
	}

	@Override
	public void checkSubclass() {
	}
}
