package widget.toolbar.tools;

import java.util.ArrayList;
import java.util.Iterator;

import interfaces.ITool;

public class ToolList extends ArrayList<ITool> {

	private static final long serialVersionUID = 7558843545963613480L;
	
	private static ToolList instance;
	
	private ToolList() {
	}
	
	public static ToolList getInstance() {
		return instance;
	}
	
	public static ITool getTool(String toolName) {
		Iterator<ITool> iter = instance.iterator();
		while (iter.hasNext()) {
			ITool tool = (ITool) iter.next();
			if (tool.getToolName() == toolName) {
				return tool;
			}
		}
		return null;
	}

}
