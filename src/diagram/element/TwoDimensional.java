package diagram.element;

public abstract class TwoDimensional extends AElement {

	protected int x;
	protected int y;
	protected int width;
	protected int height;

	@Override
	public boolean checkBoundary(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkBoundary(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public void setWidth(int width) {
		if (width < 10) {
			width = 10;
		}
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		if (height < 10) {
			height = 10;
		}
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public void setLocation(int x, int y) {
		setX(x);
		setY(y);
	}

}
