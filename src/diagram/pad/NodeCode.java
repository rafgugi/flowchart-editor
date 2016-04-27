package diagram.pad;

public class NodeCode {

	public NodeCode parent;
	public int x = 0; // current x value
	public int y = 0; // current y value
	private int xStreak = 0; // child highest x value
	private int yStreak = 0; // child highest y value

	public NodeCode(NodeCode parent, int x, int y) {
		this.parent = parent;
		this.x = x;
		this.y = y;
	}

	public NodeCode(NodeCode parent) {
		this.parent = parent;
		if (parent != null) {
			this.x = parent.x;
			this.y = parent.y;
			parent.incYStreak();
		}
	}

	public NodeCode() {
		parent = null;
		xStreak = 0;
		yStreak = 0;
	}

	public int getXStreak() {
		return xStreak;
	}

	public void incXStreak() {
		xStreak++;
	}

	public int getYStreak() {
		return yStreak;
	}

	public void incYStreak() {
		yStreak++;
	}

	public NodeCode createSibling() {
		NodeCode sibling = new NodeCode(parent, x, y + 1);
		if (parent != null) {
			parent.incYStreak();
		}
		return sibling;
	}

	public NodeCode createChild() {
		NodeCode child = new NodeCode(this, getXStreak(), 0);
		incXStreak();
		return child;
	}

	@Override
	public String toString() {
		String retval = "";
		if (parent != null) {
			retval = parent.toString();
		}
		return retval + "[" + x + "," + y + "]";
	}

}
