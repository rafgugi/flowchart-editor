package diagram.pad;

import java.util.ArrayList;

public class BlockContainer extends PadElement {

	private ArrayList<BlockSingle> elements = new ArrayList<>();
	private BlockSingle parent = null;

	public BlockContainer(BlockSingle parent) {
		this.parent = parent;
	}

	public BlockContainer() {
		this(null);
	}

	public ArrayList<BlockSingle> getElements() {
		return elements;
	}

	public void addElement(BlockSingle element) {
		elements.add(element);
	}

	public BlockSingle getParent() {
		return parent;
	}

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

}
