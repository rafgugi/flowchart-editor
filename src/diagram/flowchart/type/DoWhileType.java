package diagram.flowchart.type;

public class DoWhileType {

	private static DoWhileType instance;

	private DoWhileType() {
	}

	public static DoWhileType get() {
		if (instance == null) {
			instance = new DoWhileType();
		}
		return instance;
	}
}