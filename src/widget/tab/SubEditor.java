package widget.tab;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.TabItem;

import diagram.element.AElement;
import interfaces.IElement;
import interfaces.ISubEditor;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

public class SubEditor extends TabItem implements
	ISubEditor, MouseListener, DragDetectListener, PaintListener {

	private String title;
	private Canvas canvas;
	private Editor editor;
	private ArrayList<IElement> elements;

	public SubEditor(Editor parent, int style) {
		super(parent, style);
		editor = parent;
		elements = new ArrayList<>();
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
		canvas.addDragDetectListener(this);
		canvas.addPaintListener(this);
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
	public void addElement(IElement element) {
		((AElement) element).setCanvas(canvas);
		elements.add(element);
		draw();
	}

	@Override
	public void removeElement(IElement element) {
		elements.remove(element);
		draw();
	}

	@Override
	public IElement getElement(int x, int y) {
		IElement ans = null;
		for (IElement element : elements) {
			if (element.checkBoundary(x, y)) {
				ans = element;
			}
		}
		return ans;
	}

	@Override
	public ArrayList<IElement> getElements(int x, int y) {
		ArrayList<IElement> ans = new ArrayList<>();
		for (IElement element : elements) {
			if (element.checkBoundary(x, y)) {
				ans.add(element);
			}
		}
		return ans;
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		((MouseListener) editor.getActiveTool()).mouseDoubleClick(e);
	}

	@Override
	public void mouseDown(MouseEvent e) {
		((MouseListener) editor.getActiveTool()).mouseDown(e);
	}

	@Override
	public void mouseUp(MouseEvent e) {
		((MouseListener) editor.getActiveTool()).mouseUp(e);
	}

	@Override
	public void dragDetected(DragDetectEvent e) {
		((DragDetectListener) editor.getActiveTool()).dragDetected(e);
	}

	@Override
	public void draw() {
		clearCanvas();
		for (IElement element : elements) {
			element.draw();
		}
	}

	@Override
	public void clearCanvas() {
		GC gc = new GC(canvas);
		gc.setBackground(new Color(gc.getDevice(), 255, 255, 255));
		gc.fillRectangle(0, 0, canvas.getSize().x, canvas.getSize().y);
		gc.dispose();
	}

	@Override
	public void paintControl(PaintEvent pe) {
		draw();
	}

}
