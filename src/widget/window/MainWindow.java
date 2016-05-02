package widget.window;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Menu;
import interfaces.IEditor;
import interfaces.IMainWindow;
import interfaces.IMenuBar;
import interfaces.IToolStrip;
import interfaces.IValidationList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;

import widget.menu.MenuBar;
import widget.tab.Editor;
import widget.toolbar.ToolStrip;
import widget.validation.ValidationList;

public class MainWindow extends AWindow implements IMainWindow {

	private IMenuBar menuBar;
	private IEditor editor;
	private IToolStrip toolstrip;
	private IValidationList validationList;
	private static MainWindow instance;
	private Label status;

	private MainWindow() {
	}

	public static MainWindow getInstance() {
		if (instance == null) {
			instance = new MainWindow();
		}
		return instance;
	}

	@Override
	public void initialize() {
		setTitle("Window.java");

		setBar(new MenuBar(this));

		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		super.setLayout(layout);

		setToolStrip(new ToolStrip(this));
		setEditor(new Editor(this));
		
		validationList = new ValidationList(this, SWT.BORDER);

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
}
