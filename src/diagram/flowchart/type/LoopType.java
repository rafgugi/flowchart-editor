package diagram.flowchart.type;

import interfaces.IType;

public class LoopType implements IType {

	private static LoopType instance;

	public static LoopType get() {
		if (instance == null) {
			instance = new LoopType();
		}
		return instance;
	}
}