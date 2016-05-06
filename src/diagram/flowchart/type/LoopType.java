package diagram.flowchart.type;

public class LoopType extends AType {

	private static LoopType instance;

	public static LoopType get() {
		if (instance == null) {
			instance = new LoopType();
		}
		return instance;
	}
}