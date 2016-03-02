package diagram.pad;

public class While extends Loop {

	@Override
	public String generate() {
		String ans = "while (" + getText() + ") {\n";
		ans += getChild().generate();
		ans += "}\n";
		return ans;
	}

}
