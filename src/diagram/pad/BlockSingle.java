package diagram.pad;

import interfaces.PadElement;

/**
 * Single block of PAD element. This can be sequence (process), selection
 * (if-else), or loop (while or do-while). Text type is different depending
 * the type of the element.
 */
public abstract class BlockSingle implements PadElement {

	private String text = "";

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

}
