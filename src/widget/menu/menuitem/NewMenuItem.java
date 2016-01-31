package widget.menu.menuitem;

import widget.menu.AMenu;
import widget.tab.Editor;
import widget.window.MainWindow;

public class NewMenuItem extends AMenuItem {

	public NewMenuItem(AMenu parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setTitle("&New\t\tCtrl+N");
		super.setShortcut('N');
	}

	@Override
	public void execute() {
		Editor editor = (Editor) MainWindow.getInstance().getEditor();
		editor.newSubEditor();
	}

}
