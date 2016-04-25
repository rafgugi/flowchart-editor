package diagram.pad;

public abstract class PadElement {

	private String text;

	public PadElement() {
		text = "";
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public abstract String generate();

}