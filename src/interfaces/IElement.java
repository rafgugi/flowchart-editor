package interfaces;

import java.util.ArrayList;

public interface IElement {
	public String toString();
	public IDrawingState getState();
	public void select();
	public void deselect();
	public boolean isActive();
	public void draw();
	public void renderNormal();
	public void renderEdit();
	public boolean checkBoundary(int x, int y);
	public boolean checkBoundary(int x1, int y1, int x2, int y2);
	public void action();
	public void drag(int x1, int y1, int x2, int y2);
	public ArrayList<IElement> getConnectedElements();
	public void connect(IElement element);
	public void disconnect(IElement element);
}
