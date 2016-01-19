package interfaces;

import java.util.List;

public interface IMenu extends IWidget {
	public void addItem(IMenuItem item);
	public List<IMenuItem> getItems();
	public void setItems(List<IMenuItem> items);
	public String getTitle();
	public void setTitle(String name);
}
