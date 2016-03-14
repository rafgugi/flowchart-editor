package diagram.flowchart;

import org.eclipse.swt.graphics.Point;

import diagram.element.Line;
import diagram.element.Rectangle;
import diagram.pad.NodeCode;
import interfaces.IDiagramElement;
import interfaces.IElement;
import widget.window.property.ProcessProperty;

public class Process extends Rectangle implements IDiagramElement, FlowChartElement {
	
	private FlowLine flow;
	private NodeCode nodeCode;

	public Process(Point src, Point dst) {
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
				this.flow = flow;
			}
		}
	}
	
	@Override
	public void disconnect(IElement element) {
		super.disconnect(element);
		flow = null;
	}

	public FlowLine getFlow() {
		return flow;
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
