package widget.menu.menuitem;

import command.ValidateDiagramCommand;
import widget.menu.AMenu;

public class ValidateMenuItem extends AMenuItem {

	public ValidateMenuItem(AMenu parent) {
		super(parent);
	}

	protected void initialize() {
		setTitle("Validate\t\tCtrl+H");
		super.setShortcut('H');
	}

	@Override
	public void execute() {
		new ValidateDiagramCommand().execute();
	}

}
