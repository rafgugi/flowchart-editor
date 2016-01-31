package diagram.element;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

public class Rectangle extends AElement {

	public Rectangle(Point src, Point dst) {
		super(src, dst);
	}

	@Override
	public void draw() {
		GC gc = new GC(getCanvas());
		gc.drawRectangle(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public boolean checkBoundary(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

}
