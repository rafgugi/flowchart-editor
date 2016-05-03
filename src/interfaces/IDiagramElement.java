package interfaces;

public interface IDiagramElement extends IElement {

	/**
	 * This function will be triggered when the element 
	 * is doubleclicked
	 */
	public void action();

	/**
	 * Get diagram text.
	 * 
	 * @return text
	 */
	public String getText();

	/**
	 * Set diagram text.
	 *
	 * @param text
	 */
	public void setText(String text);

}
