package widget;

import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

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
		menuBar = new MenuBar(this);
        this.setText("Center");
        this.setSize(250, 200);

        RowLayout layout = new RowLayout();
        layout.marginLeft = 30;
        layout.marginTop = 30;
        layout.marginBottom = 150;
        layout.marginRight = 150;
        
		this.setLayout(layout);
		this.setBar(new MenuBar(this));
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
