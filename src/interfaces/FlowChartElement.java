package interfaces;

import diagram.flowchart.NodeCode;

/**
 * This interface just help generating node code for every FlowChartElement.
 */
public interface FlowChartElement {

	/**
	 * Get the current node code.
	 * 
	 * @return Node code
	 */
	public NodeCode getNodeCode();

	/**
	 * Set the current node code.
	 * 
	 * @param Node
	 *            code
	 */
	public void setNodeCode(NodeCode code);

	/**
	 * Get the current flowchart type.
	 * 
	 * @return Flowchart type
	 */
	public IType getType();

	/**
	 * Set the current flowchart type.
	 * 
	 * @param type
	 */
	public void setType(IType type);

	/**
	 * Check whether the node has been tranversed.
	 * 
	 * @return boolean
	 */
	public boolean hasBeenTraversed();

	/**
	 * Reset traversed flag to false.
	 */
	public void resetTraversed();

	/**
	 * Set traversed flag to true.
	 */
	public void traverse();

	/**
	 * Prepare element before used in a set of algorithm.
	 */
	public void prepare();

	public int getDoWhileCounter();
	public void setDoWhileCounter(int counter);
	
	public int getRecodeDoWhileCounter();
	public void setRecodeDoWhileCounter(int counter);

	public FlowChartElement getDoWhileNode();
	public void setDoWhileNode(FlowChartElement node);

}
