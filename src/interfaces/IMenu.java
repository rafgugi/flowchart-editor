package interfaces;

import java.util.List;

/**
 * Menu is single item of MenuBar that always shown. When
 * menu is clicked, it will collapse menu items.
 */
public interface IMenu extends IWidget {

	/**
	 * Get the menu title (or name).
	 *
	 * @return Menu title
	 */
	public String getTitle();

	/**
	 * Set the menu title (or name).
	 *
	 * @param Menu title
	 */
	public void setTitle(String name);

	/**
	 * Add a menu item in this menu.
	 *
	 * @param Menu item to be added.
	 */
	public void addItem(IMenuItem item);

	/**
	 * Get all menu items.
	 *
	 * @return All this menu items
	 */
	public List<IMenuItem> getMenuItems();

	/**
	 * Set collection of menu items.
	 *
	 * @param Menu items collection
	 */
	public void setItems(List<IMenuItem> items);
}
