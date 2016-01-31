package widget.window;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

import interfaces.IEditor;
import interfaces.IMenuBar;
import interfaces.IToolStrip;
import interfaces.IWindow;
import widget.menu.MenuBar;
import widget.tab.Editor;
import widget.toolbar.ToolStrip;

public class MainWindow extends Shell implements IWindow {

	private IMenuBar menuBar;
	private IEditor editor;
	private IToolStrip toolstrip;
	private static MainWindow instance;
	private StatusBar status;

	private MainWindow() {
		super(new Display());
		initialize();
	}

	public static MainWindow getInstance() {
		if (instance == null) {
			instance = new MainWindow();
		}
		return instance;
	}

	@Override
	public void show() {
		super.open();
		while (!super.isDisposed()) {
			if (!super.getDisplay().readAndDispatch()) {
				super.getDisplay().sleep();
			}
		}
	}

	@Override
	public void checkSubclass() {
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
		
		this.status = new StatusBar(this);
	}

	@Override
	public void setTitle(String name) {
		super.setText(name);
	}

	@Override
	public String getTitle() {
		return super.getText();
	}

	@Override
	public IMenuBar getBar() {
		return menuBar;
	}

	@Override
	public void setBar(IMenuBar bar) {
		super.setMenuBar((Menu) bar);
		this.menuBar = bar;
	}

	@Override
	public IEditor getEditor() {
		return editor;
	}

	@Override
	public void setEditor(IEditor editor) {
		this.editor = editor;
	}

	@Override
	public IToolStrip getToolStrip() {
		return toolstrip;
	}

	@Override
	public void setToolStrip(IToolStrip toolstrip) {
		this.toolstrip = toolstrip;
	}
	
	public void setStatus(String status) {
		this.status.setText(status);
	}
}