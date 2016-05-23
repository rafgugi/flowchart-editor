package widget.window;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class DialogWindow extends AWindow {
	
	private Text input;
	private Label label;

	protected void initialize() {
		setText("DialogWindow.java");

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		super.setLayout(layout);
		
		GridData gridData;
		
		label = new Label(this, SWT.NONE);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.TOP;
		gridData.grabExcessVerticalSpace = false;
		label.setLayoutData(gridData);
		label.setText("Label");
		
		input = new Text(this, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		gridData = new GridData();
		gridData.heightHint = 50;
		gridData.widthHint = 200;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		input.setLayoutData(gridData);
		
		Button enter = new Button(this, SWT.PUSH);
		enter.setText("     OK     ");
		gridData = new GridData(SWT.END, SWT.CENTER, false, false);
		gridData.horizontalSpan = 2;
		enter.setLayoutData(gridData);
		
		super.pack();
	}
	
}
