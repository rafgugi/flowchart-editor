package diagram.pad;

/**
 * Do-while type loop.
 */
public class DoWhile extends Loop {

	@Override
	public String generate() {
		String ans = createTabIndent() + "do {\n";
		ans += getChild().generate();
		ans += createTabIndent() + "} while (" + getText() + ");\n";
		return ans;
	}

}
