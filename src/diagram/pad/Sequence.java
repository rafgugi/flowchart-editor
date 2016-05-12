package diagram.pad;

public class Sequence extends BlockSingle {

	@Override
	public String generate() {
		return getText() + ";\n";
	}

}
