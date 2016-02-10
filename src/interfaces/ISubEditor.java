package interfaces;

import java.util.List;

public interface ISubEditor extends IWidget {
	public String getTitle();
	public void setTitle(String name);
	public void addElement(IElement element);
	public void removeElement(IElement element);
	public IElement getElement(int x, int y);
	public List<IElement> getElements();
	public void close();
	public void draw();
	public void clearCanvas();
	public void deselectAll();
	public List<IElement> getSelectedElements();
}
