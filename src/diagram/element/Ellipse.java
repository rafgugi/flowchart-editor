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

		/* Determine max text width from each line */
		String[] lines = text.split("\r\n|\r|\n");
		int textWidth = -1;
		for (String line : lines) {
			if (gc.stringExtent(line).x > textWidth)
				textWidth = gc.stringExtent(line).x;
		}

		/* Determine text height of lines */
		int textHeight = gc.stringExtent(text).y * lines.length;

		/* Auto enlarge the */
		if (textWidth > getWidth()) {
			setWidth(textWidth + 4);
		}
		if (textHeight > getHeight()) {
			setHeight(textHeight + 4);
		}

		draw(gc, getX(), getY(), getWidth(), getHeight());

		gc.drawText(text, getX() + getWidth() / 2 - textWidth / 2, getY() + getHeight() / 2 - textHeight / 2);
		gc.dispose();
	}

}
