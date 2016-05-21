package widget.toolbar.tools;

import org.eclipse.swt.events.MouseEvent;

import diagram.element.Line;
import diagram.element.TwoDimensional;
import diagram.flowchart.FlowLine;
import exception.CreateElementException;
import exception.FlowchartEditorException;
import interfaces.IElement;
import widget.toolbar.ToolStrip;
import widget.window.MainWindow;

public class LineTool extends ATool {

	private TwoDimensional srcElement;
	private TwoDimensional dstElement;
	private MouseEvent downTemp;

	public LineTool(ToolStrip parent, String name) {
		super(parent, name);
	}

	public LineTool(ToolStrip parent) {
		super(parent, "Line tool");
	}

	protected void initialize() {
		setIconName("arrow.png");
		super.initialize();
		isDrag = false;
	}

	@Override
	public void mouseDown(MouseEvent e) {
		srcElement = null;
		dstElement = null;
		IElement src = getActiveSubEditor().getElement(e.x, e.y);
		if (src != null && src instanceof TwoDimensional) {
			srcElement = (TwoDimensional) src;
			getActiveSubEditor().deselectAll();
			getActiveSubEditor().draw();
		}
		downTemp = e;
	}

	@Override
	public void mouseMove(MouseEvent e) {
		if (!isDrag) {
			return;
		}

		getActiveSubEditor().draw();

		Line.draw(downTemp.x, downTemp.y, e.x, e.y, true);
	}

	@Override
	public void mouseUp(MouseEvent e) {
		try {
			if (!isDrag) {
				throw new CreateElementException("Drag to draw object.");
			}
			IElement dst = getActiveSubEditor().getElement(e.x, e.y);
			if (dst != null && dst instanceof TwoDimensional) {
				dstElement = (TwoDimensional) dst;
			}
			/* Yang diklik itu ada element nya apa nggak */
			if (srcElement == null || dstElement == null || srcElement == dstElement) {
				throw new CreateElementException("Connect element exactly.");
			}
			/* Cek apakeh element udah tersambung */
			boolean alredy = false;
			for (IElement element : getActiveSubEditor().getElements()) {
				if (element instanceof Line) {
					Line flow = (Line) element;
					int isSrc = flow.checkConnected(srcElement);
					int isDst = flow.checkConnected(dstElement);
					alredy = isSrc == Line.CONNECTED_SRC && isDst == Line.CONNECTED_DST;
				}
			}
			if (alredy) {
				throw new CreateElementException("Object alredy connected.");
			}

			FlowLine flowLine = null;
			try {
				flowLine = new FlowLine(srcElement, dstElement);
				getActiveSubEditor().addElement(flowLine);
				flowLine.select();
			} catch (FlowchartEditorException ex) {
				MainWindow.getInstance().setStatus(ex.getMessage());
			} finally {
				if (flowLine == null) {
					// jika gagal maka ngapain?
				}
			}
		} catch (CreateElementException ex) {
			MainWindow.getInstance().setStatus(ex.getMessage());
		}
		getActiveSubEditor().draw();
		isDrag = false;
		downTemp = null;
	}

}
