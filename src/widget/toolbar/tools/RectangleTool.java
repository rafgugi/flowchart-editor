package widget.toolbar.tools;

import org.eclipse.swt.events.MouseEvent;

import interfaces.ISubEditor;
import widget.tab.SubEditor;
import widget.toolbar.ToolStrip;
import widget.window.MainWindow;

public class RectangleTool extends ATool {

	public RectangleTool(ToolStrip parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setIconName("rectangle.png");
		super.initialize();
	}

	@Override
	public void execute() {
		MainWindow.getInstance().setStatus("Rectangle tool");
	}

	@Override
	public void mouseUp(MouseEvent e) {
		System.out.println(e.toString());
		System.out.println("Create Rectangle at (" + e.x + ", " + e.y + ")");
		ISubEditor subEditor = MainWindow.getInstance().getEditor().getActiveSubEditor();
		((SubEditor)subEditor).draw();
	}

}
