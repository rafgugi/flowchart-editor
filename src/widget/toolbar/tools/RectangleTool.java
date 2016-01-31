package widget.toolbar.tools;

import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;

import diagram.element.Rectangle;
import interfaces.IElement;
import interfaces.ISubEditor;
import widget.toolbar.ToolStrip;
import widget.window.MainWindow;

public class RectangleTool extends ATool {

	private boolean isDrag;
	private MouseEvent downTemp;

	public RectangleTool(ToolStrip parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setIconName("rectangle.png");
		super.initialize();
		isDrag = false;
	}

	@Override
	public void execute() {
		MainWindow.getInstance().setStatus("Rectangle tool");
	}

	@Override
	public void mouseDown(MouseEvent e) {
		System.out.println("mouseDown");
		downTemp = e;
		isDrag = false;
	}

	@Override
	public void mouseUp(MouseEvent e) {
		System.out.println("mouseUp");
		ISubEditor subEditor = MainWindow.getInstance().getEditor().getActiveSubEditor();
		if (isDrag) {
			Point src = new Point(downTemp.x, downTemp.y);
			Point dst = new Point(e.x, e.y);
			IElement rect = new Rectangle(src, dst);
			subEditor.addElement(rect);
		} else {
			System.out.println("Drag to draw object.");
		}
	}

	@Override
	public void dragDetected(DragDetectEvent e) {
		System.out.println("dragDetected");
		isDrag = true;
	}

}
