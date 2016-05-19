package diagram.element;

import org.eclipse.swt.graphics.Point;

public class Diamond extends TwoDimensional {

	public Diamond() {
	}

	public Diamond(Point src, Point dst) {
		super(src, dst);
	}

	public static void draw(int x, int y, int w, int h) {
		int[] points = { x + w / 2, y, x + w, y + h / 2, x + w / 2, y + h, x, y + h / 2 };
		getCanvas().fillPolygon(points);
		getCanvas().drawPolygon(points);
	}

	@Override
	public void renderNormal() {
		getCanvas().setFgColor(0, 0, 0);
		getCanvas().setBgColor(255, 255, 255);

		draw(getX(), getY(), getWidth(), getHeight());
		TwoDimensional.drawText(getText(), this);
	}

}
