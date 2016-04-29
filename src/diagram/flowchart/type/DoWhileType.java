package diagram.flowchart.type;

public class DoWhileType extends LoopType {

	private static DoWhileType instance;

	public static DoWhileType get() {
		if (instance == null) {
			instance = new DoWhileType();
		}
		return instance;
	}
}