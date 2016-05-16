package interfaces;

public interface IPropertyWindow extends IWindow {

	/**
	 * Get element for this property window.
	 * 
	 * @return element
	 */
	public IElement getElement();

	/**
	 * Set element for this property window.
	 * 
	 * @param element
	 */
	public void setElement(IElement element);

	/**
	 * Apply changes from this property window.
	 */
	public void execute();

}
