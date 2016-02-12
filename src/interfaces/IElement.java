package interfaces;

public interface IElement {
	public IDrawingState getState();
	public void select();
	public void deselect();
	public void draw();
	public void renderNormal();
	public void renderEdit();
	public boolean checkBoundary(int x, int y);
	public boolean checkBoundary(int x1, int y1, int x2, int y2);
	public void action();
}
