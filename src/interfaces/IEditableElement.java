package interfaces;

public interface IEditableElement extends IElement {

	/**
	 * Create edit points on the 8 points of element, then put edit points in
	 * private variable editPoints
	 */
	public void createEditPoints();

	/**
	 * Triggered when the element is dragged by another element. Parameter e is
	 * usually an EditPoint. This element perform drag depends on which
	 * EditPoint is dragged.
	 * 
	 * @param x
	 * @param y
	 * @param element
	 */
	public void drag(int x, int y, IElement e);

}
