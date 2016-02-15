package diagram.element;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

public class RoundedRectangle extends TwoDimensional {

	protected String text;

	public RoundedRectangle(Point src, Point dst) {
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

		/* Determine max text width from each line */
		String[] lines = text.split("\r\n|\r|\n");
		int textWidth = -1;
		for (String line : lines) {
			if (gc.stringExtent(line).x > textWidth)
				textWidth = gc.stringExtent(line).x;
		}

		/* Determine text height of lines */
		int textHeight = gc.stringExtent(text).y * lines.length;

		/* Auto enlarge the shape*/
		if (textWidth > getWidth()) {
			setWidth(textWidth + 4);
		}
		if (textHeight > getHeight()) {
			setHeight(textHeight + 4);
		}

		gc.drawRoundRectangle(getX(), getY(), getWidth(), getHeight(), getHeight(), getHeight());
		gc.fillRoundRectangle(getX() + 1, getY() + 1, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());

		gc.drawText(text, getX() + getWidth() / 2 - textWidth / 2, getY() + getHeight() / 2 - textHeight / 2);
		gc.dispose();
	}

	@Override
	public void action() {
		// cuma ngasih pilihan jadi start atau jadi end.
	}

}
