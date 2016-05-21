package widget.editor;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.TabItem;

import diagram.element.Line;
import diagram.state.NormalState;
import exception.ElementNotFoundException;
import exception.FlowchartEditorException;
import interfaces.ICanvas;
import interfaces.IDiagram;
import interfaces.IElement;
import interfaces.ISubEditor;
import main.Main;
import widget.window.MainWindow;

import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;

public class SubEditor extends TabItem
		implements ISubEditor, PaintListener, MouseListener, DragDetectListener, MouseMoveListener {

	private String title;
	private Canvas canvas;
	private List<IElement> elements = new ArrayList<>();
	private IDiagram diagram;

	public SubEditor(Editor parent, int style) {
		super(parent, style);
		initialize();
	}

	public SubEditor(Editor parent) {
		this(parent, SWT.NONE);
	}

	protected void initialize() {
		canvas = new Canvas(this);
		super.setControl(canvas);
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
		Main.log("Add " + element.toString());
		elements.add(element);
		draw();
	}

	@Override
	public void removeElement(IElement element) {
		Main.log("Remove " + element.toString());
		for (IElement conn : element.getConnectedElements()) {
			try {
				conn.disconnect(element);
			} catch (FlowchartEditorException e) {
				MainWindow.getInstance().setStatus(e.getMessage());
			}
		}
		elements.remove(element);
		draw();
	}

	@Override
	public IElement getElement(int x, int y) {
		IElement retval = null;
		for (IElement element : elements) {
			IElement ans = element.checkBoundary(x, y);
			if (ans != null) {
				if (ans instanceof Line && retval != null) {
					continue;
				}
				retval = ans;
			}
		}
		// Main.log("Dapet elemen " + retval);
		return retval;
	}

	@Override
	public List<IElement> getElements() {
		return elements;
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		e.x -= getCanvas().getTranslateX();
		e.y -= getCanvas().getTranslateY();
		((MouseListener) MainWindow.getInstance().getEditor().getActiveTool()).mouseDoubleClick(e);
	}

	@Override
	public void mouseDown(MouseEvent e) {
		e.x -= getCanvas().getTranslateX();
		e.y -= getCanvas().getTranslateY();
		((MouseListener) MainWindow.getInstance().getEditor().getActiveTool()).mouseDown(e);
	}

	@Override
	public void mouseUp(MouseEvent e) {
		e.x -= getCanvas().getTranslateX();
		e.y -= getCanvas().getTranslateY();
		((MouseListener) MainWindow.getInstance().getEditor().getActiveTool()).mouseUp(e);
	}

	@Override
	public void dragDetected(DragDetectEvent e) {
		((DragDetectListener) MainWindow.getInstance().getEditor().getActiveTool()).dragDetected(e);
	}

	@Override
	public void mouseMove(MouseEvent e) {
		e.x -= getCanvas().getTranslateX();
		e.y -= getCanvas().getTranslateY();
		((MouseMoveListener) MainWindow.getInstance().getEditor().getActiveTool()).mouseMove(e);
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
		getCanvas().clear();
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
			if (!element.getState().equals(NormalState.getInstance())) {
				ans.add(element);
			}
		}
		return ans;
	}

	@Override
	public ICanvas getCanvas() {
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

	@Override
	public void setDiagram(IDiagram diagram) {
		this.diagram = diagram;
	}

	@Override
	public IDiagram getDiagram() {
		return diagram;
	}

}
