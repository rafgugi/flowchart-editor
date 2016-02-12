package widget.toolbar.tools;

import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;

import diagram.element.Rectangle;
import interfaces.IElement;
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
		setIconName("process.png");
		super.initialize();
		isDrag = false;
	}

	@Override
	public void execute() {
		MainWindow.getInstance().setStatus("Rectangle tool");
	}

	@Override
	public void mouseDown(MouseEvent e) {
		downTemp = e;
		isDrag = false;
		getActiveSubEditor().deselectAll();
	}

	@Override
	public void mouseUp(MouseEvent e) {
		if (isDrag) {
			Point src = new Point(downTemp.x, downTemp.y);
			Point dst = new Point(e.x, e.y);
			IElement rect = new Rectangle(src, dst);
			rect.select();
			getActiveSubEditor().addElement(rect);
		} else {
			System.out.println("Drag to draw object.");
		}
	}

	@Override
	public void dragDetected(DragDetectEvent e) {
		isDrag = true;
	}

}
