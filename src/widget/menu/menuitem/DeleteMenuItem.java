package widget.menu.menuitem;

import org.eclipse.swt.SWT;

import command.DeleteElementsCommand;
import widget.menu.AMenu;

public class DeleteMenuItem extends AMenuItem {

	public DeleteMenuItem(AMenu parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setTitle("&Delete\tdel");
		super.setShortcut((int) SWT.DEL);
	}

	@Override
	public void execute() {
		new DeleteElementsCommand().execute();
	}

}
