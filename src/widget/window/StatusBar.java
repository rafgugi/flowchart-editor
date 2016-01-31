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

		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = SWT.FILL;
		super.setLayoutData(gridData);
		super.setText("Ready");
	}

	@Override
	public void checkSubclass() {
	}

}
