package interfaces;

public interface IElement {
	public int getX();
	public void setX(int x);
	public int getY();
	public void setY(int y);
	public int getWidth();
	public void setWidth(int width);
	public int getHeight();
	public void setHeight(int height);
	public void setLocation(int x, int y);
	public IDrawingState getState();
	public void select();
	public void deselect();
	public void draw();
	public void renderNormal();
	public void renderEdit();
	public boolean checkBoundary(int x, int y);
	public boolean checkBoundary(int x1, int y1, int x2, int y2);
}
