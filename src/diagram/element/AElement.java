package diagram.element;

import org.eclipse.swt.widgets.Canvas;

import diagram.state.*;
import exception.ElementNotFoundException;
import interfaces.IDrawingState;
import interfaces.IElement;
import widget.window.MainWindow;

import java.util.ArrayList;

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
		
		/* Yang mau dibuang dimasukin temp dulu biar ga rusak */
		ArrayList<IElement> temp = new ArrayList<>();
		for (IElement connected : getConnectedElements()) {
			if (connected instanceof EditPoint) {
				temp.add(connected);
			}
		}
		
		/* Mbuang yang harus dibuang */
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

	@Override
	public void drag(int x1, int y1, int x2, int y2) {
		deselect();
		select();
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
	}

}
