package diagram.flowchart.type;

public class ConvergenceType extends AType {

	private static ConvergenceType instance;

	public static ConvergenceType get() {
		if (instance == null) {
			instance = new ConvergenceType();
		}
		return instance;
	}
}