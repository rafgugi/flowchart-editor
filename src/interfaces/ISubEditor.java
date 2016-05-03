package interfaces;

import java.util.List;
import java.util.UUID;

public interface ISubEditor extends IWidget {

	/**
	 * Get the tab title (or name).
	 *
	 * @return Tab title
	 */
	public String getTitle();

	/**
	 * Set the tab title (or name).
	 *
	 * @param Tab title
	 */
	public void setTitle(String name);

	/**
	 * Add an element
	 *
	 * @param element to be added
	 */
	public void addElement(IElement element);

	/**
	 * Remove an element
	 *
	 * @param element to be removed
	 */
	public void removeElement(IElement element);

	/**
	 * Get an element given the point to be checked whether the point is inside
	 * the element. Return the most front element. Return null if no element
	 * found.
	 *
	 * @return IElement | null
	 */
	public IElement getElement(int x, int y);

	/**
	 * Get an element given ID. Throws exception if no element found.
	 *
	 * @return IElement
	 * @throws ElementNotFoundException
	 */
	public IElement getElement(UUID id);

	/**
	 * Get an element given ID. Throws exception if no element found.
	 *
	 * @return IElement
	 * @throws ElementNotFoundException
	 */
	public IElement getElement(String id);

	/**
	 * Get collection of elements.
	 *
	 * @return Collection of elements
	 */
	public List<IElement> getElements();

	/**
	 * Close this tab.
	 */
	public void close();

	/**
	 * Draw all elements.
	 */
	public void draw();

	/**
	 * Clear canvas. Fill canvas with white color.
	 */
	public void clearCanvas();

	/**
	 * Set all elements state to be normal.
	 */
	public void deselectAll();

	/**
	 * Get all selected elements.
	 *
	 * @return Collection of elected elements
	 */
	public List<IElement> getSelectedElements();
}
