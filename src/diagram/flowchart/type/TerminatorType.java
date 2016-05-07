package diagram.flowchart.type;

public class TerminatorType extends AType {

	private static TerminatorType instance;

	public static TerminatorType get() {
		if (instance == null) {
			instance = new TerminatorType();
		}
		return instance;
	}
}