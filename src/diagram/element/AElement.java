package diagram.element;

import org.eclipse.swt.widgets.Canvas;

import diagram.state.*;
import exception.ElementNotFoundException;
import interfaces.IDrawingState;
import interfaces.IElement;
import widget.tab.SubEditor;
import widget.window.MainWindow;

import java.util.ArrayList;

public abstract class AElement implements IElement {
	
	protected IDrawingState state;
	private ArrayList<IElement> connected;

	public AElement() {
		connected = new ArrayList<>();
		state = NormalState.getInstance();
	}

	protected Canvas getCanvas() {
		return ((SubEditor) MainWindow.getInstance().getEditor().getActiveSubEditor()).getCanvas();
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
		state = NormalState.getInstance();
	}

	@Override
	public IDrawingState getState() {
		return state;
	}

	@Override
	public boolean isActive() {
		return getState() != NormalState.getInstance();
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

	@Override
	public ArrayList<IElement> getConnectedElements() {
		return connected;
	}

	@Override
	public void connect(IElement element) {
		connected.add(element);
	}

	@Override
	public void disconnect(IElement element) {
		if (!connected.remove(element)) {
			throw new ElementNotFoundException("Element not found");
		}
	}

}
