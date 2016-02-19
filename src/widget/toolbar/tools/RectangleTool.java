package widget.toolbar.tools;

import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

import diagram.element.Rectangle;
import exception.CreateElementException;
import interfaces.IElement;
import widget.tab.SubEditor;
import widget.toolbar.ToolStrip;
import widget.window.MainWindow;

public class RectangleTool extends ATool {

	private boolean isDrag;
	private MouseEvent downTemp;

	public RectangleTool(ToolStrip parent, String name) {
		super(parent, name);
	}

	public RectangleTool(ToolStrip parent) {
		super(parent, "Rectangle tool");
	}

	@Override
	public void initialize() {
		setIconName("process.png");
		super.initialize();
	}

	@Override
	public void mouseDown(MouseEvent e) {
		downTemp = e;
		isDrag = false;
		getActiveSubEditor().deselectAll();
	}

	@Override
	public void mouseMove(MouseEvent e) {
		if (!isDrag) {
			return;
		}
		SubEditor s;
		s = (SubEditor) MainWindow.getInstance().getEditor().getActiveSubEditor();
		GC gc = s.getGC();
		
		MainWindow.getInstance().getEditor().getActiveSubEditor().draw();

		Rectangle.draw(gc, Math.min(downTemp.x, e.x), Math.min(downTemp.y, e.y), 
				Math.abs(downTemp.x - e.x), Math.abs(downTemp.y - e.y));
		gc.dispose();
	}

	@Override
	public void mouseUp(MouseEvent e) {
		try {
			if (!isDrag) {
				throw new CreateElementException("Drag to draw object.");
			}
			Point src = new Point(downTemp.x, downTemp.y);
			Point dst = new Point(e.x, e.y);
			IElement rect = new Rectangle(src, dst);
			rect.select();
			getActiveSubEditor().addElement(rect);
		} catch (CreateElementException ex) {
			System.out.println(ex.getMessage());
		}
		isDrag = false;
		downTemp = null;
	}

	@Override
	public void dragDetected(DragDetectEvent e) {
		isDrag = true;
	}

}
