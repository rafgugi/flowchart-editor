package widget.tab;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.TabItem;

import diagram.element.AElement;
import diagram.state.EditState;
import interfaces.IElement;
import interfaces.ISubEditor;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

public class SubEditor extends TabItem
		implements ISubEditor, PaintListener, MouseListener, DragDetectListener, MouseMoveListener {

	private String title;
	private Canvas canvas;
	private Editor editor;
	private List<IElement> elements;

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
		canvas.addMouseMoveListener(this);
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
		System.out.println("Add " + element.toString());
		((AElement) element).setCanvas(canvas);
		elements.add(element);
		draw();
	}

	@Override
	public void removeElement(IElement element) {
		System.out.println("Remove " + element.toString());
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
	public List<IElement> getElements() {
		return elements;
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
	public void mouseMove(MouseEvent e) {
		((MouseMoveListener) editor.getActiveTool()).mouseMove(e);
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

	@Override
	public void deselectAll() {
		ArrayList<IElement> temp = new ArrayList<>();
		for (Iterator<IElement> iterator = getElements().iterator(); iterator.hasNext();) {
			IElement e = iterator.next();
			if (e.isActive()) {
				temp.add(e);
			}
		}
		for (IElement e : temp) {
			e.deselect();
		}
	}

	@Override
	public List<IElement> getSelectedElements() {
		ArrayList<IElement> ans = new ArrayList<>();
		for (IElement element : getElements()) {
			if (element.getState().equals(EditState.getInstance())) {
				ans.add(element);
			}
		}
		return ans;
	}

	public GC getGC() {
		return new GC(canvas);
	}

}
