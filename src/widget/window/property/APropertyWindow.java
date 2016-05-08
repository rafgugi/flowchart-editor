package widget.window.property;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import interfaces.IElement;
import widget.window.AWindow;

public abstract class APropertyWindow extends AWindow implements Listener {

	private IElement element;

	public APropertyWindow(IElement element) {
		setElement(element);
	}

	/**
	 * Get element for this property window.
	 * 
	 * @return element
	 */
	public IElement getElement() {
		return element;
	}

	/**
	 * Set element for this property window.
	 * 
	 * @param element
	 */
	public void setElement(IElement element) {
		this.element = element;
	}

	/**
	 * Apply changes from this property window.
	 */
	public abstract void execute();

	@Override
	public void handleEvent(Event event) {
		execute();
	}

}
