package diagram.element;

import org.eclipse.swt.graphics.Point;

public class Rectangle extends TwoDimensional {

	public Rectangle() {
	}

	public Rectangle(Point src, Point dst) {
		super(src, dst);
	}

	public static void draw(int x, int y, int w, int h) {
		getCanvas().fillRectangle(x, y, w, h);
		getCanvas().drawRectangle(x, y, w, h);
	}

	@Override
	public void renderNormal() {
		getCanvas().setFgColor(0, 0, 0);
		getCanvas().setBgColor(255, 255, 255);

		draw(getX(), getY(), getWidth(), getHeight());
		TwoDimensional.drawText(getText(), this);
	}

}
