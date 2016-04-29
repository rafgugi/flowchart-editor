package diagram.flowchart.type;

public class ProcessType {

	private static ProcessType instance;

	private ProcessType() {
	}

	public static ProcessType get() {
		if (instance == null) {
			instance = new ProcessType();
		}
		return instance;
	}
}