package widget.menu.menuitem;

import widget.MainWindow;
import widget.menu.AMenu;
import widget.tab.Editor;

public class New extends AMenuItem {

	public New(AMenu parent) {
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
