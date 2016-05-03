package interfaces;

public interface IDrawingState {

	/**
	 * Draw an element based on current state
	 *
	 * @param element
	 */
	public void draw(IElement element);

}
