package diagram.element;

import java.util.ArrayList;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

import widget.window.property.ProcessProperty;

public class Rectangle extends TwoDimensional {
	
	private String text;

	public Rectangle(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		text = "";
	}

	public Rectangle(Point src, Point dst) {
		this(Math.min(src.x, dst.x), Math.min(src.y, dst.y), 
			Math.abs(src.x - dst.x), Math.abs(src.y - dst.y));
	}

	@Override
	public void renderNormal() {
		GC gc = new GC(getCanvas());
		Color black = new Color(gc.getDevice(), 0, 0, 0);
		Color white = new Color(gc.getDevice(), 255, 255, 255);
		gc.setForeground(black);
		gc.setBackground(white);
		
		/* Determine max text width from each line */
		String[] lines = text.split("\r\n|\r|\n");
		int textWidth = -1;
		for (String line : lines) {
			if (gc.stringExtent(line).x > textWidth)
			textWidth = gc.stringExtent(line).x;
		}
		
		/* Determine text height of lines */
		int textHeight = gc.stringExtent(text).y * lines.length;
		
		/* Auto enlarge the  */
		if (textWidth > getWidth()) {
			setWidth(textWidth + 4);
		}
		if (textHeight > getHeight()) {
			setHeight(textHeight + 4);
		}
		
		gc.drawRectangle(getX(), getY(), getWidth(), getHeight());
		gc.fillRectangle(getX() + 1, getY() + 1, getWidth() - 1, getHeight() - 1);

		gc.drawText(text, getX() + getWidth() / 2 - textWidth / 2, getY() + getHeight() / 2 - textHeight / 2);
		gc.dispose();
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

	@Override
	public void action() {
		ProcessProperty prop = new ProcessProperty(this);
		prop.show();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		draw();
	}

	@Override
	public void drag(int x1, int y1, int x2, int y2) {
		int x = getX() + x2 - x1;
		int y = getY() + y2 - y1;
		setLocation(x, y);
	}

}
