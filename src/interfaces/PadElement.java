package interfaces;

public interface PadElement {

	public static final int INITIAL_INDENT = 1;
	public static final String TAB_CHAR = "\t";

	/**
	 * Generate raw block of code from this PAD element
	 *
	 * @return raw code
	 */
	public String generate();

	/**
	 * Get tab indent value
	 * 
	 * @return tab indent
	 */
	public int getTabIndent();

}
