package widget.toolbar.tools;

import org.eclipse.swt.events.MouseEvent;

import widget.toolbar.ToolStrip;
import widget.window.MainWindow;

public class PointerTool extends ATool {

	public PointerTool(ToolStrip parent) {
		super(parent);
	}
	
	@Override
	public void initialize() {
		setIconName("pointer.png");
		super.initialize();
	}

	@Override
	public void execute() {
		MainWindow.getInstance().setStatus("Pointer tool clicked");
	}

	@Override
	public void mouseUp(MouseEvent e) {
		System.out.println("Pointer clicked at (" + e.x + ", " + e.y + ")");
	}

}
