package widget.tab;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.TabItem;
import interfaces.ISubEditor;

public class SubEditor extends TabItem implements ISubEditor, MouseListener {

	private String title;
	private Canvas canvas;
	private Editor editor;

	public SubEditor(Editor parent, int style) {
		super(parent, style);
		editor = parent;
		initialize();
	}

	public SubEditor(Editor parent) {
		this(parent, SWT.NONE);
	}

	@Override
	public void initialize() {
		canvas = new Canvas(this.getParent(), SWT.BORDER);
		super.setControl(canvas);
		
		canvas.addMouseListener(this);
	}

	@Override
	public void checkSubclass() {
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
		super.setText(title);
	}

	@Override
	public void close() {
		super.dispose();
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		((MouseListener)editor.getActiveTool()).mouseDoubleClick(e);
	}

	@Override
	public void mouseDown(MouseEvent e) {
		((MouseListener)editor.getActiveTool()).mouseDown(e);
	}

	@Override
	public void mouseUp(MouseEvent e) {
		((MouseListener)editor.getActiveTool()).mouseUp(e);
	}
	
	public void draw() {
		GC gc = new GC(canvas.getDisplay());
		gc.drawRectangle(10, 10, 20, 20);
		canvas.print(gc);
		canvas.redraw();
	}

}
