package widget.toolbar.tools;

import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;

import diagram.element.RoundedRectangle;
import interfaces.IElement;
import widget.toolbar.ToolStrip;

public class EllipseTool extends ATool {

	private boolean isDrag;
	private MouseEvent downTemp;

	public EllipseTool(ToolStrip parent, String name) {
		super(parent, name);
	}

	public EllipseTool(ToolStrip parent) {
		super(parent, "Ellipse tool");
	}
	
	@Override
	public void initialize() {
		setIconName("terminator.png");
		super.initialize();
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
			IElement rect = new RoundedRectangle(src, dst);
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
