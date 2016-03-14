package diagram.pad;

public class NodeCode {
	
	public NodeCode parent;
	public int x;
	public int y;
	private int xStreak;
	private int yStreak;
	
	public NodeCode(NodeCode parent, int x, int y) {
		this.parent = parent;
		this.x = x;
		this.y = y;
	}
	
	public NodeCode(NodeCode parent) {
		this(parent, 0, parent.getYStreak());
		parent.incYStreak();
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
