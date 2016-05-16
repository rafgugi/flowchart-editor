package diagram;

import interfaces.IDiagram;

public class Flowchart implements IDiagram {

	private static IDiagram instance;

	private Flowchart() {
	}

	public static IDiagram get() {
		if (instance == null) {
			instance = new Flowchart();
		}
		return instance;
	}

}
