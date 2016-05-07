package diagram.element;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.json.JSONObject;

import interfaces.IElement;
import interfaces.ISubEditor;
import widget.window.MainWindow;

public class Line extends AEditable {

	private TwoDimensional srcElement;
	private TwoDimensional dstElement;

	private int srcx;
	private int srcy;
	private int dstx;
	private int dsty;
	private String text = "";

	public static final int NOT_CONNECTED = 0;
	public static final int CONNECTED_SRC = 1;
	public static final int CONNECTED_DST = 2;
	public static final int CONNECTED_BOTH = 3;

	public static final String NONE = "";
	public static final String YES = "Y";
	public static final String NO = "N";

	public static final int BOUNDARY = 6;

	public Line() {
	}

	public Line(TwoDimensional src, TwoDimensional dst) {
		this();
		setSrcElement(src);
		setDstElement(dst);
	}

	public static void draw(GC gc, int srcx, int srcy, int dstx, int dsty, boolean pointed) {
		gc.drawLine(srcx, srcy, dstx, dsty);

		if (pointed) {
			int n = 10;
			double angle = Math.atan2(dsty - srcy, dstx - srcx);
			int x1 = (int) (dstx - n * Math.cos(angle - Math.PI / 6));
			int y1 = (int) (dsty - n * Math.sin(angle - Math.PI / 6));
			int x2 = (int) (dstx - n * Math.cos(angle + Math.PI / 6));
			int y2 = (int) (dsty - n * Math.sin(angle + Math.PI / 6));

			int[] points = { dstx, dsty, x1, y1, x2, y2 };
			gc.fillPolygon(points);
			gc.drawPolygon(points);
		}
	}

	protected void generateSrcDstPoints() {
		ArrayList<Point> srcPoints;
		srcPoints = new ArrayList<>();
		int srcx = getSrcElement().getX();
		int srcy = getSrcElement().getY();
		int srcw = getSrcElement().getWidth();
		int srch = getSrcElement().getHeight();
		srcPoints.add(new Point(srcx + srcw / 2, srcy));
		srcPoints.add(new Point(srcx, srcy + srch / 2));
		srcPoints.add(new Point(srcx + srcw / 2, srcy + srch));
		srcPoints.add(new Point(srcx + srcw, srcy + srch / 2));

		ArrayList<Point> dstPoints;
		dstPoints = new ArrayList<>();
		int dstx = getDstElement().getX();
		int dsty = getDstElement().getY();
		int dstw = getDstElement().getWidth();
		int dsth = getDstElement().getHeight();
		dstPoints.add(new Point(dstx + dstw / 2, dsty));
		dstPoints.add(new Point(dstx, dsty + dsth / 2));
		dstPoints.add(new Point(dstx + dstw / 2, dsty + dsth));
		dstPoints.add(new Point(dstx + dstw, dsty + dsth / 2));

		double min = Double.MAX_VALUE;
		srcx = 0;
		srcy = 0;
		dstx = 0;
		dsty = 0;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				double euc = 0;
				euc += Math.pow(srcPoints.get(i).x - dstPoints.get(j).x, 2);
				euc += Math.pow(srcPoints.get(i).y - dstPoints.get(j).y, 2);
				euc = Math.sqrt(euc);
				if (euc < min) {
					srcx = srcPoints.get(i).x;
					srcy = srcPoints.get(i).y;
					dstx = dstPoints.get(j).x;
					dsty = dstPoints.get(j).y;
					min = euc;
				}
			}
		}

		setSrcx(srcx);
		setSrcy(srcy);
		setDstx(dstx);
		setDsty(dsty);
	}

	@Override
	public void renderNormal() {
		GC gc = new GC(getCanvas());
		Color black = new Color(gc.getDevice(), 0, 0, 0);
		Color white = new Color(gc.getDevice(), 255, 255, 255);
		gc.setForeground(black);
		gc.setBackground(black);

		generateSrcDstPoints();
		draw(gc, getSrcx(), getSrcy(), getDstx(), getDsty(), true);

		/* Determine max text width from each line */
		String[] lines = text.split("\r\n|\r|\n");
		int textWidth = -1;
		for (String line : lines) {
			if (gc.stringExtent(line).x > textWidth)
				textWidth = gc.stringExtent(line).x;
		}

		/* Determine text height of lines */
		int textHeight = gc.stringExtent(text).y * lines.length;

		gc.setBackground(white);
		String temp = text;
		if (!temp.equals("")) {
			temp = " " + temp + " ";
		}
		int middlex = (getSrcx() + getDstx()) / 2;
		int middley = (getSrcy() + getDsty()) / 2;
		gc.drawText(temp, middlex - textWidth / 2, middley - textHeight / 2);

		gc.dispose();
	}

	@Override
	public void renderEdit() {
		createEditPoints();
		super.renderEdit();
	}
	
	@Override
	public IElement checkBoundary(int x, int y) {
		IElement retval = super.checkBoundary(x, y);
		return retval;
	}

	@Override
	public IElement checkBoundary(int x1, int y1, int x2, int y2) {
		int x = Math.min(x1, x2);
		int y = Math.min(y1, y2);
		int w = Math.abs(x1 - x2);
		int h = Math.abs(y1 - y2);

		if (getSrcx() < x || getSrcx() > x + w) {
			return null;
		}
		if (getSrcy() < y || getSrcy() > y + h) {
			return null;
		}
		if (getDstx() < x || getDstx() > x + w) {
			return null;
		}
		if (getDsty() < y || getDsty() > y + h) {
			return null;
		}
		return this;
	}

	public TwoDimensional getSrcElement() {
		return srcElement;
	}

	public void setSrcElement(TwoDimensional element) {
		if (srcElement != null) {
			disconnect(srcElement);
			srcElement.disconnect(this);
		}
		srcElement = element;
		connect(element);
		element.connect(this);
	}

	public TwoDimensional getDstElement() {
		return dstElement;
	}

	public void setDstElement(TwoDimensional element) {
		if (dstElement != null) {
			disconnect(dstElement);
			dstElement.disconnect(this);
		}
		dstElement = element;
		connect(element);
		element.connect(this);
	}

	@Override
	public void drag(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void drag(int x1, int y1, int x2, int y2, IElement e) {
	}

	public int checkConnected(TwoDimensional element) {
		if (srcElement == dstElement) {
			return CONNECTED_BOTH;
		}
		if (srcElement == element) {
			return CONNECTED_SRC;
		}
		if (dstElement == element) {
			return CONNECTED_DST;
		}
		return NOT_CONNECTED;
	}

	public int getSrcx() {
		return srcx;
	}

	public void setSrcx(int srcx) {
		this.srcx = srcx;
	}

	public int getSrcy() {
		return srcy;
	}

	public void setSrcy(int srcy) {
		this.srcy = srcy;
	}

	public int getDstx() {
		return dstx;
	}

	public void setDstx(int dstx) {
		this.dstx = dstx;
	}

	public int getDsty() {
		return dsty;
	}

	public void setDsty(int dsty) {
		this.dsty = dsty;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void createEditPoints() {
		ArrayList<Point> points = new ArrayList<>();
		points.add(new Point(getSrcx(), getSrcy()));
		points.add(new Point(getDstx(), getDsty()));
		createEditPoints(points);
	}

	@Override
	public JSONObject jsonEncode() {
		JSONObject obj = super.jsonEncode();
		obj.put("text", text);
		obj.put("src", srcElement.getId());
		obj.put("dst", dstElement.getId());
		return obj;
	}

	@Override
	public void jsonDecode(JSONObject obj) {
		super.jsonDecode(obj);
		ISubEditor se = MainWindow.getInstance().getEditor().getActiveSubEditor();
		IElement src = se.getElement(obj.getString("src"));
		IElement dst = se.getElement(obj.getString("dst"));
		setSrcElement((TwoDimensional) src);
		setDstElement((TwoDimensional) dst);
		setText(obj.getString("text"));
	}

}
