package diagram.flowchart.type;

public class ProcessType extends AType {

	private static ProcessType instance;

	public static ProcessType get() {
		if (instance == null) {
			instance = new ProcessType();
		}
		return instance;
	}
}