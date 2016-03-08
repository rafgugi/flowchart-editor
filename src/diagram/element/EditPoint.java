package diagram.element;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import interfaces.IElement;

public class EditPoint extends AElement {

	protected int x;
	protected int y;
	protected AEditable element;
	public static int length = 5;
	protected int code;

	public static final int TOP_LEFT = 0;
	public static final int TOP_MIDDLE = 1;
	public static final int TOP_RIGHT = 2;
	public static final int MIDDLE_RIGHT = 3;
	public static final int BOTTOM_RIGHT = 4;
	public static final int BOTTOM_MIDDLE = 5;
	public static final int BOTTOM_LEFT = 6;
	public static final int MIDDLE_LEFT = 7;

	public static final int BEGIN = 0;
	public static final int END = 1;

	public EditPoint(AEditable element, int x, int y, int code) {
		this.element = element;
		this.x = x;
		this.y = y;
		this.code = code;
	}

	public IElement getParent() {
		return element;
	}

	@Override
	public String toString() {
		return "EditPoint for (" + getParent().toString() + ")[" + code + "]";
	}

	public int getCode() {
		return code;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setPoint(int x, int y) {
		setX(x);
		setY(y);
	}

	@Override
	public void select() {
		element.select();
	}

	@Override
	public void deselect() {
	}

	@Override
	public void renderNormal() {
		select();
		renderEdit();
	}

	@Override
	public void renderEdit() {
		GC gc = new GC(getCanvas());
		Color black = new Color(gc.getDevice(), 0, 0, 0);
		Color white = new Color(gc.getDevice(), 255, 255, 255);
		gc.setForeground(black);
		gc.setBackground(white);
		int x, y;
		x = this.x - 2;
		y = this.y - 2;
		gc.drawRectangle(x, y, length, length);
		gc.fillRectangle(x + 1, y + 1, length - 1, length - 1);
		gc.dispose();
	}

	@Override
	public IElement checkBoundary(int x, int y) {
		if (x < this.x - length / 2 || x > this.x + length / 2) {
			return null;
		}
		if (y < this.y - length / 2 || y > this.y + length / 2) {
			return null;
		}
		return this;
	}

	@Override
	public IElement checkBoundary(int x1, int y1, int x2, int y2) {
		return null;
	}

	@Override
	public void drag(int x1, int y1, int x2, int y2) {
		element.drag(x1, y1, x2, y2, this);
	}

}
