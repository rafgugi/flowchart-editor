package widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import interfaces.IEditor;
import interfaces.IMenuBar;
import interfaces.IWindow;
import widget.menu.MenuBar;
import widget.tab.Editor;

public class Window extends Shell implements IWindow {

	private IMenuBar menuBar;
	private IEditor editor;
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

		Display display = this.getDisplay();
		Image image = new Image(display, 16, 16);
		Color color = display.getSystemColor(SWT.COLOR_RED);
		GC gc = new GC(image);
		gc.setBackground(color);
		gc.fillRectangle(image.getBounds());
		gc.dispose();
		ToolBar toolBar = new ToolBar(this, SWT.VERTICAL | SWT.BORDER);

		gridData = new GridData();
		gridData.verticalAlignment = SWT.FILL;
		toolBar.setLayoutData(gridData);
		for (int i = 0; i < 12; i++) {
			int style = SWT.RADIO;
			ToolItem item = new ToolItem(toolBar, style);
			item.setImage(image);
		}
		toolBar.pack();

		setEditor(new Editor(this));

		Group group2 = new Group(this, SWT.PUSH);
		group2.setText("Group2 text");
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = SWT.FILL;
		group2.setLayoutData(gridData);
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
	public void setTitle(String name) {
		this.setText(name);
	}

	@Override
	public String getTitle() {
		return this.getText();
	}
}
