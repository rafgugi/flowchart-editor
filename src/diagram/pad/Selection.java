package diagram.pad;

/**
 * Selection type (if-else). Block code for each "yes" and "no" is saved
 * in the block container.
 */
public class Selection extends BlockSingle {

	private BlockContainer yesChild;
	private BlockContainer noChild;

	/**
	 * Get "yes" child.
	 * 
	 * @return child
	 */
	public BlockContainer getYesChild() {
		return yesChild;
	}

	/**
	 * Set "yes" child.
	 * 
	 * @param child
	 */
	public void setYesChild(BlockContainer child) {
		yesChild = child;
	}

	/**
	 * Get "no" child.
	 * 
	 * @return child
	 */
	public BlockContainer getNoChild() {
		return noChild;
	}

	/**
	 * Set "no" child.
	 * 
	 * @param child
	 */
	public void setNoChild(BlockContainer child) {
		noChild = child;
	}

	@Override
	public String generate() {
		String ans = createTabIndent() + "if (" + getText() + ") {\n";
		if (getYesChild() != null) {
			ans += getYesChild().generate();
		}
		ans += createTabIndent() + "} else {\n";
		if (getNoChild() != null) {
			ans += getNoChild().generate();
		}
		ans += createTabIndent() + "}\n";
		return ans;
	}

	@Override
	public String toString() {
		String ans = super.toString();
		if (getYesChild() != null)
			ans += createTabIndent() + "  Child Yes:\n" + getYesChild();
		if (getNoChild() != null)
			ans += createTabIndent() + "  Child No:\n" + getNoChild();
		return ans;
	}

}
