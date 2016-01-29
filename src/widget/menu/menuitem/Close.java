package widget.menu.menuitem;

import widget.Window;
import widget.menu.AMenu;
import widget.tab.Editor;

public class Close extends AMenuItem {

	public Close(AMenu parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		System.out.println("Initialize Close");
		setTitle("Close\t\tCtrl+W");
		super.setShortcut('W');
	}

	@Override
	public void execute() {
		System.out.println("Close");
		Editor editor = (Editor) Window.getInstance().getEditor();
		editor.close();
	}

}
