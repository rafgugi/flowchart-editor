package diagram.flowchart.type;

public class SelectionType {

	private static SelectionType instance;

	private SelectionType() {
	}

	public static SelectionType get() {
		if (instance == null) {
			instance = new SelectionType();
		}
		return instance;
	}
}