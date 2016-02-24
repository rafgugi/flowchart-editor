package diagram.element;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;

import diagram.state.*;
import interfaces.IDrawingState;
import interfaces.IElement;

import java.util.ArrayList;
import java.util.List;

public abstract class AElement implements IElement {

	protected Canvas canvas;
	private IDrawingState state;
	private ArrayList<IElement> connected;

	public AElement() {
		connected = new ArrayList<>();
		deselect();
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	@Override
	public void select() {
		state = EditState.getInstance();
	}

	@Override
	public void deselect() {
		state = NormalState.getInstance();
	}

	@Override
	public IDrawingState getState() {
		return state;
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
		connected.remove(element);
	}

}
