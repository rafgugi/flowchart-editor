package diagram.pad;

/**
 * Sequence element, contains statement code.
 */
public class Sequence extends BlockSingle {

	@Override
	public String generate() {
		return createTabIndent() + getText() + ";\n";
	}

}
