package widget.toolbar.tools;

import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.MouseEvent;
import diagram.element.FlowLine;
import diagram.element.TwoDimensional;
import interfaces.IElement;
import widget.toolbar.ToolStrip;
import widget.window.MainWindow;

public class LineTool extends ATool {

	private boolean isDrag;
	private TwoDimensional srcElement;
	private TwoDimensional dstElement;

	public LineTool(ToolStrip parent) {
		super(parent);
	}

	@Override
	public void initialize() {
		setIconName("arrow.png");
		super.initialize();
		isDrag = false;
	}

	@Override
	public void execute() {
		System.out.println("line");
		MainWindow.getInstance().setStatus("Line tool");
	}

	@Override
	public void mouseDown(MouseEvent e) {
		srcElement = null;
		dstElement = null;
		isDrag = false;
		IElement src = getActiveSubEditor().getElement(e.x, e.y);
		if (src != null && src instanceof TwoDimensional) {
			srcElement = (TwoDimensional) src;
			getActiveSubEditor().deselectAll();
			getActiveSubEditor().draw();
		}
	}

	@Override
	public void mouseUp(MouseEvent e) {
		if (isDrag) {
			IElement dst = getActiveSubEditor().getElement(e.x, e.y);
			if (dst != null && dst instanceof TwoDimensional) {
				dstElement = (TwoDimensional) dst;
			}
			if (srcElement == null || dstElement == null || srcElement == dstElement) {
				System.out.println("Connect element properly.");
			} else {
				FlowLine flowLine = new FlowLine(srcElement, dstElement);
				flowLine.select();
				getActiveSubEditor().addElement(flowLine);
			}
		} else {
			System.out.println("Drag to draw object.");
		}
	}

	@Override
	public void dragDetected(DragDetectEvent e) {
		isDrag = true;
	}

}
