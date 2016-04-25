package diagram.pad;

import java.util.ArrayList;

public class ElementContainer extends PadElement {

	private ArrayList<PadElement> elements;
	private PadElement parent;

	public ElementContainer(PadElement parent) {
		this.parent = parent;
		elements = new ArrayList<>();
	}

	public ElementContainer() {
		this(null);
	}

	public void addElement(PadElement element) {
		elements.add(element);
	}

	public PadElement getParent() {
		return parent;
	}

	public void setParent(PadElement parent) {
		this.parent = parent;
	}

	@Override
	public String generate() {
		String ans = "";
		for (PadElement element : elements) {
			ans += element.generate();
		}
		return ans;
	}

}