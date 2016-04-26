package diagram.flowchart;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

import diagram.element.Diamond;
import diagram.element.Line;
import diagram.pad.NodeCode;
import interfaces.IDiagramElement;
import interfaces.IElement;
import widget.window.property.ProcessProperty;

public class Decision extends Diamond implements IDiagramElement, FlowChartElement {
	
	private ArrayList<FlowLine> flows = new ArrayList<>();
	private NodeCode nodeCode;

	public Decision() {
	}

	public Decision(Point src, Point dst) {
		super(src, dst);
	}

	@Override
	public void action() {
		ProcessProperty prop = new ProcessProperty(this);
		prop.show();
	}

	@Override
	public void connect(IElement element) {
		super.connect(element);
		if (element instanceof FlowLine) {
			FlowLine flow = (FlowLine) element;
			if (flow.checkConnected(this) == Line.CONNECTED_SRC) {
				flows.add(flow);
			}
		}
	}

	@Override
	public void disconnect(IElement element) {
		super.disconnect(element);
		flows.remove(element);
	}
	
	public ArrayList<FlowLine> getFlows() {
		return flows;
	}

	@Override
	public NodeCode getNodeCode() {
		return nodeCode;
	}

	@Override
	public void setNodeCode(NodeCode code) {
		this.nodeCode = code;
	}

}
