package widget.toolbar.tools;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;

import diagram.element.RoundedRectangle;
import diagram.flowchart.Terminator;
import exception.CreateElementException;
import interfaces.IElement;
import main.Main;
import widget.toolbar.ToolStrip;

public class TerminatorTool extends ATool {

	private MouseEvent downTemp;

	public TerminatorTool(ToolStrip parent, String name) {
		super(parent, name);
	}

	public TerminatorTool(ToolStrip parent) {
		super(parent, "Terminator tool");
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
	public void mouseMove(MouseEvent e) {
		if (!isDrag) {
			return;
		}
		
		getActiveSubEditor().draw();

		RoundedRectangle.draw(Math.min(downTemp.x, e.x), Math.min(downTemp.y, e.y), 
				Math.abs(downTemp.x - e.x), Math.abs(downTemp.y - e.y));
	}

	@Override
	public void mouseUp(MouseEvent e) {
		try {
			if (!isDrag) {
				throw new CreateElementException("Drag to draw object.");
			}
			Point src = new Point(downTemp.x, downTemp.y);
			Point dst = new Point(e.x, e.y);
			IElement rect = new Terminator(new RoundedRectangle(src, dst));
			getActiveSubEditor().addElement(rect);
			rect.select();
			getActiveSubEditor().draw();
		} catch (CreateElementException ex) {
			Main.log(ex.getMessage());
		}
		isDrag = false;
		downTemp = null;
	}

}
