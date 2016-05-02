package widget.menu.menuitem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;

import exception.FlowchartEditorException;
import interfaces.IMenuItem;
import widget.menu.AMenu;
import widget.window.MainWindow;

public abstract class AMenuItem extends MenuItem implements IMenuItem, Listener {

	private String name;

	public AMenuItem(AMenu parent, int style) {
		super(parent, style);
		initialize();
		super.addListener(SWT.Selection, this);
	}

	public AMenuItem(AMenu parent) {
		this(parent, SWT.PUSH);
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

	protected void setShortcut(char shortcut) {
		super.setAccelerator(SWT.MOD1 + shortcut);
	}

	protected void setShortcut(int shortcut) {
		super.setAccelerator(shortcut);
	}

	@Override
	public void checkSubclass() {
	}

	@Override
	public void handleEvent(Event event) {
		try {
			execute();	
		} catch (FlowchartEditorException e) { 
			MainWindow.getInstance().setStatus(e.getMessage());
		}
	}
}
