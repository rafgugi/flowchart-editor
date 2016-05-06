package diagram.flowchart.type;

public class SelectionType extends AType {

	private static SelectionType instance;

	public static SelectionType get() {
		if (instance == null) {
			instance = new SelectionType();
		}
		return instance;
	}
}