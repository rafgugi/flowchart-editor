package widget.window.property;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;

import diagram.element.FlowLine;
import interfaces.IElement;
import widget.window.MainWindow;

public class FlowLineProperty extends APropertyWindow {

	private Combo input;
	private Label label;
	protected Button okButton;
	private static String[] choices = new String[] { FlowLine.NONE, FlowLine.YES, FlowLine.NO };

	public FlowLineProperty(IElement element) {
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

		input = new Combo(this, SWT.READ_ONLY);
		input.setItems(choices);
		gridData = new GridData();
		gridData.heightHint = 50;
		gridData.widthHint = 200;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		input.setLayoutData(gridData);
		/* Select current item */
		input.select(Arrays.asList(choices).indexOf(((FlowLine) getElement()).getText()));

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
		FlowLine element = (FlowLine) super.getElement();
		String text = input.getText();
		element.setText(text);
		MainWindow.getInstance().getEditor().getActiveSubEditor().draw();
		this.dispose();
	}

}
