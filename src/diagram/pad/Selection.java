package diagram.pad;

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
		String ans = "if (" + getText() + ") {\n";
		if (getYesChild() != null) {
			ans += getYesChild().generate();
		}
		ans += "} else {\n";
		if (getNoChild() != null) {
			ans += getNoChild().generate();
		}
		ans += "}\n";
		return ans;
	}

}
