package diagram.element;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;
import org.json.JSONObject;

import exception.FlowchartEditorException;
import interfaces.IElement;

public abstract class TwoDimensional extends AEditable {

	private int x;
	private int y;
	private int width;
	private int height;
	private String text = "";

	public TwoDimensional() {
	}

	public TwoDimensional(int x, int y, int width, int height) {
		this();
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}

	public TwoDimensional(Point src, Point dst) {
		this(Math.min(src.x, dst.x), Math.min(src.y, dst.y), Math.abs(src.x - dst.x), Math.abs(src.y - dst.y));
	}

	public static void drawText(String text, TwoDimensional element) {
		String intext = element.getText();
		int inx = element.getX();
		int iny = element.getY();
		int inw = element.getWidth();
		int inh = element.getHeight();

		/* Determine max text width from each line */
		String[] lines = intext.split("\r\n|\r|\n");
		int textWidth = -1;
		for (String line : lines) {
			int[] extent = getCanvas().stringExtent(line);
			if (extent[0] > textWidth) {
				textWidth = extent[0];
			}
		}

		/* Determine text height of lines */
		int textHeight = getCanvas().stringExtent(intext)[1] * lines.length;

		/* Auto enlarge the element */
		boolean changed = false;
		if (textWidth > inw) {
			inw = textWidth + 4;
			element.setWidth(inw);
			changed = true;
		}
		if (textHeight > inh) {
			inh = textHeight + 4;
			element.setHeight(inh);
			changed = true;
		}
		if (changed) {
			element.renderNormal();
			return;
		}

		getCanvas().drawText(intext, inx + inw / 2 - textWidth / 2, iny + inh / 2 - textHeight / 2);
	}

	@Override
	public IElement checkBoundary(int x, int y) {
		// check apakah atasnya udah ada return nya
		IElement ans = super.checkBoundary(x, y);
		if (ans != null) {
			return ans;
		}

		if (x < getX() || x > getX() + getWidth()) {
			return null;
		}
		if (y < getY() || y > getY() + getHeight()) {
			return null;
		}
		return this;
	}

	@Override
	public IElement checkBoundary(int x1, int y1, int x2, int y2) {
		int x = Math.min(x1, x2);
		int y = Math.min(y1, y2);
		int w = Math.abs(x1 - x2);
		int h = Math.abs(y1 - y2);

		if (getX() < x || getX() > x + w) {
			return null;
		}
		if (getY() < y || getY() > y + h) {
			return null;
		}
		if (getX() + getWidth() < x || getX() + getWidth() > x + w) {
			return null;
		}
		if (getY() + getHeight() < y || getY() + getHeight() > y + h) {
			return null;
		}
		return this;
	}

	/**
	 * Get x value.
	 *
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Set x value.
	 *
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Get y value.
	 *
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set y value.
	 *
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Get width value.
	 *
	 * @return width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Set width value.
	 *
	 * @param width
	 */
	public void setWidth(int width) {
		if (width < 10) {
			width = 10;
		}
		this.width = width;
	}

	/**
	 * Get height value.
	 *
	 * @return height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Set height value.
	 *
	 * @param height
	 */
	public void setHeight(int height) {
		if (height < 10) {
			height = 10;
		}
		this.height = height;
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

	/**
	 * Set x and y value.
	 * 
	 * @param x
	 * @param y
	 */
	public void setLocation(int x, int y) {
		setX(x);
		setY(y);
	}

	/**
	 * Set x, y, width, and height value from two point square.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void setBoundary(int x1, int y1, int x2, int y2) {
		if (x1 > x2) {
			setBoundary(x2, y1, x1, y2);
			return;
		}
		if (y1 > y2) {
			setBoundary(x1, y2, x2, y1);
			return;
		}
		setX(x1);
		setY(y1);
		setWidth(x2 - x1);
		setHeight(y2 - y1);
	}

	@Override
	public void drag(int x1, int y1, int x2, int y2) {
		int x = getX() + x2 - x1;
		int y = getY() + y2 - y1;
		setLocation(x, y);
		super.drag(x1, y1, x2, y2);
	}

	@Override
	public void drag(int x1, int y1, int x2, int y2, IElement e) {
		if (e instanceof EditPoint) {
			switch (((EditPoint) e).getCode()) {
			case EditPoint.TOP_LEFT:
				setBoundary(x2, y2, getX() + getWidth(), getY() + getHeight());
				break;
			case EditPoint.TOP_MIDDLE:
				setBoundary(getX(), y2, getX() + getWidth(), getY() + getHeight());
				break;
			case EditPoint.TOP_RIGHT:
				setBoundary(x2, y2, getX(), getY() + getHeight());
				break;
			case EditPoint.MIDDLE_RIGHT:
				setBoundary(x2, getY() + getHeight(), getX(), getY());
				break;
			case EditPoint.BOTTOM_RIGHT:
				setBoundary(x2, y2, getX(), getY());
				break;
			case EditPoint.BOTTOM_MIDDLE:
				setBoundary(getX() + getWidth(), y2, getX(), getY());
				break;
			case EditPoint.BOTTOM_LEFT:
				setBoundary(x2, y2, getX() + getWidth(), getY());
				break;
			case EditPoint.MIDDLE_LEFT:
				setBoundary(x2, getY(), getX() + getWidth(), getY() + getHeight());
				break;
			default:
				throw new FlowchartEditorException("Unexpected EditPoint constant.");
			}
		}
		super.drag(x1, y1, x2, y2, e);
	}

	@Override
	public void createEditPoints() {
		// 0 1 2
		// 7 X 3
		// 6 5 4
		ArrayList<Point> points = new ArrayList<>();
		points.add(new Point(getX(), getY())); // 0
		points.add(new Point(getX() + getWidth() / 2, getY())); // 1
		points.add(new Point(getX() + getWidth(), getY())); // 2
		points.add(new Point(getX() + getWidth(), getY() + getHeight() / 2)); // 3
		points.add(new Point(getX() + getWidth(), getY() + getHeight())); // 4
		points.add(new Point(getX() + getWidth() / 2, getY() + getHeight())); // 5
		points.add(new Point(getX(), getY() + getHeight())); // 6
		points.add(new Point(getX(), getY() + getHeight() / 2)); // 7
		createEditPoints(points);
	}

	@Override
	public JSONObject jsonEncode() {
		JSONObject obj = super.jsonEncode();
		obj.put("x", x);
		obj.put("y", y);
		obj.put("width", width);
		obj.put("height", height);
		obj.put("text", text);
		return obj;
	}

	@Override
	public void jsonDecode(JSONObject obj) {
		super.jsonDecode(obj);
		setX(obj.getInt("x"));
		setY(obj.getInt("y"));
		setWidth(obj.getInt("width"));
		setHeight(obj.getInt("height"));
		setText(obj.getString("text"));
	}

	/**
	 * Get children, destination of the outgoing flows.
	 * 
	 * @return children
	 */
	public ArrayList<TwoDimensional> getChildren() {
		ArrayList<TwoDimensional> children = new ArrayList<>();
		for (IElement elem : getConnectedElements()) {
			Line line = (Line) elem;
			if (line.checkConnected(this) == Line.CONNECTED_SRC) {
				children.add(line.getDstElement());
			}
		}
		return children;
	}

	/**
	 * Get parents, source of the incoming flows.
	 * 
	 * @return parents
	 */
	public ArrayList<TwoDimensional> getParents() {
		ArrayList<TwoDimensional> parents = new ArrayList<>();
		for (IElement elem : getConnectedElements()) {
			Line line = (Line) elem;
			if (line.checkConnected(this) == Line.CONNECTED_DST) {
				parents.add(line.getSrcElement());
			}
		}
		return parents;
	}

	@Override
	public String toString() {
		String ans = getClass().getSimpleName();
		if (!getText().equals("")) {
			ans += "[" + getText() + "]";
		}
		return ans;
	}

}
