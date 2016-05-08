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
import org.eclipse.swt.events.MouseMoveListener;
import widget.toolbar.ToolStrip;
import widget.window.MainWindow;

public abstract class ATool extends ToolItem
		implements ITool, Listener, MouseListener, DragDetectListener, MouseMoveListener {

	private String iconName;
	private String iconFolder = "/resources/icons/";
	private String toolName;

	public ATool(ToolStrip parent, int style) {
		super(parent, style);
		initialize();
		super.addListener(SWT.Selection, this);
	}

	public ATool(ToolStrip parent) {
		this(parent, SWT.RADIO);
	}

	public ATool(ToolStrip parent, String toolName) {
		this(parent, SWT.RADIO);
		setToolName(toolName);
	}

	@Override
	public void initialize() {
		generateIcon();
	}

	/**
	 * Generate icon for this tool.
	 */
	protected void generateIcon() {
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

	/**
	 * Set the tool name.
	 * 
	 * @param toolName
	 */
	protected void setToolName(String toolName) {
		this.toolName = toolName;
		super.setToolTipText(getToolName());
	}

	@Override
	public void checkSubclass() {
	}

	@Override
	public void execute() {
		MainWindow.getInstance().setStatus(getToolName());
	}

	/**
	 * Get icon file name.
	 * 
	 * @return filename
	 */
	protected String getIconName() {
		return iconName;
	}

	/**
	 * Set icon file name.
	 * 
	 * @param filename
	 */
	protected void setIconName(String iconName) {
		this.iconName = iconName;
	}

	/**
	 * Get icons directory.
	 * 
	 * @return directory
	 */
	protected String getIconFolder() {
		return iconFolder;
	}

	/**
	 * Set icons directory.
	 * 
	 * @param directory
	 */
	protected void setIconFolder(String iconFolder) {
		this.iconFolder = iconFolder;
	}

	@Override
	public void select() {
		super.setSelection(true);
		MainWindow.getInstance().getEditor().setActiveTool(this);
	}

	@Override
	public void deselect() {
		super.setSelection(false);
	}

	@Override
	public void handleEvent(Event event) {
		if (MainWindow.getInstance().getEditor().getActiveTool() != this) {
			for (ITool tool : MainWindow.getInstance().getToolStrip().getTools()) {
				tool.deselect();
			}
			this.select();
			execute();
		}
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		// System.out.println("Unimplemented mouseDoubleClick \n(" +
		// e.toString() + ")");
	}

	@Override
	public void dragDetected(DragDetectEvent e) {
	}

	@Override
	public void mouseMove(MouseEvent e) {
	}

	protected ISubEditor getActiveSubEditor() {
		return MainWindow.getInstance().getEditor().getActiveSubEditor();
	}

}
