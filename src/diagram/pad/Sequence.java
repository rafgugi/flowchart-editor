package diagram.pad;

/**
 * Sequence element, contains statement code.
 */
public class Sequence extends BlockSingle {

	@Override
	public String generate() {
		String ending = ";";
		if (getText().charAt(getText().length() - 1) == ';')
			ending = "";
		return createTabIndent() + getText() + ending + "\n";
	}

}
