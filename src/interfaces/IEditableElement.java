package interfaces;

public interface IEditableElement extends IElement {

	/**
	 * Create edit points on the 8 points of element, then
	 * put edit points in private variable editPoints
	 */
	public void createEditPoints();

	/**
	 * Triggered when the element is dragged
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void drag(int x1, int y1, int x2, int y2, IElement e);

}
