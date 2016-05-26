package interfaces;

import diagram.flowchart.NodeCode;

/**
 * This interface just help generating node code for every FlowChartElement.
 */
public interface FlowChartElement extends IDiagramElement {

	/**
	 * Get the current node code.
	 * 
	 * @return Node code
	 */
	public NodeCode getNodeCode();

	/**
	 * Set the current node code.
	 * 
	 * @param Node code
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
	 * Check whether the node has been traversed.
	 * 
	 * @return boolean
	 */
	public boolean isTraversed();

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

	/**
	 * Get counter of do while that surround this element.
	 * 
	 * @return doWhileCounter
	 */
	public FlowChartElement getDoWhileNode();

	/**
	 * Set counter of do while that surround this element.
	 * 
	 * @param doWhileCounter
	 */
	public void setDoWhileNode(FlowChartElement doWhile);

}
