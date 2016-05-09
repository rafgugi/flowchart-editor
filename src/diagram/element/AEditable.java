package diagram.element;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

import interfaces.IEditableElement;
import interfaces.IElement;

public abstract class AEditable extends AElement implements IEditableElement {

	private ArrayList<EditPoint> editPoints;
	
	public AEditable() {
		editPoints = new ArrayList<>();
	}

	@Override
	public void select() {
		if (!isActive()) {
			super.select();
			createEditPoints();	
		}
	}

	@Override
	public void deselect() {
		if (!isActive()) {
			return;
		}
		// Main.log("Deselect " + this.toString());
		editPoints.clear();
		super.deselect();
	}
	
	@Override
	public IElement checkBoundary(int x, int y) {
		for (IElement e : editPoints) {
			IElement ans = e.checkBoundary(x, y);
			if (ans != null) {
				return ans;
			}
		}
		return null;
	}

	@Override
	public void renderEdit() {
		renderNormal();
		for (EditPoint ep : editPoints) {
			ep.draw();
		}
	}

	@Override
	public void drag(int x1, int y1, int x2, int y2, IElement e) {
		deselect();
		select();
	}

	/**
	 * Add edit point for this element.
	 * 
	 * @param editPoint
	 */
	public void addEditPoint(EditPoint editPoint) {
		editPoints.add(editPoint);
	}

	/**
	 * Create edit point based on this element type.
	 * 
	 * @param points
	 */
	public void createEditPoints(ArrayList<Point> points) {
		for (int i = 0; i < points.size(); i++) {
			Point point = points.get(i);
			EditPoint ep = new EditPoint(this, point.x, point.y, i);
			addEditPoint(ep);
		}
	}

}
