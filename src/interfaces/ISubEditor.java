package interfaces;

import java.util.List;
import java.util.UUID;

public interface ISubEditor extends IWidget {
	public String getTitle();
	public void setTitle(String name);
	public void addElement(IElement element);
	public void removeElement(IElement element);
	public IElement getElement(int x, int y);
	public IElement getElement(UUID id);
	public IElement getElement(String id);
	public List<IElement> getElements();
	public void close();
	public void draw();
	public void clearCanvas();
	public void deselectAll();
	public List<IElement> getSelectedElements();
}
