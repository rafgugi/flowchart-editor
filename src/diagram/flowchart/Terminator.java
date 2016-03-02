package diagram.flowchart;

import org.eclipse.swt.graphics.Point;

import diagram.element.RoundedRectangle;
import interfaces.IDiagramElement;
import widget.window.property.TerminatorProperty;

public class Terminator extends RoundedRectangle implements IDiagramElement {

	public Terminator(Point src, Point dst) {
		super(src, dst);
	}

	@Override
	public void action() {
		new TerminatorProperty(this).show();
	}
}
