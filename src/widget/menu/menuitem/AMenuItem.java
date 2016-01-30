package widget.menu.menuitem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import interfaces.IMenuItem;
import widget.menu.AMenu;

public abstract class AMenuItem extends MenuItem implements IMenuItem, Listener {

	private String name;

	public AMenuItem(Menu parent, int style) {
		super(parent, style);
		initialize();
		super.addListener(SWT.Selection, this);
	}

	public AMenuItem(AMenu parent) {
		this(parent.getDropDown(), SWT.PUSH);
	}

	@Override
	public String getTitle() {
		return name;
	}

	@Override
	public void setTitle(String name) {
		super.setText(name);
		this.name = name;
	}
	
	public void setShortcut(char shortcut) {
		super.setAccelerator(SWT.MOD1 + shortcut);
	}

	@Override
	public void checkSubclass() {
	}

	@Override
	public void handleEvent(Event event) {
		execute();
	}
}
