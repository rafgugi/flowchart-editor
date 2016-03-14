package diagram.flowchart;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

import diagram.element.Diamond;
import diagram.element.Line;
import interfaces.IDiagramElement;
import interfaces.IElement;
import widget.window.property.ProcessProperty;

public class Decision extends Diamond implements IDiagramElement {
	
	private ArrayList<FlowLine> flows;

	public Decision(Point src, Point dst) {
		super(src, dst);
		flows = new ArrayList<>();
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

}
