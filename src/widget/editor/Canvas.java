package widget.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import interfaces.ICanvas;
import main.Main;

public class Canvas extends org.eclipse.swt.widgets.Canvas implements ICanvas, Listener {

	public final static int MAX_SIZE_X = 2000;
	public final static int MAX_SIZE_Y = 2000;

	/* Widgets */
	private Scroller hBar;
	private Scroller vBar;
	private SubEditor parent;
	private GC gc;

	/* Drawing color */
	private Color fgColor = new Color(0, 0, 0);
	private Color bgColor = new Color(255, 255, 255);

	public Canvas(SubEditor parent) {
		super(parent.getParent(), SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		this.parent = parent;
		initialize();
	}

	protected void initialize() {
		hBar = new Scroller(this, super.getHorizontalBar());
		vBar = new Scroller(this, super.getVerticalBar());

		super.addMouseListener(parent);
		super.addMouseMoveListener(parent);
		super.addDragDetectListener(parent);
		super.addPaintListener(parent);
		super.addListener(SWT.Resize, this);
	}

	@Override
	public void checkSubclass() {
	}

	private GC getGC() {
		if (gc == null || gc.isDisposed()) {
			gc = new GC(this);
		}
		org.eclipse.swt.graphics.Color temp;
		temp = new org.eclipse.swt.graphics.Color(gc.getDevice(), fgColor.r, fgColor.g, fgColor.b);
		gc.setForeground(temp);
		temp = new org.eclipse.swt.graphics.Color(gc.getDevice(), bgColor.r, bgColor.g, bgColor.b);
		gc.setBackground(temp);
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
		gc.drawLine(srcx + getTranslateX(), srcy + getTranslateY(), dstx + getTranslateX(), dsty + getTranslateY());
		gc.dispose();
	}

	@Override
	public void drawOval(int x, int y, int w, int h) {
		GC gc = getGC();
		gc.drawOval(x + getTranslateX(), y + getTranslateY(), w, h);
		gc.dispose();
	}

	@Override
	public void fillOval(int x, int y, int w, int h) {
		GC gc = getGC();
		gc.fillOval(x + getTranslateX(), y + getTranslateY(), w, h);
		gc.dispose();
	}

	@Override
	public void drawPolygon(int[] points) {
		GC gc = getGC();
		int[] newPoints = new int[points.length];
		for (int i = 0; i < points.length; i++) {
			newPoints[i] = points[i] + (i % 2 == 0 ? getTranslateX() : getTranslateY());
		}
		gc.drawPolygon(newPoints);
		gc.dispose();
	}

	@Override
	public void fillPolygon(int[] points) {
		GC gc = getGC();
		int[] newPoints = new int[points.length];
		for (int i = 0; i < points.length; i++) {
			newPoints[i] = points[i] + (i % 2 == 0 ? getTranslateX() : getTranslateY());
		}
		gc.fillPolygon(newPoints);
		gc.dispose();
	}

	@Override
	public void drawRectangle(int x, int y, int w, int h) {
		GC gc = getGC();
		gc.drawRectangle(x + getTranslateX(), y + getTranslateY(), w, h);
		gc.dispose();
	}

	@Override
	public void fillRectangle(int x, int y, int w, int h) {
		GC gc = getGC();
		gc.fillRectangle(x + getTranslateX(), y + getTranslateY(), w, h);
		gc.dispose();
	}

	@Override
	public void drawRoundRectangle(int x, int y, int w, int h, int radX, int radY) {
		GC gc = getGC();
		gc.drawRoundRectangle(x + getTranslateX(), y + getTranslateY(), w, h, radX, radY);
		gc.dispose();
	}

	@Override
	public void fillRoundRectangle(int x, int y, int w, int h, int radX, int radY) {
		GC gc = getGC();
		gc.fillRoundRectangle(x + getTranslateX(), y + getTranslateY(), w, h, radX, radY);
		gc.dispose();
	}

	@Override
	public void drawText(String text, int x, int y) {
		GC gc = getGC();
		gc.drawText(text, x + getTranslateX(), y + getTranslateY());
		gc.dispose();
	}

	@Override
	public int[] stringExtent(String text) {
		Point p = getGC().stringExtent(text);
		return new int[] { p.x, p.y };
	}

	@Override
	public int getTranslateX() {
		return hBar.getTranslate();
	}

	@Override
	public int getTranslateY() {
		return vBar.getTranslate();
	}

	@Override
	public void handleEvent(Event arg0) {
		Main.log(super.getSize() + "");
		Main.log(super.getBounds() + "");
	}

}
