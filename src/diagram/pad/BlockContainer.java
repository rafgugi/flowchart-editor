package diagram.pad;

import java.util.ArrayList;

import interfaces.PadElement;
import main.Main;

/**
 * Block container element that contains another single block of PAD
 * elements. This element define the layer of PAD. First layer has no
 * parent, others' parent are either loop or selection.
 */
public class BlockContainer implements PadElement {

	private ArrayList<BlockSingle> elements = new ArrayList<>();
	private BlockSingle parent = null;

	public BlockContainer(BlockSingle parent) {
		setParent(parent);
	}

	public BlockContainer() {
		this(null);
	}

	/**
	 * Get collection of single block elements.
	 * 
	 * @return elements
	 */
	public ArrayList<BlockSingle> getElements() {
		return elements;
	}

	/**
	 * Add a single block element.
	 * 
	 * @param element
	 */
	public void addElement(BlockSingle element) {
		element.setParent(this);
		elements.add(element);
	}

	/**
	 * Get parent.
	 * 
	 * @return parent
	 */
	public BlockSingle getParent() {
		return parent;
	}

	/**
	 * Set parent.
	 * 
	 * @param parent
	 */
	public void setParent(BlockSingle parent) {
		this.parent = parent;
	}

	@Override
	public String generate() {
		String ans = "";
		for (BlockSingle element : elements) {
			ans += element.generate();
		}
		return ans;
	}

	@Override
	public int getTabIndent() {
		if (parent != null) {
			return parent.getTabIndent() + 1;
		}
		return PadElement.INITIAL_INDENT;
	}
	
	protected String createTabIndent() {
		Main.log(getClass().getSimpleName() + " " + getTabIndent() + " tab(s).");
		return createTabIndent(getTabIndent());
	}
	
	protected String createTabIndent(int tabIndent) {
		String ans = "";
		for (int i = 0; i < tabIndent; i++) {
			ans += PadElement.TAB_CHAR;
		}
		return ans;
	}

	@Override
	public String toString() {
		String ans = createTabIndent(getTabIndent() - 1);
		ans += "  [" + this.getClass().getSimpleName() + "]\n";
		for (BlockSingle child : getElements()) {
			ans += child;
		}
		return ans;
	}
}
