package diagram.element;

import java.util.ArrayList;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

import widget.window.property.ProcessProperty;

public class Rectangle extends AElement {

	private String text;

	public Rectangle(Point src, Point dst) {
		super(src, dst);
		text = "";
	}

	@Override
	public void renderNormal() {
		GC gc = new GC(getCanvas());
		Color black = new Color(gc.getDevice(), 0, 0, 0);
		Color white = new Color(gc.getDevice(), 255, 255, 255);
		gc.setForeground(black);
		gc.setBackground(white);
		gc.drawRectangle(getX(), getY(), getWidth(), getHeight());
		gc.fillRectangle(getX() + 1, getY() + 1, getWidth() - 1, getHeight() - 1);

		int textWidth = gc.stringExtent(text).x;
		int textHeight = gc.stringExtent(text).y;
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

}
