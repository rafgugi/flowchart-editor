package diagram.pad;

import interfaces.PadElement;
import main.Main;

/**
 * Single block of PAD element. This can be sequence (process), selection
 * (if-else), or loop (while or do-while). Text type is different depending
 * the type of the element.
 */
public abstract class BlockSingle implements PadElement {

	private String text = "";
	private BlockContainer parent = null;

	/**
	 * Get text.
	 * 
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set text.
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Get parent.
	 * 
	 * @return parent
	 */
	public BlockContainer getParent() {
		return parent;
	}

	/**
	 * Set parent.
	 * 
	 * @param parent
	 */
	public void setParent(BlockContainer parent) {
		this.parent = parent;
	}

	@Override
	public int getTabIndent() {
		return getParent().getTabIndent();
	}
	
	protected String createTabIndent() {
		String ans = "";
		Main.log(getTabIndent() + " tab(s).");
		for (int i = 0; i < getTabIndent(); i++) {
			ans += PadElement.TAB_CHAR;
		}
		return ans;
	}
}
