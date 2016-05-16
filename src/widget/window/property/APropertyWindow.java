package widget.window.property;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import interfaces.IElement;
import interfaces.IPropertyWindow;
import widget.window.AWindow;

public abstract class APropertyWindow extends AWindow implements IPropertyWindow, Listener {

	private IElement element;

	public APropertyWindow(IElement element) {
		setElement(element);
	}

	@Override
	public IElement getElement() {
		return element;
	}

	@Override
	public void setElement(IElement element) {
		this.element = element;
	}

	@Override
	public abstract void execute();

	@Override
	public void handleEvent(Event event) {
		execute();
	}

}
