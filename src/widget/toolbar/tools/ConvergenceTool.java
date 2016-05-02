package widget.toolbar.tools;

import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

import diagram.element.Ellipse;
import diagram.flowchart.Convergence;
import exception.CreateElementException;
import interfaces.IElement;
import widget.tab.SubEditor;
import widget.toolbar.ToolStrip;

public class ConvergenceTool extends ATool {

	private boolean isDrag;
	private MouseEvent downTemp;

	public ConvergenceTool(ToolStrip parent, String name) {
		super(parent, name);
	}

	public ConvergenceTool(ToolStrip parent) {
		super(parent, "Convergence tool");
	}
	
	@Override
	public void initialize() {
		setIconName("ellipse.png");
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
		GC gc = ((SubEditor) getActiveSubEditor()).getGC();
		
		getActiveSubEditor().draw();

		Ellipse.draw(gc, Math.min(downTemp.x, e.x), Math.min(downTemp.y, e.y), 
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
			IElement rect = new Convergence(src, dst);
			getActiveSubEditor().addElement(rect);
			rect.select();
			getActiveSubEditor().draw();
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
