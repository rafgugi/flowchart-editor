package interfaces;

import java.util.List;

public interface IEditor extends IWidget {

	/**
	 * Create a new tab
	 *
	 * @param tab
	 */
	public void addSubEditor(ISubEditor subEditor);

	/**
	 * Create a default tab with default name.
	 */
	public void newSubEditor();

	/**
	 * Create a default tab.
	 *
	 * @param tab
	 *            name
	 */
	public void newSubEditor(String title);

	/**
	 * Get currently active tab
	 *
	 * @return ISubEditor active tab
	 */
	public ISubEditor getActiveSubEditor();

	/**
	 * Get all tabs.
	 *
	 * @return List<ISubEditor> tabs
	 */
	public List<ISubEditor> getSubEditors();

	/**
	 * Close and remove the active tab
	 */
	public void close();

	/**
	 * Get currently active tool.
	 *
	 * @return active tool
	 */
	public ITool getActiveTool();

	/**
	 * Set active tool.
	 *
	 * @param active
	 *            tool
	 */
	public void setActiveTool(ITool tool);

}
