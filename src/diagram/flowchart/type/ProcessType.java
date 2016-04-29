package diagram.flowchart.type;

public class ProcessType {

	private static SelectionType instance;

	private ProcessType() {
	}

	public static SelectionType get() {
		if (instance == null) {
			instance = new ProcessType();
		}
		return instance;
	}
}