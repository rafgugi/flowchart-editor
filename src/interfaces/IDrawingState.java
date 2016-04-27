package interfaces;

public interface IDrawingState {
	/**
	 * Draw an element based on current state
	 *
	 * @param element to be drawn 
	 */
	public void draw(IElement element);
}
