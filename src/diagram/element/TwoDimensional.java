package diagram.element;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

public abstract class TwoDimensional extends AElement {

	protected int x;
	protected int y;
	protected int width;
	protected int height;

	public TwoDimensional(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}

	public TwoDimensional(Point src, Point dst) {
		this(Math.min(src.x, dst.x), Math.min(src.y, dst.y), Math.abs(src.x - dst.x), Math.abs(src.y - dst.y));
	}

	public static ArrayList<Point> getEditPoints(int x, int y, int w, int h) {
		ArrayList<Point> points = new ArrayList<>();
		// 0 1 2
		// 7   3
		// 6 5 4
		points.add(new Point(x, y)); // 0
		points.add(new Point(x + w / 2, y)); // 1
		points.add(new Point(x + w, y)); // 2
		points.add(new Point(x + w, y + h / 2)); // 3
		points.add(new Point(x + w, y + h)); // 4
		points.add(new Point(x + w / 2, y + h)); // 5
		points.add(new Point(x, y + h)); // 6
		points.add(new Point(x, y + h / 2)); // 7
		return points;
	}

	public static ArrayList<Point> getEditPoints(TwoDimensional element) {
		return getEditPoints(element.getX(), element.getY(), element.getWidth(), element.getHeight());
	}

	@Override
	public void renderEdit() {
		renderNormal();
		ArrayList<Point> points = TwoDimensional.getEditPoints(this);
		for (Point point : points) {
			EditPoint ep = new EditPoint(this, point.x, point.y, points.indexOf(point));
			
		}

//		super.createEditPoint(TwoDimensional.getEditPoints(this));
	}

	@Override
	public boolean checkBoundary(int x, int y) {
		if (x < getX() || x > getX() + getWidth()) {
			return false;
		}
		if (y < getY() || y > getY() + getHeight()) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkBoundary(int x1, int y1, int x2, int y2) {
		int x = Math.min(x1, x2);
		int y = Math.min(y1, y2);
		int w = Math.abs(x1 - x2);
		int h = Math.abs(y1 - y2);

		if (getX() < x || getX() > x + w) {
			return false;
		}
		if (getY() < y || getY() > y + h) {
			return false;
		}
		if (getX() + getWidth() < x || getX() + getWidth() > x + w) {
			return false;
		}
		if (getY() + getHeight() < y || getY() + getHeight() > y + h) {
			return false;
		}
		return true;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public void setWidth(int width) {
		if (width < 10) {
			width = 10;
		}
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		if (height < 10) {
			height = 10;
		}
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public void setLocation(int x, int y) {
		setX(x);
		setY(y);
	}

	@Override
	public void drag(int x1, int y1, int x2, int y2) {
		int x = getX() + x2 - x1;
		int y = getY() + y2 - y1;
		setLocation(x, y);
	}

}
