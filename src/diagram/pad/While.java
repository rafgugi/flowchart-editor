package diagram.pad;

/**
 * While type loop.
 */
public class While extends Loop {

	@Override
	public String generate() {
		String ans = createTabIndent() + "while (" + getText() + ") {\n";
		ans += getChild().generate();
		ans += createTabIndent() + "}\n";
		return ans;
	}

}
