package widget.window.property;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import diagram.element.Rectangle;
import interfaces.IElement;

public class ProcessProperty extends APropertyWindow {

	private Text input;
	private Label label;
	protected Button okButton;

	public ProcessProperty(IElement element) {
		super(element);
	}

	@Override
	public void initialize() {
		setText("Property");

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
		label.setText("Text:  ");

		input = new Text(this, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		gridData = new GridData();
		gridData.heightHint = 50;
		gridData.widthHint = 200;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		input.setLayoutData(gridData);
		input.setText(((Rectangle) getElement()).getText());

		okButton = new Button(this, SWT.PUSH);
		okButton.setText("     OK     ");
		gridData = new GridData(SWT.END, SWT.CENTER, false, false);
		gridData.horizontalSpan = 2;
		okButton.setLayoutData(gridData);
		okButton.addListener(SWT.Selection, this);

		super.pack();
	}

	@Override
	public void execute() {
		Rectangle element = (Rectangle) super.getElement();
		String text = input.getText();
		System.out.println(text);
		element.setText(text);
		this.dispose();
	}

}