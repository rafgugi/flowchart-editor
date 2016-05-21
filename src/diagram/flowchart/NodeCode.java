package diagram.flowchart;

import java.util.ArrayList;

import interfaces.FlowChartElement;

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

	/* Node code family */
	private NodeCode parent = null;
	private NodeCode sibling = null;
	private ArrayList<NodeCode> children = new ArrayList<>();
	
	private int x = 0; // current x value
	private int y = 0; // current y value
	private int xStreak = 0; // child highest x value

	private FlowChartElement element;

	public NodeCode(NodeCode parent, int x, int y) {
		this.parent = parent;
		this.x = x;
		this.y = y;
	}

	public NodeCode() {
	}

	/**
	 * Create node code, the same parent of this, same x value, one higher y
	 * value of this.
	 * 
	 * @return Sibling node code
	 */
	public NodeCode createSibling() {
		NodeCode sibling = new NodeCode(parent, x, y + 1);
		this.sibling = sibling;
		return sibling;
	}

	/**
	 * Create node code, the parent is this node code, y value is 0, x value is
	 * this xStreak.
	 * 
	 * @return Child node code
	 */
	public NodeCode createChild() {
		NodeCode child = new NodeCode(this, xStreak, 0);
		children.add(child);
		xStreak++;
		return child;
	}

	/**
	 * Reset this node children, also reset xStreak.
	 */
	public void resetChildren() {
		xStreak = 0;
		children.clear();
	}

	/**
	 * Get sibling of this code
	 * 
	 * @return sibling
	 */
	public NodeCode getSibling() {
		return sibling;
	}

	/**
	 * Get collection of children of this code
	 * 
	 * @return children
	 */
	public ArrayList<NodeCode> getChildren() {
		return children;
	}

	/**
	 * Get corresponding element to this node.
	 * 
	 * @return element
	 */
	public FlowChartElement getElement() {
		return element;
	}

	/**
	 * Set corresponding element.
	 * 
	 * @param element
	 */
	public void setElement(FlowChartElement element) {
		this.element = element;
	}

	@Override
	public String toString() {
		String retval = "";
		if (parent != null) {
			retval = parent.toString();
		}
		return retval + "(" + x + "," + y + ")";
	}

}
