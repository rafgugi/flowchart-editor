package widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

import interfaces.IEditor;
import interfaces.IMenuBar;
import interfaces.IToolStrip;
import interfaces.IWindow;
import widget.menu.MenuBar;
import widget.tab.Editor;
import widget.toolbar.ToolStrip;

public class Window extends Shell implements IWindow {

	private IMenuBar menuBar;
	private IEditor editor;
	private IToolStrip toolstrip;
	private static Window instance;

	private Window() {
		super(new Display());
		System.out.println("Constructornya Window");
		initialize();
	}

	public static Window getInstance() {
		if (instance == null) {
			System.out.println("Bikin Window di Window.getInstance");
			instance = new Window();
			if (instance == null) {
				System.out.println("instance == null");
			}
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
		setText("Window.java");

		setBar(new MenuBar(this));

		GridData gridData;

		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		this.setLayout(layout);

		Group group = new Group(this, SWT.PUSH);
		group.setText("Group text");
		gridData = new GridData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.verticalSpan = 2;
		group.setLayoutData(gridData);
		
		setToolStrip(new ToolStrip(this));
		setEditor(new Editor(this));

		Group group2 = new Group(this, SWT.PUSH);
		group2.setText("Group2 text");
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = SWT.FILL;
		group2.setLayoutData(gridData);
	}

	@Override
	public void setTitle(String name) {
		this.setText(name);
	}

	@Override
	public String getTitle() {
		return this.getText();
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
}
