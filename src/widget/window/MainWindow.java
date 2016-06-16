package widget.window;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;

import interfaces.IEditor;
import interfaces.IMainWindow;
import interfaces.IMenuBar;
import interfaces.IToolStrip;
import interfaces.IValidationList;
import widget.editor.Editor;
import widget.menu.MenuBar;
import widget.toolbar.ToolStrip;
import widget.validation.ValidationList;

public class MainWindow extends AWindow implements IMainWindow {

	private IMenuBar menuBar;
	private IEditor editor;
	private IToolStrip toolstrip;
	private IValidationList validationList;
	private Label status;

	private static MainWindow instance;

	private MainWindow() {
	}

	public static MainWindow getInstance() {
		if (instance == null) {
			instance = new MainWindow();
		}
		return instance;
	}

	protected void initialize() {
		setTitle("Window.java");

		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		super.setLayout(layout);

		/* Create menu bar */
		setBar(new MenuBar(this));

		/* Create main window's components */
		setToolStrip(new ToolStrip(this));
		setEditor(new Editor(this));
		setValidationList(new ValidationList(this));

		status = new Label(this, SWT.NONE);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = SWT.FILL;
		status.setLayoutData(gridData);
		setStatus("No diagram yet.");

		super.pack();
	}

	@Override
	public IMenuBar getBar() {
		return menuBar;
	}

	private void setBar(IMenuBar bar) {
		super.setMenuBar((Menu) bar);
		this.menuBar = bar;
	}

	@Override
	public IEditor getEditor() {
		return editor;
	}

	private void setEditor(IEditor editor) {
		this.editor = editor;
	}

	@Override
	public IToolStrip getToolStrip() {
		return toolstrip;
	}

	private void setToolStrip(IToolStrip toolstrip) {
		this.toolstrip = toolstrip;
	}

	public void setStatus(String status) {
		this.status.setText(status);
	}

	@Override
	public IValidationList getValidationList() {
		return validationList;
	}

	private void setValidationList(ValidationList validationList) {
		this.validationList = validationList;
	}
}
