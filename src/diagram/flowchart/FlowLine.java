package diagram.flowchart;

import diagram.element.PolyLine;
import diagram.element.TwoDimensional;
import interfaces.IDiagramElement;
import interfaces.IElement;
import main.Main;
import widget.window.property.FlowLineProperty;

public class FlowLine extends PolyLine implements IDiagramElement {

	public static final String NONE = "";
	public static final String YES = "Y";
	public static final String NO = "N";

	public FlowLine() {
	}

	public FlowLine(TwoDimensional src, TwoDimensional dst) {
		super(src, dst);
	}

	public int getCode() {
		if (getText().equals(YES)) {
			return 1;
		}
		if (getText().equals(NO)) {
			return 2;
		}
		return 0;
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
			Main.log(e.getClass().getName() + e.getId());
		}
	}

}
