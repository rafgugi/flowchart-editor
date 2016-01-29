package widget.toolbar;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.ToolBar;

import interfaces.ITool;
import interfaces.IToolStrip;
import widget.Window;

public class ToolStrip extends ToolBar implements IToolStrip {

	public ToolStrip(Window parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	private List<ITool> tools;
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
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
