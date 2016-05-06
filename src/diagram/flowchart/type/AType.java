package diagram.flowchart.type;

import interfaces.IType;

public abstract class AType implements IType {

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
