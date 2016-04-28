package widget.tab;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.TabItem;

import diagram.state.EditState;
import exception.ElementNotFoundException;
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
		super.setControl(getCanvas());

		getCanvas().addMouseListener(this);
		getCanvas().addMouseMoveListener(this);
		getCanvas().addDragDetectListener(this);
		getCanvas().addPaintListener(this);
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
		IElement retval = null;
		for (IElement element : elements) {
			IElement ans = element.checkBoundary(x, y);
			if (ans != null) {
				retval = ans;
			}
		}
		// System.out.println("Dapet elemen " + retval);
		return retval;
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
		GC gc = new GC(getCanvas());
		gc.setBackground(new Color(gc.getDevice(), 255, 255, 255));
		gc.fillRectangle(0, 0, getCanvas().getSize().x, getCanvas().getSize().y);
		gc.dispose();
	}

	@Override
	public void paintControl(PaintEvent pe) {
		draw();
	}

	@Override
	public void deselectAll() {
		ArrayList<IElement> temp = new ArrayList<>();
		for (IElement e : getElements()) {
			if (e.isActive()) {
				temp.add(e);
			}
		}
		for (IElement e : temp) {
			e.deselect();
		}
		if (!temp.isEmpty()) {
			draw();			
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
		return new GC(getCanvas());
	}

	public Canvas getCanvas() {
		return canvas;
	}

	@Override
	public IElement getElement(UUID id) {
		for (IElement elem : elements) {
			if (elem.getId().equals(id)) {
				return elem;
			}
		}
		throw new ElementNotFoundException("Can't find ID " + id);
	}

	@Override
	public IElement getElement(String id) {
		return getElement(UUID.fromString(id));
	}

}
