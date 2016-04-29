package diagram.flowchart.type;

public class WhileType extends LoopType {

	private static WhileType instance;

	public static WhileType get() {
		if (instance == null) {
			instance = new WhileType();
		}
		return instance;
	}
}