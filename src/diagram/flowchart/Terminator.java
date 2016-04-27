package diagram.flowchart;

import org.eclipse.swt.graphics.Point;

import diagram.element.Line;
import diagram.element.RoundedRectangle;
import diagram.pad.NodeCode;
import interfaces.IDiagramElement;
import interfaces.IElement;
import widget.window.property.TerminatorProperty;

public class Terminator extends RoundedRectangle implements IDiagramElement, FlowChartElement {

	private FlowLine flow;
	private NodeCode nodeCode;
	private String type;

	public static String START = "Start";
	public static String END = "End";

	public Terminator() {
	}

	public Terminator(Point src, Point dst) {
		super(src, dst);
		setText(START);
		flow = null;
	}

	@Override
	public void action() {
		new TerminatorProperty(this).show();
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

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

}
