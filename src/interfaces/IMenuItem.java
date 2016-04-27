package interfaces;

/**
 * MenuItem is single item of Menu. It will execute command
 * when clicked.
 */
public interface IMenuItem extends IWidget {

	/**
	 * Get the menu title (or name).
	 *
	 * @return Menu title
	 */
	public String getTitle();

	/**
	 * Executed when clicked.
	 */
	public void execute();
}
