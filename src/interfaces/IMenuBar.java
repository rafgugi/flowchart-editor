package interfaces;

import java.util.List;

public interface IMenuBar extends IWidget {
	public void addItem(IMenu item);
	public List<IMenu> getMenus();
	public void setMenus(List<IMenu> items);
	public String getTitle();
	public void setTitle(String name);
}
