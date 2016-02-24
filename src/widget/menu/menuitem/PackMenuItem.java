package widget.menu.menuitem;

import command.PackDiagramCommand;
import widget.menu.AMenu;

public class PackMenuItem extends AMenuItem {

	public PackMenuItem(AMenu parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setTitle("&Pack\t\tCtrl+P");
		super.setShortcut('P');
	}

	@Override
	public void execute() {
		new PackDiagramCommand().execute();
	}

}
