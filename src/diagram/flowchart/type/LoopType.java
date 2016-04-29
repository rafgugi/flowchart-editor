package diagram.flowchart.type;

public class LoopType {

	private static LoopType instance;

	private LoopType() {
	}

	public static LoopType get() {
		if (instance == null) {
			instance = new LoopType();
		}
		return instance;
	}
}