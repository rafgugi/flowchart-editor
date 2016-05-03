package interfaces;

/**
 * Tool in the toolbar to manage the elements.
 */
public interface ITool extends IWidget {

	/**
	 * Get the tool name.
	 *
	 * @return Tool name
	 */
	public String getToolName();

	/**
	 * Execute when the tool is clicked.
	 */
	public void execute();

	/**
	 * Set this tool as active.
	 */
	public void select();

	/**
	 * Set this tool as deactive.
	 */
	public void deselect();

}
