package diagram.element;

import java.util.ArrayList;

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

	public static void draw(int srcx, int srcy, int dstx, int dsty, boolean pointed) {
		getCanvas().drawLine(srcx, srcy, dstx, dsty);

		if (pointed) {
			getCanvas().setBgColor(0, 0, 0);
			int n = 10;
			double angle = Math.atan2(dsty - srcy, dstx - srcx);
			int x1 = (int) (dstx - n * Math.cos(angle - Math.PI / 6));
			int y1 = (int) (dsty - n * Math.sin(angle - Math.PI / 6));
			int x2 = (int) (dstx - n * Math.cos(angle + Math.PI / 6));
			int y2 = (int) (dsty - n * Math.sin(angle + Math.PI / 6));

			int[] points = { dstx, dsty, x1, y1, x2, y2 };
			getCanvas().fillPolygon(points);
			getCanvas().drawPolygon(points);
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
		getCanvas().setFgColor(0, 0, 0);
		getCanvas().setBgColor(0, 0, 0);

		generateSrcDstPoints();
		draw(getSrcx(), getSrcy(), getDstx(), getDsty(), true);

		/* Determine max text width from each line */
		String text = getText();
		String[] lines = text.split("\r\n|\r|\n");
		int textWidth = -1;
		for (String line : lines) {
			int[] extent = getCanvas().stringExtent(line);
			if (extent[0] > textWidth)
				textWidth = extent[0];
		}

		/* Determine text height of lines */
		int textHeight = getCanvas().stringExtent(text)[1] * lines.length;

		getCanvas().setBgColor(255, 255, 255);
		String temp = text;
		if (!temp.equals("")) {
			temp = " " + temp + " ";
		}
		int middlex = (getSrcx() + getDstx()) / 2;
		int middley = (getSrcy() + getDsty()) / 2;
		getCanvas().drawText(temp, middlex - textWidth / 2, middley - textHeight / 2);
	}

	@Override
	public void renderEdit() {
		createEditPoints();
		super.renderEdit();
	}

	/**
	 * Check whether (x,y) is inside square p1 and p2
	 * 
	 * @param x
	 * @param y
	 * @param p1
	 * @param p2
	 * @return
	 */
	protected boolean checkBoundary(int x, int y, Point p1, Point p2) {
		int p1x = p1.x;
		int p1y = p1.y;
		int p2x = p2.x;
		int p2y = p2.y;

		if (p1x > p2x) {
			int temp = p1x;
			p1x = p2x;
			p2x = temp;
		}

		if (p1y > p2y) {
			int temp = p1y;
			p1y = p2y;
			p2y = temp;
		}

		p1x -= BOUNDARY;
		p1y -= BOUNDARY;
		p2x += BOUNDARY;
		p2y += BOUNDARY;

		if (x < p1x || x > p2x) {
			return false;
		}
		if (y < p1y || y > p2y) {
			return false;
		}
		return true;
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

	/**
	 * Get source element.
	 * 
	 * @return source element
	 */
	public TwoDimensional getSrcElement() {
		return srcElement;
	}

	/**
	 * Set source element.
	 * 
	 * @param source element
	 */
	public void setSrcElement(TwoDimensional element) {
		if (srcElement != null) {
			disconnect(srcElement);
			srcElement.disconnect(this);
		}
		srcElement = element;
		connect(element);
		element.connect(this);
	}

	/**
	 * Get destination element.
	 * 
	 * @return destination element
	 */
	public TwoDimensional getDstElement() {
		return dstElement;
	}

	/**
	 * Set destination element.
	 * 
	 * @param destination element
	 */
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
	public void drag(int x, int y, IElement e) {
	}

	/**
	 * Check connection status of an element. Return connection status constant.
	 *    CONNECTED_BOTH if the element is both as source and destination.
	 *    CONNECTED_SRC if the element is as source of the line.
	 *    CONNECTED_DST if the element is as destination of the line.
	 *    NOT_CONNECTED if the element is not connected to the line.
	 * 
	 * @param element
	 * @return
	 */
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

	/**
	 * Get src x
	 *
	 * @return srcx
	 */
	public int getSrcx() {
		return srcx;
	}

	/**
	 * Set src x
	 *
	 * @param srcx
	 */
	public void setSrcx(int srcx) {
		this.srcx = srcx;
	}

	/**
	 * Get src y
	 *
	 * @return srcy
	 */
	public int getSrcy() {
		return srcy;
	}

	/**
	 * Set src y
	 *
	 * @param srcy
	 */
	public void setSrcy(int srcy) {
		this.srcy = srcy;
	}

	/**
	 * Get dst x
	 *
	 * @return dstx
	 */
	public int getDstx() {
		return dstx;
	}

	/**
	 * Set dst x
	 *
	 * @param dstx
	 */
	public void setDstx(int dstx) {
		this.dstx = dstx;
	}

	/**
	 * Get dst y
	 *
	 * @return dsty
	 */
	public int getDsty() {
		return dsty;
	}

	/**
	 * Set dst y
	 *
	 * @param dsty
	 */
	public void setDsty(int dsty) {
		this.dsty = dsty;
	}

	/**
	 * Get text value.
	 *
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set text value.
	 * 
	 * @param text
	 */
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
