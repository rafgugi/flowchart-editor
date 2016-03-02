package diagram.pad;

public abstract class Loop extends PadElement {

	private ElementContainer child;

	public ElementContainer getChild() {
		return child;
	}

	public void setChild(ElementContainer child) {
		this.child = child;
	}

}
