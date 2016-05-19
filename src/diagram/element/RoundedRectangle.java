package diagram.element;

import org.eclipse.swt.graphics.Point;

public class RoundedRectangle extends TwoDimensional {

	public RoundedRectangle() {
	}

	public RoundedRectangle(Point src, Point dst) {
		super(src, dst);
	}

	public static void draw(int x, int y, int w, int h) {
		getCanvas().fillRoundRectangle(x, y, w, h, h, h);
		getCanvas().drawRoundRectangle(x, y, w, h, h, h);
	}

	@Override
	public void renderNormal() {
		getCanvas().setFgColor(0, 0, 0);
		getCanvas().setBgColor(255, 255, 255);

		draw(getX(), getY(), getWidth(), getHeight());
		TwoDimensional.drawText(getText(), this);
	}

}
