package diagram.pad;

public class NodeCode {

	public NodeCode parent;
	public int x; // current x value
	public int y; // current y value
	private int xStreak; // child highest x value
	private int yStreak; // child highest y value

	public NodeCode(NodeCode parent, int x, int y) {
		this.parent = parent;
		this.x = x;
		this.y = y;
	}

	public NodeCode(NodeCode parent) {
		this(parent, 0, parent.getYStreak());
		incYStreak();
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

}
