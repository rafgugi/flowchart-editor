package interfaces;

import diagram.pad.NodeCode;

/**
 * Ini interface biar flowchart bisa dijadikan PAD
 */
public interface FlowChartElement {
	public NodeCode getNodeCode();
	public void setNodeCode(NodeCode code);
	public IType getType();
	public void setType(IType type);
}
