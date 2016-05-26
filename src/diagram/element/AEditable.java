package diagram.element;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

import interfaces.IElement;

public abstract class AEditable extends AElement {

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

	/**
	 * Create edit points on the 8 points of element, then put edit points in
	 * private variable editPoints
	 */
	public abstract void createEditPoints();


	/**
	 * Triggered when the element is dragged by another element. Parameter e is
	 * usually an EditPoint. This element perform drag depends on which
	 * EditPoint is dragged.
	 * 
	 * @param x
	 * @param y
	 * @param element
	 */
	public void drag(int x, int y, IElement e) {
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
		editPoints.clear();
		for (int i = 0; i < points.size(); i++) {
			Point point = points.get(i);
			EditPoint ep = new EditPoint(this, point.x, point.y, i);
			addEditPoint(ep);
		}
	}

}
