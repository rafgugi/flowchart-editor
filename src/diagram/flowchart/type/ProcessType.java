package diagram.flowchart.type;

import interfaces.IType;

public class ProcessType implements IType {

	private static ProcessType instance;

	public static ProcessType get() {
		if (instance == null) {
			instance = new ProcessType();
		}
		return instance;
	}
}