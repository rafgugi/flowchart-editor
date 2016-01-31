package widget.menu.menuitem;

import widget.menu.AMenu;
import widget.tab.Editor;
import widget.window.MainWindow;

public class CloseMenuItem extends AMenuItem {

	public CloseMenuItem(AMenu parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setTitle("Close\t\tCtrl+W");
		super.setShortcut('W');
	}

	@Override
	public void execute() {
		Editor editor = (Editor) MainWindow.getInstance().getEditor();
		editor.close();
	}

}
