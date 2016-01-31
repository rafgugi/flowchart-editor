package interfaces;

import java.util.ArrayList;

public interface ISubEditor extends IWidget {
	public String getTitle();
	public void setTitle(String name);
	public void close();
	public void addElement(IElement element);
	public ArrayList<IElement> getElements(int x, int y);
}
