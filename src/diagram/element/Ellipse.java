package diagram.element;

import org.eclipse.swt.graphics.Point;

public class Ellipse extends TwoDimensional {

	public Ellipse() {
	}

	public Ellipse(Point src, Point dst) {
		super(src, dst);
	}

	public static void draw(int x, int y, int w, int h) {
		getCanvas().fillOval(x, y, w, h);
		getCanvas().drawOval(x, y, w, h);
	}

	@Override
	public void renderNormal() {
		getCanvas().setFgColor(0, 0, 0);
		getCanvas().setBgColor(255, 255, 255);

		draw(getX(), getY(), getWidth(), getHeight());
		TwoDimensional.drawText(getText(), this);
	}

}
