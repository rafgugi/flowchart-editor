package widget.window;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;

import interfaces.IWidget;

public class StatusBar extends Label implements IWidget {

	public StatusBar(MainWindow parent, int style) {
		super(parent, style);
		initialize();
	}

	public StatusBar(MainWindow parent) {
		this(parent, SWT.NONE);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void checkSubclass() {
	}

}
