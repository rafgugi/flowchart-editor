package diagram.element;

import org.json.JSONObject;

import interfaces.IElement;
import main.Main;

public class EditPoint extends AElement {

	protected int x;
	protected int y;
	protected AEditable element;
	public static final int LENGTH = 5;
	protected int code;

	/* Two dimensional */
	public static final int TOP_LEFT = 0;
	public static final int TOP_MIDDLE = 1;
	public static final int TOP_RIGHT = 2;
	public static final int MIDDLE_RIGHT = 3;
	public static final int BOTTOM_RIGHT = 4;
	public static final int BOTTOM_MIDDLE = 5;
	public static final int BOTTOM_LEFT = 6;
	public static final int MIDDLE_LEFT = 7;

	/* Line */
	public static final int BEGIN = 0;
	public static final int END = 1;

	public EditPoint(AEditable element, int x, int y, int code) {
		this.element = element;
		this.x = x;
		this.y = y;
		this.code = code;
	}

	/**
	 * Get parent element which this belongs to.
	 * 
	 * @return element
	 */
	public IElement getParent() {
		return element;
	}

	@Override
	public String toString() {
		return "EditPoint for (" + getParent().toString() + ")[" + code + "]";
	}

	/**
	 * Get code of this edit point. Code is constantly generated on create, based
	 * on parent element type, defining which edit point according to the parent
	 * element.
	 * 
	 * @return code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Set x value.
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Set y value.
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Set x and y.
	 * 
	 * @param x
	 * @param y
	 */
	public void setPoint(int x, int y) {
		setX(x);
		setY(y);
	}

	@Override
	public void select() {
		element.select();
	}

	@Override
	public void deselect() {
	}

	@Override
	public void renderNormal() {
		select();
		renderEdit();
	}

	@Override
	public void renderEdit() {
		getCanvas().setFgColor(0, 0, 0);
		getCanvas().setBgColor(255, 255, 255);
		int x, y;
		x = this.x - 2;
		y = this.y - 2;
		getCanvas().drawRectangle(x, y, LENGTH, LENGTH);
		getCanvas().fillRectangle(x + 1, y + 1, LENGTH - 1, LENGTH - 1);
	}

	@Override
	public IElement checkBoundary(int x, int y) {
		if (x < this.x - LENGTH / 2 || x > this.x + LENGTH / 2) {
			return null;
		}
		if (y < this.y - LENGTH / 2 || y > this.y + LENGTH / 2) {
			return null;
		}
		Main.log("Get editpoint [" + getCode() + "]");
		Main.log("\tof element " + element);
		return this;
	}

	@Override
	public IElement checkBoundary(int x1, int y1, int x2, int y2) {
		return null;
	}

	@Override
	public void drag(int x1, int y1, int x2, int y2) {
		Main.log("Drag by editpoint [" + getCode() + "]");
		element.drag(x1, y1, x2, y2, this);
	}

	@Override
	public void jsonDecode(JSONObject obj) {
		super.jsonDecode(obj);
	}

}
