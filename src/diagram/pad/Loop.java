package diagram.pad;

public abstract class Loop extends PadElement {

	private Block child;

	public Block getChild() {
		return child;
	}

	public void setChild(Block child) {
		this.child = child;
	}

}
