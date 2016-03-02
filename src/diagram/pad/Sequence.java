package diagram.pad;

public class Sequence extends PadElement {

	@Override
	public String generate() {
		return getText() + ";\n";
	}

}
