package diagram.state;

import interfaces.IDrawingState;
import interfaces.IElement;

public class EditState implements IDrawingState {

	private static IDrawingState instance;

	private EditState() {
	}

	public static IDrawingState getInstance() {
		if (instance == null) {
			instance = new EditState();
		}
		return instance;
	}

	@Override
	public void draw(IElement element) {
		element.renderEdit();
	}

}
