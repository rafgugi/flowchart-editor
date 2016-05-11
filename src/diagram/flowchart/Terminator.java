package diagram.flowchart;

import diagram.element.Line;
import diagram.element.TwoDimensional;
import diagram.flowchart.type.TerminatorType;
import exception.CreateElementException;
import interfaces.IDiagramElement;
import interfaces.IElement;
import main.Main;
import widget.window.property.TerminatorProperty;

public class Terminator extends FlowChartDecorator implements IDiagramElement {

	private FlowLine flow;

	public static String START = "Start";
	public static String END = "End";
	
	public Terminator() {
	}

	public Terminator(TwoDimensional component) {
		super(component);
		setText(START);
	}

	@Override
	public void select() {
		super.select();
		Main.log("Select " + this);
		Main.log("Type: " + getType());
		Main.log("Node Code:" + getNodeCode());
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
				if (this.flow != null) {
					throw new CreateElementException("Terminator can't have more than one children.");
				} else {
					this.flow = flow;
				}
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
	public void prepare() {
		super.prepare();
		setType(TerminatorType.get());
	}

}
