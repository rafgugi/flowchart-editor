package diagram.pad;

import exception.GenerateCodeException;

public class Selection extends BlockSingle {

	private BlockContainer yesChild;
	private BlockContainer noChild;

	public BlockContainer getYesChild() {
		return yesChild;
	}

	public void setYesChild(BlockContainer child) {
		yesChild = child;
	}

	public BlockContainer getNoChild() {
		return noChild;
	}

	public void setNoChild(BlockContainer child) {
		noChild = child;
	}

	public String generate() {
		throw new GenerateCodeException("Not implemented yet.");
	}

}
