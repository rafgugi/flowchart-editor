package widget.menu.menuitem;

import widget.MainWindow;
import widget.menu.AMenu;
import widget.tab.Editor;

public class Close extends AMenuItem {

	public Close(AMenu parent) {
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
