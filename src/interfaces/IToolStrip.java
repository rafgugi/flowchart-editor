package interfaces;

import java.util.List;

public interface IToolStrip extends IWidget {
	public List<ITool> getTools();
	public void addTool(ITool tool);
	public void resetTools();
}
