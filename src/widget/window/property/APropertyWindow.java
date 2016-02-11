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
	
	public IElement getElement() {
		return element;
	}

	public void setElement(IElement element) {
		this.element = element;
	}

	public abstract void execute();

	@Override
	public void handleEvent(Event event) {
		execute();
	}
	
}
