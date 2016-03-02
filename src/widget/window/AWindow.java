package widget.window;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import interfaces.IWindow;

public abstract class AWindow extends Shell implements IWindow {

	public AWindow(Display display) {
		super(display);
	}

	public AWindow() {
		this(Display.getDefault());
	}

	@Override
	public abstract void initialize();

	@Override
	public void show() {
		initialize();
		super.open();
		while (!super.isDisposed()) {
			if (!super.getDisplay().readAndDispatch()) {
				super.getDisplay().sleep();
			}
		}
		super.dispose();
	}

	@Override
	public void checkSubclass() {
	}

	@Override
	public void setTitle(String name) {
		super.setText(name);
	}

	@Override
	public String getTitle() {
		return super.getText();
	}

}
