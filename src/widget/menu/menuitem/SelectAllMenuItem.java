package widget.menu.menuitem;

import interfaces.IElement;
import interfaces.ISubEditor;
import widget.menu.AMenu;
import widget.window.MainWindow;

public class SelectAllMenuItem extends AMenuItem {

	public SelectAllMenuItem(AMenu parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setTitle("Select &All\tCtrl+A");
		super.setShortcut('A');
	}

	@Override
	public void execute() {
		ISubEditor subEditor;
		subEditor = MainWindow.getInstance().getEditor().getActiveSubEditor();
		for (IElement element : subEditor.getElements()) {
			element.select();
		}
		subEditor.draw();
	}

}
