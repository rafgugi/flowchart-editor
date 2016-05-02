package interfaces;

import java.util.ArrayList;

/**
 * Validation item. When clicked, action() is called.
 */
public interface IValidationItem {

	/**
	 * Called when this item clicked.
	 */
	public void action();

	/**
	 * Get current title. Title shown on the item.
	 * 
	 * @return title
	 */
	public String getTitle();

	/**
	 * Set current title.
	 * 
	 * @param title
	 */
	public void setTitle(String title);

	/**
	 * Get additional description.
	 * 
	 * @return description
	 */
	public String getDescription();

	/**
	 * Set additional description.
	 * 
	 * @param desc
	 */
	public void setDescription(String desc);

	/**
	 * Get collection of fail elements.
	 * 
	 * @return collection of elements
	 */
	public ArrayList<IElement> getProblems();

	/**
	 * Add a fail element.
	 * 
	 * @param element
	 */
	public void addProblems(IElement element);
}
