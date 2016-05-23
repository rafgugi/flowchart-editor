package widget.window.property;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import diagram.element.TwoDimensional;
import interfaces.IElement;
import widget.window.MainWindow;

public class JudgmentProperty extends APropertyWindow {

	private Text input;
	private Label label;
	protected Button okButton;

	public JudgmentProperty(IElement element) {
		super(element);
	}

	protected void initialize() {
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

		input = new Text(this, SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint = 200;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		input.setLayoutData(gridData);
		input.setText(((TwoDimensional) getElement()).getText());

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
		TwoDimensional element = (TwoDimensional) super.getElement();
		String text = input.getText();
		element.setText(text);
		MainWindow.getInstance().getEditor().getActiveSubEditor().draw();
		this.dispose();
	}

}
