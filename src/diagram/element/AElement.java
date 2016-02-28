package diagram.element;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;

import diagram.state.*;
import exception.ElementNotFoundException;
import interfaces.IDrawingState;
import interfaces.IElement;
import widget.window.MainWindow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AElement implements IElement {

	protected Canvas canvas;
	private IDrawingState state;
	private ArrayList<IElement> connected;

	public AElement() {
		connected = new ArrayList<>();
		state = NormalState.getInstance();
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	@Override
	public void select() {
		if (state == EditState.getInstance()) {
			return;
		}
		System.out.println("Select " + this.toString());
		state = EditState.getInstance();
	}

	@Override
	public void deselect() {
		if (!isActive()) {
			return;
		}
		System.out.println("Deselect " + this.toString());
		ArrayList<IElement> temp = new ArrayList<>();
		for (Iterator<IElement> iter = getConnectedElements().iterator(); iter.hasNext();) {
			IElement connected = iter.next();
			if (connected instanceof EditPoint) {
				temp.add(connected);
			}
		}
		for (IElement e : temp) {
			e.disconnect(this);
			this.disconnect(e);
			MainWindow m = MainWindow.getInstance();
			m.getEditor().getActiveSubEditor().removeElement(e);
		}
		state = NormalState.getInstance();
	}

	@Override
	public IDrawingState getState() {
		return state;
	}

	@Override
	public boolean isActive() {
		if (getState() == NormalState.getInstance()) {
			return false;
		}
		return true;
	}

	@Override
	public void draw() {
		state.draw(this);
	}

	public void drawEditPoint(Point p) {
		GC gc = new GC(canvas);
		Color black = new Color(gc.getDevice(), 0, 0, 0);
		Color white = new Color(gc.getDevice(), 255, 255, 255);
		gc.setForeground(black);
		gc.setBackground(white);
		int x, y, length;
		x = p.x - 2;
		y = p.y - 2;
		length = 5;
		gc.drawRectangle(x, y, length, length);
		gc.fillRectangle(x + 1, y + 1, length - 1, length - 1);
		gc.dispose();
	}

	public void drawEditPoint(List<Point> points) {
		for (Point p : points) {
			drawEditPoint(p);
		}
	}

	public ArrayList<IElement> getConnectedElements() {
		return connected;
	}

	public void connect(IElement element) {
		connected.add(element);
	}

	public void disconnect(IElement element) {
		if (!connected.remove(element)) {
			throw new ElementNotFoundException("Element not found");
		}
		System.out.println("Disconnect " + toString() + " from " + element.toString());
	}

	public void createEditPoints(ArrayList<Point> points) {
		for (int j = 0; j < points.size(); j++) {
			Point point = points.get(j);
			EditPoint ep = new EditPoint(this, point.x, point.y, j);
			this.connect(ep);
			ep.connect(this);
			MainWindow.getInstance().getEditor().getActiveSubEditor().addElement(ep);
//			ep.select();
		}
	}

}
