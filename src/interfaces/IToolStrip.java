package interfaces;

import java.util.List;

/**
 * Tool container.
 */
public interface IToolStrip extends IWidget {

	/**
	 * Get collection of tools.
	 *
	 * @return Collection of tools.
	 */
	public List<ITool> getTools();

	/**
	 * Add a tool.
	 *
	 * @param Tool.
	 */
	public void addTool(ITool tool);

	/**
	 * Remove all tools. Not implementated yet.
	 */
	public void resetTools();
}
