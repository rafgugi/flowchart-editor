package widget.toolbar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ToolBar;

import interfaces.ITool;
import interfaces.IToolStrip;
import widget.Window;
import widget.toolbar.tools.Ellipse;

public class ToolStrip extends ToolBar implements IToolStrip {

	private List<ITool> tools;

	public ToolStrip(Window parent, int style) {
		super(parent, style);
		tools = new ArrayList<>();
		initialize();
	}
	
	public ToolStrip(Window parent) {
		this(parent, SWT.BORDER | SWT.VERTICAL);
	}
	
	@Override
	public void initialize() {
		GridData gridData = new GridData();
		gridData.verticalAlignment = SWT.FILL;
		super.setLayoutData(gridData);
		super.pack();
		for (int i = 0; i < 10; i++) {
			addTool(new Ellipse(this));
		}
	}

	@Override
	public List<ITool> getTools() {
		return tools;
	}

	@Override
	public void setTools(List<ITool> tools) {
		this.tools = tools;
	}

	@Override
	public void addTool(ITool tool) {
		tools.add(tool);
		// TODO Auto-generated method stub
	}

	@Override
	public void resetTools() {
		Iterator<ITool> iterator = tools.iterator();
		while (iterator.hasNext()) {
			ITool tool = (ITool) iterator.next();
			tools.remove(tool);
			// TODO Auto-generated method stub
		}
	}

	@Override
	public void checkSubclass() {
	}
	
}
