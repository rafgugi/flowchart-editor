package widget.toolbar.tools;

import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.MouseEvent;

import interfaces.IElement;
import widget.toolbar.ToolStrip;
import widget.window.MainWindow;

public class PointerTool extends ATool {

	private boolean isDrag;

	public PointerTool(ToolStrip parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setIconName("pointer.png");
		super.initialize();
	}

	@Override
	public void execute() {
		MainWindow.getInstance().setStatus("Pointer tool");
	}

	@Override
	public void mouseDown(MouseEvent e) {
		isDrag = false;
	}

	@Override
	public void mouseUp(MouseEvent e) {
		if (!isDrag) {
			IElement element = getActiveSubEditor().getElement(e.x, e.y);
			getActiveSubEditor().deselectAll();
			if (element != null) {
				element.select();
				getActiveSubEditor().draw();
			}
		}
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		// subEditor.clear();
	}

	@Override
	public void dragDetected(DragDetectEvent e) {
		isDrag = true;
	}

}
