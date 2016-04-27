package interfaces;

import java.util.ArrayList;
import java.util.UUID;

public interface IElement extends JSONAble {

	/**
	 * Get current element state
	 *
	 * @return current state
	 */
	public IDrawingState getState();

	/**
	 * Set the element state to selected.
	 */
	public void select();

	/**
	 * Set the element state to normal.
	 */
	public void deselect();

	/**
	 * Check if the element selected.
	 *
	 * @return is active
	 */
	public boolean isActive();

	/**
	 * Draw this element based on the state.
	 */
	public void draw();

	/**
	 * Draw this element based on normal state.
	 */
	public void renderNormal();

	/**
	 * Draw this element based on selected state.
	 */
	public void renderEdit();

	/**
	 * Get element ID. ID is generated randomly on create.
	 *
	 * @return UUID id
	 */
	public UUID getId();

	/**
	 * Check if the point is in the element area. Return this
	 * element if true. Otherwise return null.
	 *
	 * @param x
	 * @param y
	 * @return IElement | null
	 */
	public IElement checkBoundary(int x, int y);

	/**
	 * Check if the element is inside the square given the points. 
	 * Return this element if true. Otherwise return null.
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return IElement | null
	 */
	public IElement checkBoundary(int x1, int y1, int x2, int y2);

	/**
	 * Triggered when the element is dragged.
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void drag(int x1, int y1, int x2, int y2);

	/**
	 * Get elements that connected to this element.
	 *
	 * @return Connected elements
	 */
	public ArrayList<IElement> getConnectedElements();

	/**
	 * Connect an element.
	 *
	 * @param Element to be connected.
	 */
	public void connect(IElement element);

	/**
	 * Disconnect an element.
	 *
	 * @param Element to be disconnected.
	 */
	public void disconnect(IElement element);
}
