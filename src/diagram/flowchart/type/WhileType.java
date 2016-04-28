package diagram.flowchart.type;

public class WhileType {

	private static WhileType instance;

	private WhileType() {
	}

	public static WhileType get() {
		if (instance == null) {
			instance = new WhileType();
		}
		return instance;
	}
}