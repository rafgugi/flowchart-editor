package diagram.element;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;

import interfaces.IElement;

public abstract class AElement implements IElement {

	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Canvas canvas;

	public AElement(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public AElement(Point src, Point dst) {
		x = src.x < dst.x ? src.x : dst.x;
		y = src.y < dst.y ? src.y : dst.y;
		width = src.x - dst.x;
		width = width < 0 ? -width : width;
		height = src.y - dst.y;
		height = height < 0 ? -height : height;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public int getHeight() {
		return height;
	}

}
