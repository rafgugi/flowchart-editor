package diagram.element;

import diagram.state.*;
import interfaces.IDrawingState;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;

import interfaces.IElement;
import java.util.List;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

public abstract class AElement implements IElement {

	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Canvas canvas;
	private IDrawingState state;
	
	public AElement(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		state = NormalState.getInstance();
	}

	public AElement(Point src, Point dst) {
		this(Math.min(src.x, dst.x), Math.min(src.y, dst.y), 
			Math.abs(src.x - dst.x), Math.abs(src.y - dst.y));
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public void setLocation(int x, int y) {
		setX(x);
		setY(y);
	}
	
	@Override
	public void select() {
		state = EditState.getInstance();
	}
	
	@Override
	public void deselect() {
		state = NormalState.getInstance();
	}
	
	@Override
	public IDrawingState getState() {
		return state;
	}
	
	@Override
	public void draw() {
		state.draw(this);
	}

	@Override
	public boolean checkBoundary(int x, int y) {
		if (x < getX() || x > getX() + getWidth()) {
			return false;
		}
		if (y < getY() || y > getY() + getWidth()) {
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
	
	public void createEditPoint(Point p) {
		GC gc = new GC(canvas);
		Color black = new Color(gc.getDevice(), 0, 0, 0);
		Color white = new Color(gc.getDevice(), 255, 255, 255);
		gc.setForeground(black);
		gc.setBackground(white);
		int x, y, length;
		x = p.x - 2;
		y = p.y - 2;
		length = 5;
		gc.drawRectangle(x, y, length, length);
		gc.fillRectangle(x + 1, y + 1, length - 1, length- 1);
		gc.dispose();
	}
	
	public void createEditPoint(List<Point> points) {
		for (Point p : points) {
			createEditPoint(p);
		}
	}

}
