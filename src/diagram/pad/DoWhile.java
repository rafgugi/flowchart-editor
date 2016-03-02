package diagram.pad;

public class DoWhile extends Loop {

	@Override
	public String generate() {
		String ans = "do {\n";
		ans += getChild().generate();
		ans += "} while (" + getText() + ");\n";
		return ans;
	}

}
