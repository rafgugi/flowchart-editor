package widget.toolbar.tools;

import org.eclipse.swt.events.MouseEvent;

import widget.toolbar.ToolStrip;
import widget.window.MainWindow;

public class EllipseTool extends ATool {

	public EllipseTool(ToolStrip parent) {
		super(parent);
	}
	
	@Override
	public void initialize() {
		setIconName("terminator.png");
		super.initialize();
	}

	@Override
	public void execute() {
		MainWindow.getInstance().setStatus("Ellipse tool clicked");
	}

	@Override
	public void mouseUp(MouseEvent e) {
		System.out.println(e.toString());
	}

}
