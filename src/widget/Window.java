package widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import interfaces.IMenuBar;
import interfaces.IWindow;
import widget.menu.MenuBar;

public class Window extends Shell implements IWindow {
	
	private IMenuBar menuBar;
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
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		this.setLayout(layout);

		this.setText("Window.java");
		this.setSize(250, 200);
		
		this.setBar(new MenuBar(this));

//		RowLayout layout = new RowLayout();
//		layout.marginLeft = 30;
//		layout.marginTop = 30;
//		layout.marginBottom = 150;
//		layout.marginRight = 150;
//		this.setLayout(layout);
		
		final TabFolder tabFolder = new TabFolder(this, SWT.PUSH);
//		Rectangle clientArea = this.getClientArea();
//		tabFolder.setLocation(clientArea.x, clientArea.y);
//		tabFolder.setSize(100, 100);
		for (int i = 1; i <= 5; i++) {
			TabItem item = new TabItem(tabFolder, SWT.NONE);
			item.setText("TabItem " + i);
			final Canvas c = new Canvas(tabFolder, SWT.BORDER);
			c.setSize(50, 50);
//			Button button = new Button (tabFolder, SWT.PUSH);
//			button.setText ("Page " + i);
			item.setControl(c);
		}
		tabFolder.pack ();
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
	public void setTitle(String name) {
		this.setText(name);
	}

	@Override
	public String getTitle() {
		return this.getText();
	}
}
