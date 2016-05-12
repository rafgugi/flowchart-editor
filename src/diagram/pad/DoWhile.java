package diagram.pad;

/**
 * Do-while type loop.
 */
public class DoWhile extends Loop {

	@Override
	public String generate() {
		String ans = "do {\n";
		ans += getChild().generate();
		ans += "} while (" + getText() + ");\n";
		return ans;
	}

}
