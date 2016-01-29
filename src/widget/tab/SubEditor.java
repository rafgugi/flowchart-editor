package widget.tab;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.TabItem;
import interfaces.ISubEditor;

public class SubEditor extends TabItem implements ISubEditor {

	private String title;
	private Canvas canvas;

	public SubEditor(Editor parent, int style) {
		super(parent, style);
		initialize();
	}

	public SubEditor(Editor parent) {
		this(parent, SWT.NONE);
	}

	@Override
	public void initialize() {
		canvas = new Canvas(this.getParent(), SWT.BORDER);
		this.setControl(canvas);
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
		this.setText(title);
	}

	@Override
	public void close() {
		this.dispose();
	}

}
