package diagram.element;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

public class Ellipse extends TwoDimensional {

	public Ellipse() {
	}

	public Ellipse(Point src, Point dst) {
		super(src, dst);
	}

	public static void draw(GC gc, int x, int y, int w, int h) {
		gc.fillOval(x, y, w, h);
		gc.drawOval(x, y, w, h);
	}

	@Override
	public void renderNormal() {
		GC gc = new GC(getCanvas());
		Color black = new Color(gc.getDevice(), 0, 0, 0);
		Color white = new Color(gc.getDevice(), 255, 255, 255);
		gc.setForeground(black);
		gc.setBackground(white);

		draw(gc, getX(), getY(), getWidth(), getHeight());
		TwoDimensional.drawText(gc, getText(), this);

		gc.dispose();
	}

}
