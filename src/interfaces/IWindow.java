package interfaces;

/**
 * Window or shell.
 */
public interface IWindow {

	/**
	 * Get window title.
	 *
	 * @return Window title.
	 */
	public String getTitle();

	/**
	 * Set window title.
	 *
	 * @param Window title.
	 */
	public void setTitle(String name);

	/**
	 * Show the window.
	 */
	public void show();
}
