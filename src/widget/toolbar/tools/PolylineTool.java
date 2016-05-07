package widget.toolbar.tools;

import java.util.ArrayList;

import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

import diagram.element.Line;
import diagram.element.PolyLine;
import diagram.element.TwoDimensional;
import diagram.flowchart.FlowLine;
import exception.CreateElementException;
import interfaces.IElement;
import widget.tab.SubEditor;
import widget.toolbar.ToolStrip;
import widget.window.MainWindow;

public class PolylineTool extends ATool {

	private TwoDimensional srcElement;
	private TwoDimensional dstElement;
	private Point firstPoint;
	private ArrayList<Point> elbows = new ArrayList<>();

	public PolylineTool(ToolStrip parent, String name) {
		super(parent, name);
	}

	public PolylineTool(ToolStrip parent) {
		super(parent, "Polyline tool");
	}

	@Override
	public void initialize() {
		setIconName("polyarrow.png");
		super.initialize();
	}

	@Override
	public void mouseDown(MouseEvent e) {
		try {
			IElement src = getActiveSubEditor().getElement(e.x, e.y);
			if (srcElement == null) { // jika blom ada elemen awal
				if (src != null && src instanceof TwoDimensional) {
					srcElement = (TwoDimensional) src;
					firstPoint = new Point(e.x, e.y);
					getActiveSubEditor().deselectAll();
					getActiveSubEditor().draw();
				} else {
					throw new CreateElementException("Klik titik titik pembentuk garis.");
				}
			} else {
				if (src != null && src instanceof TwoDimensional) {
					dstElement = (TwoDimensional) src;
				} else { // jika klik sembarang area, bikin elbow!
					elbows.add(new Point(e.x, e.y));
				}
			}
		} catch (CreateElementException ex) {
			MainWindow.getInstance().setStatus(ex.getMessage());
		}
		if (firstPoint != null) {
			SubEditor s = (SubEditor) getActiveSubEditor();
			GC gc = s.getGC();
			Color black = new Color(gc.getDevice(), 0, 0, 0);
			gc.setForeground(black);
			gc.setBackground(black);
			
			Point temp = firstPoint;
			for (Point elbow : elbows) {
				Line.draw(gc, temp.x, temp.y, elbow.x, elbow.y, false);
				temp = elbow;
			}
		}
	}

	@Override
	public void mouseUp(MouseEvent e) {
		try {
			if (srcElement != null && dstElement != null) {
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
		getActiveSubEditor().draw();
	}

	@Override
	public void dragDetected(DragDetectEvent e) {
	}

	@Override
	public void deselect() {
		super.deselect();
		reset();
	}

}
