package interfaces;

import java.util.List;

public interface IMenu extends IWidget {
	public String getTitle();
	public void setTitle(String name);
	public void addItem(IMenuItem item);
	public List<IMenuItem> getMenuItems();
	public void setItems(List<IMenuItem> items);
}
