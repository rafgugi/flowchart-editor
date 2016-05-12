package diagram.flowchart;

/**
 * Code of flowchart. The basic unit of coding for every node is a number in the
 * form of [x,y]. x is the coding number of branches judgment node. y is a
 * sequence code which increase one by one in the same layer. The node code of a
 * new layer inherits from the code of its direct judgment node and append code
 * at its tail.
 * 
 * JOURNAL OF SOFTWARE, VOL. 7, NO. 5, MAY 2012 1111 © 2012 ACADEMY PUBLISHER
 */
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

	/**
	 * Each node code has own child x streak. Each time this node code make
	 * another child with increasing x, xStreak is increased.
	 * 
	 * @return xStreak
	 */
	public int getXStreak() {
		return xStreak;
	}

	/**
	 * Increase xStreak.
	 */
	public void incXStreak() {
		xStreak++;
	}

	/**
	 * Reset xStreak.
	 */
	public void resetXStreak() {
		xStreak = 0;
	}

	/**
	 * Each node code has own child y streak. Each time this node code make
	 * another child with increasing y, yxStreak is increased.
	 * 
	 * @return yStreak
	 */
	public int getYStreak() {
		return yStreak;
	}

	/**
	 * Increase yStreak.
	 */
	public void incYStreak() {
		yStreak++;
	}

	/**
	 * Reset yStreak.
	 */
	public void resetYStreak() {
		yStreak = 0;
	}

	/**
	 * Create node code, the same parent of this, same x value, one higher y
	 * value of this.
	 * 
	 * @return Sibling node code
	 */
	public NodeCode createSibling() {
		NodeCode sibling = new NodeCode(parent, x, y + 1);
		if (parent != null) {
			parent.incYStreak();
		}
		return sibling;
	}

	/**
	 * Create node code, the parent is this node code, y value is 0, x value is
	 * this xStreak.
	 * 
	 * @return Child node code
	 */
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
