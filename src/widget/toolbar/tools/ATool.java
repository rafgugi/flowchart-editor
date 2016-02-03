package widget.toolbar.tools;

import interfaces.ISubEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolItem;

import interfaces.ITool;
import widget.toolbar.ToolStrip;
import widget.window.MainWindow;

public abstract class ATool extends ToolItem implements ITool, Listener, MouseListener, DragDetectListener {

	private String iconName;
	private String iconFolder;
	private String toolName;

	public ATool(ToolStrip parent, int style) {
		super(parent, style);
		iconFolder = "/resources/icons/";
		initialize();
		super.addListener(SWT.Selection, this);
	}

	public ATool(ToolStrip parent) {
		this(parent, SWT.PUSH);
	}

	@Override
	public void initialize() {
		generateIcon();
	}

	public void generateIcon() {
		if (getIconFolder() == null || getIconName() == null) {
			throw new NullPointerException();
		}
		String iconFile = getIconFolder() + getIconName();
		Display display = Display.getDefault();
		ImageData imageData = new ImageData(getClass().getResourceAsStream(iconFile));
		// imageData.height = 20;
		// imageData.width = 20;
		Image image = new Image(display, imageData);
		super.setImage(image);
	}

	@Override
	public String getToolName() {
		return toolName;
	}

	protected void setToolName(String toolName) {
		this.toolName = toolName;
	}

	@Override
	public void checkSubclass() {
	}

	@Override
	public abstract void execute();

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getIconFolder() {
		return iconFolder;
	}

	public void setIconFolder(String iconFolder) {
		this.iconFolder = iconFolder;
	}

	@Override
	public void handleEvent(Event event) {
		MainWindow.getInstance().getEditor().setActiveTool(this);
		execute();
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		// System.out.println("Unimplemented mouseDoubleClick \n(" +
		// e.toString() + ")");
	}

	@Override
	public void mouseDown(MouseEvent e) {
		// System.out.println("Unimplemented mouseDown \n(" + e.toString() +
		// ")");
	}

	@Override
	public void mouseUp(MouseEvent e) {
		// System.out.println("Unimplemented mouseUp \n(" + e.toString() + ")");
	}

	@Override
	public void dragDetected(DragDetectEvent e) {
		// System.out.println("Unimplemented dragDetected \n(" + e.toString() +
		// ")");
	}
	
	protected ISubEditor getActiveSubEditor() {
		return MainWindow.getInstance().getEditor().getActiveSubEditor();
	}

}
