package interfaces;

import java.util.ArrayList;
import java.util.UUID;

public interface IElement {
	public IDrawingState getState();
	public void select();
	public void deselect();
	public boolean isActive();
	public void draw();
	public void renderNormal();
	public void renderEdit();
	public UUID getId();
	public void setId(UUID id);
	public IElement checkBoundary(int x, int y);
	public IElement checkBoundary(int x1, int y1, int x2, int y2);
	public void drag(int x1, int y1, int x2, int y2);
	public ArrayList<IElement> getConnectedElements();
	public void connect(IElement element);
	public void disconnect(IElement element);
	public String toString();
}
