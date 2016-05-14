package diagram.rules;

import java.util.List;

import interfaces.IDiagramRule;
import interfaces.IElement;
import widget.window.MainWindow;

public abstract class ARule implements IDiagramRule {
	
	protected List<IElement> getAllElements() {
		return MainWindow.getInstance().getEditor().getActiveSubEditor().getElements();
	}
}
