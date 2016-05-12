package diagram.pad;

public abstract class Loop extends BlockSingle {

	private BlockContainer child;

	public BlockContainer getChild() {
		return child;
	}

	public void setChild(BlockContainer child) {
		this.child = child;
	}

}
