package interfaces;

import java.util.List;

/**
 * MenuBar is menu container bar on the top of MainWindow
 */
public interface IMenuBar extends IWidget {

	/**
	 * Add a menu.
	 *
	 * @param Menu to be added.
	 */
	public void addItem(IMenu item);

	/**
	 * Get all menus.
	 *
	 * @return Collection of menus.
	 */
	public List<IMenu> getMenus();

}
