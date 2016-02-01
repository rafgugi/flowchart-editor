package diagram.element;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

public class Rectangle extends AElement {

	public Rectangle(Point src, Point dst) {
		super(src, dst);
	}

	@Override
	public void draw() {
		GC gc = new GC(getCanvas());
		Color black = new Color(gc.getDevice(), 0, 0, 0);
		Color white = new Color(gc.getDevice(), 255, 255, 255);
		gc.setForeground(black);
		gc.setBackground(white);
		gc.drawRectangle(getX(), getY(), getWidth(), getHeight());
		gc.fillRectangle(getX() + 1, getY() + 1, getWidth() - 1, getHeight() - 1);
	}

	@Override
	public boolean checkBoundary(int x, int y) {
		if (x < super.x || x > super.x + super.width) {
			return false;
		}
		if (y < super.y || y > super.y + super.height) {
			return false;
		}
		return true;
	}

}
