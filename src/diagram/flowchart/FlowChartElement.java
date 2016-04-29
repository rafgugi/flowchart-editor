package diagram.flowchart;

import diagram.pad.NodeCode;
import interfaces.IType;

/**
 * Ini interface biar flowchart bisa dijadikan PAD
 * 
 * @author sg
 *
 */
public interface FlowChartElement {
	public NodeCode getNodeCode();
	public void setNodeCode(NodeCode code);
	public IType getType();
	public void setType(IType type);
}
