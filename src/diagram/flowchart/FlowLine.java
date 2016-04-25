package diagram.flowchart;

import diagram.element.Line;
import diagram.element.TwoDimensional;
import interfaces.IDiagramElement;
import widget.window.property.FlowLineProperty;

public class FlowLine extends Line implements IDiagramElement {

	public FlowLine() {
	}

	public FlowLine(TwoDimensional src, TwoDimensional dst) {
		super(src, dst);
	}

	@Override
	public void action() {
		FlowLineProperty prop = new FlowLineProperty(this);
		prop.show();
	}

}
