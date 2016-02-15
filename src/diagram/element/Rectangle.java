package diagram.element;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

import widget.window.property.ProcessProperty;

public class Rectangle extends TwoDimensional {

	protected String text;

	public Rectangle(Point src, Point dst) {
		super(src, dst);
		text = "";
	}
	
	public static void draw(GC gc, int x, int y, int w, int h) {
		gc.drawRectangle(x, y, w, h);
		gc.fillRectangle(x + 1, y + 1, w - 1, h - 1);
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
		
		/* Auto enlarge the  */
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
