package diagram.flowchart.type;

import interfaces.IType;

public class SelectionType implements IType {

	private static SelectionType instance;

	public static SelectionType get() {
		if (instance == null) {
			instance = new SelectionType();
		}
		return instance;
	}
}