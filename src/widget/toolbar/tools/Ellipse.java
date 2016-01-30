package widget.toolbar.tools;

import widget.toolbar.ToolStrip;

public class Ellipse extends ATool {

	public Ellipse(ToolStrip parent) {
		super(parent);
	}
	
	@Override
	public void initialize() {
		setIconName("ellipse.png");
		super.initialize();
	}

	@Override
	public void execute() {
		System.out.println("Ellipse tool clicked");
	}

}
