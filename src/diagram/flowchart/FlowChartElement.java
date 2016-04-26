package diagram.flowchart;

import diagram.pad.NodeCode;

/**
 * Ini interface biar flowchart bisa dijadikan PAD
 * 
 * @author sg
 *
 */
public interface FlowChartElement {
	public NodeCode getNodeCode();
	public void setNodeCode(NodeCode code);
	public String getType();
	public void setType(String type);
}
