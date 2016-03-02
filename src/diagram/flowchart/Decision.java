package diagram.flowchart;

import org.eclipse.swt.graphics.Point;

import diagram.element.Diamond;
import interfaces.IDiagramElement;
import widget.window.property.ProcessProperty;

public class Decision extends Diamond implements IDiagramElement {

	public Decision(Point src, Point dst) {
		super(src, dst);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() {
		ProcessProperty prop = new ProcessProperty(this);
		prop.show();
	}

}
