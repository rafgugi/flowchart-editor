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
		this(Math.min(src.x, dst.x), Math.min(src.y, dst.y), 
			Math.abs(src.x - dst.x), Math.abs(src.y - dst.y));
	}

	@Override
	public void renderEdit() {
		renderNormal();
		ArrayList<Point> points;
		int x = getX();
		int y = getY();
		int w = getWidth();
		int h = getHeight();

		points = new ArrayList<>();
		points.add(new Point(x, y));
		points.add(new Point(x + w, y));
		points.add(new Point(x, y + h));
		points.add(new Point(x + w, y + h));
		points.add(new Point(getX() + getWidth() / 2, getY()));
		points.add(new Point(getX(), getY() + getHeight() / 2));
		points.add(new Point(getX() + getWidth() / 2, getY() + getHeight()));
		points.add(new Point(getX() + getWidth(), getY() + getHeight() / 2));

		super.createEditPoint(points);
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
		if (getY() + getHeight()< y || getY() + getHeight() > y + h) {
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
