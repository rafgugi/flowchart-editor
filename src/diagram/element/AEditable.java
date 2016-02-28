package diagram.element;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

import interfaces.IEditableElement;
import interfaces.IElement;
import widget.window.MainWindow;

public abstract class AEditable extends AElement implements IEditableElement {

	@Override
	public void select() {
		super.select();
		createEditPoints();
	}

	@Override
	public void renderEdit() {
		renderNormal();
	}

	@Override
	public void drag(int x1, int y1, int x2, int y2, IElement e) {
		deselect();
		select();
	}

	public void createEditPoints(ArrayList<Point> points) {
		for (int i = 0; i < points.size(); i++) {
			Point point = points.get(i);
			EditPoint ep = new EditPoint(this, point.x, point.y, i);
			this.connect(ep);
			ep.connect(this);
			MainWindow.getInstance().getEditor().getActiveSubEditor().addElement(ep);
			ep.select();
		}
	}

}
