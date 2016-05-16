package diagram;

import interfaces.IDiagram;

public class PAD implements IDiagram {

	private static IDiagram instance;

	private PAD() {
	}

	public static IDiagram get() {
		if (instance == null) {
			instance = new PAD();
		}
		return instance;
	}

}
