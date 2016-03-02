package diagram.flowchart;

import org.eclipse.swt.graphics.Point;

import diagram.element.Rectangle;
import interfaces.IDiagramElement;
import widget.window.property.ProcessProperty;

public class Process extends Rectangle implements IDiagramElement {

	public Process(Point src, Point dst) {
		super(src, dst);
	}

	@Override
	public void action() {
		ProcessProperty prop = new ProcessProperty(this);
		prop.show();
	}

}
