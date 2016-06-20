package widget.toolbar.tools;

import java.util.ArrayList;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;

import diagram.element.Line;
import diagram.element.PolyLine;
import diagram.element.TwoDimensional;
import diagram.flowchart.FlowLine;
import exception.CreateElementException;
import exception.EmptySubEditorException;
import interfaces.IElement;
import widget.toolbar.ToolStrip;
import widget.window.MainWindow;

public class PolylineTool extends ATool {

	private TwoDimensional srcElement;
	private TwoDimensional dstElement;
	private Point firstPoint;
	private ArrayList<Point> elbows = new ArrayList<>();

	public PolylineTool(ToolStrip parent) {
		super(parent, "Polyline tool");
	}

	protected void initialize() {
		setIconName("polyarrow.png");
		super.initialize();
	}

	@Override
	public void mouseDown(MouseEvent e) {
		isDrag = false;
		try {
			IElement src = getActiveSubEditor().getElement(e.x, e.y);
			if (srcElement == null) { // jika blom ada elemen awal
				if (src != null && src instanceof TwoDimensional) {
					srcElement = (TwoDimensional) src;
					firstPoint = new Point(e.x, e.y);
					getActiveSubEditor().deselectAll();
				} else {
					throw new CreateElementException("Klik element yang akan disambungkan");
				}
			}
		} catch (CreateElementException ex) {
			MainWindow.getInstance().setStatus(ex.getMessage());
		}
		if (firstPoint != null) {
			Point temp = firstPoint;
			for (Point elbow : elbows) {
				Line.draw(temp.x, temp.y, elbow.x, elbow.y, false);
				temp = elbow;
			}
		}
	}

	@Override
	public void mouseMove(MouseEvent e) {
		if (!isDrag) {
			return;
		}
		if (srcElement != null) {
			Point lastPoint;
			if (elbows.isEmpty()) {
				lastPoint = firstPoint;
			} else {
				lastPoint = elbows.get(elbows.size() - 1);
			}
			
			getActiveSubEditor().draw();

			Point temp = firstPoint;
			for (Point elbow : elbows) {
				Line.draw(temp.x, temp.y, elbow.x, elbow.y, false);
				temp = elbow;
			}

			Line.draw(lastPoint.x, lastPoint.y, e.x, e.y, false);
		}
	}

	@Override
	public void mouseUp(MouseEvent e) {
		try {
			if (srcElement != null) {
				IElement src = getActiveSubEditor().getElement(e.x, e.y);
				if (src != null && src instanceof TwoDimensional) {
					dstElement = (TwoDimensional) src;
					if (srcElement == dstElement && elbows.isEmpty()) {
						throw new CreateElementException("Drag to connect object(s).");
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
					
					PolyLine line = new FlowLine(srcElement, dstElement);
					for (Point elbow : elbows) {
						line.addElbow(elbow);
					}
					line.select();
					getActiveSubEditor().addElement(line);
				} else {
					/* tambahin elbow nya */
					elbows.add(new Point(e.x, e.y));
				}
			}
		} catch (CreateElementException ex) {
			MainWindow.getInstance().setStatus(ex.getMessage());
		} finally {
			if (dstElement != null) {
				reset();
			}
		}
	}

	public void reset() {
		srcElement = null;
		dstElement = null;
		elbows.clear();
		firstPoint = null;
		try {
			getActiveSubEditor().draw();
		} catch (EmptySubEditorException e) {
		}
	}

	@Override
	public void deselect() {
		super.deselect();
		reset();
	}

}
