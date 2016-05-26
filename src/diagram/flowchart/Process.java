package diagram.flowchart;

import diagram.element.Line;
import diagram.element.TwoDimensional;
import diagram.flowchart.type.ProcessType;
import exception.CreateElementException;
import interfaces.IDiagramElement;
import interfaces.IElement;
import main.Main;
import widget.window.property.ProcessProperty;

public class Process extends FlowChartDecorator implements IDiagramElement {

	private FlowLine flow;

	public Process() {
	}

	public Process(TwoDimensional component) {
		super(component);
	}

	@Override
	public void select() {
		super.select();
		Main.log("Select " + this);
		Main.log("Type: " + getType());
		Main.log("Node Code:" + getNodeCode());
		Main.log("DoWhile:" + getDoWhileNode());
		Main.log("Traversed:" + (isTraversed() ? "true" : "false"));
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
				if (this.flow != null) {
					throw new CreateElementException("Process can't have more than one children.");
				} else {
					this.flow = flow;
				}
			}
		}
	}

	@Override
	public void disconnect(IElement element) {
		if (element == flow) {
			flow = null;
		}
		super.disconnect(element);
	}

	/**
	 * Get flow that go from this element.
	 * 
	 * @return flow
	 */
	public FlowLine getFlow() {
		return flow;
	}
	
	@Override
	public void prepare() {
		super.prepare();
		setType(ProcessType.get());
	}

}
