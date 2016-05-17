package widget.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ScrollBar;

import interfaces.ICanvas;

public class Canvas extends org.eclipse.swt.widgets.Canvas implements ICanvas {

	/* Widgets */
	private ScrollBar hBar;
	private ScrollBar vBar;
	private SubEditor parent;
	private GC gc;

	/* Drawing color */
	private Color fgColor = new Color(0, 0, 0);
	private Color bgColor = new Color(255, 255, 255);

	/* Translation for every drawing element */
	private int translateX = 0;
	private int translateY = 0;

	public Canvas(SubEditor parent) {
		super(parent.getComposite(), SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		this.parent = parent;
		initialize();
	}

	public void initialize() {
//		super.setSize(500, 500);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.minimumHeight = 320;
		gridData.minimumWidth = 480;
//		super.setLayoutData(gridData);

		hBar = super.getHorizontalBar();
		vBar = super.getVerticalBar();

		super.addMouseListener(parent);
		super.addMouseMoveListener(parent);
		super.addDragDetectListener(parent);
		super.addPaintListener(parent);
	}

	@Override
	public void checkSubclass() {
	}

	private GC getGC() {
		if (gc == null || gc.isDisposed()) {
			gc = new GC(this);
			org.eclipse.swt.graphics.Color temp;
			temp = new org.eclipse.swt.graphics.Color(gc.getDevice(), fgColor.r, fgColor.g, fgColor.b);
			gc.setForeground(temp);
			temp = new org.eclipse.swt.graphics.Color(gc.getDevice(), bgColor.r, bgColor.g, bgColor.b);
			gc.setBackground(temp);
		}
		return gc;
	}

	@Override
	public void setFgColor(int r, int g, int b) {
		fgColor.r = r;
		fgColor.g = g;
		fgColor.b = b;
	}

	@Override
	public void setBgColor(int r, int g, int b) {
		bgColor.r = r;
		bgColor.g = g;
		bgColor.b = b;
	}

	@Override
	public void clear() {
		gc = getGC();
		setBgColor(255, 255, 255);
		gc.fillRectangle(0, 0, super.getSize().x, super.getSize().y);
		gc.dispose();
	}

	@Override
	public void drawLine(int srcx, int srcy, int dstx, int dsty) {
		GC gc = getGC();
		gc.drawLine(srcx + translateX, srcy + translateY, dstx + translateX, dsty + translateY);
		gc.dispose();
	}

	@Override
	public void drawOval(int x, int y, int w, int h) {
		GC gc = getGC();
		gc.drawOval(x + translateX, y + translateY, w, h);
		gc.dispose();
	}

	@Override
	public void fillOval(int x, int y, int w, int h) {
		GC gc = getGC();
		gc.fillOval(x + translateX, y + translateY, w, h);
		gc.dispose();
	}

	@Override
	public void drawPolygon(int[] points) {
		GC gc = getGC();
		for (int i = 0; i < points.length; i++) {
			points[i] = points[i] + (i % 2 == 0 ? translateX : translateY);
		}
		gc.drawPolygon(points);
		gc.dispose();
	}

	@Override
	public void fillPolygon(int[] points) {
		GC gc = getGC();
		for (int i = 0; i < points.length; i++) {
			points[i] = points[i] + (i % 2 == 0 ? translateX : translateY);
		}
		gc.fillPolygon(points);
		gc.dispose();
	}

	@Override
	public void drawRectangle(int x, int y, int w, int h) {
		GC gc = getGC();
		gc.drawRectangle(x + translateX, y + translateY, w, h);
		gc.dispose();
	}

	@Override
	public void fillRectangle(int x, int y, int w, int h) {
		GC gc = getGC();
		gc.fillRectangle(x + translateX, y + translateY, w, h);
		gc.dispose();
	}

	@Override
	public void drawRoundRectangle(int x, int y, int w, int h, int radX, int radY) {
		GC gc = getGC();
		gc.drawRoundRectangle(x + translateX, y + translateY, w, h, radX, radY);
		gc.dispose();
	}

	@Override
	public void fillRoundRectangle(int x, int y, int w, int h, int radX, int radY) {
		GC gc = getGC();
		gc.fillRoundRectangle(x + translateX, y + translateY, w, h, radX, radY);
		gc.dispose();
	}

	@Override
	public void drawText(String text, int x, int y) {
		GC gc = getGC();
		gc.drawText(text, x + translateX, y);
		gc.dispose();
	}

	@Override
	public int[] stringExtent(String text) {
		Point p = getGC().stringExtent(text);
		return new int[] { p.x, p.y };
	}

}
