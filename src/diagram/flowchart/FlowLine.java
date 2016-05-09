package diagram.flowchart;

import diagram.element.PolyLine;
import diagram.element.TwoDimensional;
import interfaces.IDiagramElement;
import interfaces.IElement;
import widget.window.property.FlowLineProperty;

public class FlowLine extends PolyLine implements IDiagramElement {

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
	
	@Override
	public void select() {
		super.select();
		for (IElement e : getConnectedElements()) {
			System.out.println(e.getClass().getName() + e.getId());
		}
	}

}
