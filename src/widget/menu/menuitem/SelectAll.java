package widget.menu.menuitem;

import org.eclipse.swt.SWT;

import widget.menu.AMenu;

public class SelectAll extends AMenuItem {

	public SelectAll(AMenu parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		System.out.println("Initialize SelectAllMenuItem");
		setTitle("Select &All\tCtrl+A");
    	super.setAccelerator(SWT.MOD1 + 'A');
	}

	@Override
	public void execute() {
		System.out.println("Select All");
	}

}
