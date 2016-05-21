package interfaces;

import java.util.ArrayList;

/**
 * List of validation fails. You can click the item then it automatically select
 * the fails elements. Generated when ValidateCommand is executed.
 */
public interface IValidationList {

	/**
	 * Add an item into the list.
	 * 
	 * @param item
	 */
	public void addItem(IValidationItem item);

	/**
	 * Remove an item from the list.
	 * 
	 * @param item
	 */
	public void removeItem(IValidationItem item);

	/**
	 * Get index of the item. If this item not found in the collection, return
	 * -1.
	 * 
	 * @param item
	 * @return index
	 */
	int getIndex(IValidationItem item);

	/**
	 * Let this create new item for you.
	 * 
	 * @return Validation item
	 */
	public IValidationItem newItem();

	/**
	 * Get all items in the list.
	 * 
	 * @return collection of items
	 */
	public ArrayList<IValidationItem> getValidationItems();

	/**
	 * Remove all items.
	 */
	public void reset();

}
