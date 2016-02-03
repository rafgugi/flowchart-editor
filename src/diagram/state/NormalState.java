package diagram.state;

import interfaces.IDrawingState;
import interfaces.IElement;

public class NormalState implements IDrawingState {
	
	private static IDrawingState instance;

	private NormalState() {
	}
	
	public static IDrawingState getInstance() {
		if (instance == null) {
			instance = new NormalState();
		}
		return instance;
	}

	@Override
	public void draw(IElement element) {
		element.renderNormal();
	}

}
