package diagram.rules;

import java.util.ArrayList;

import diagram.flowchart.Terminator;
import interfaces.IElement;

public class IncompleteTerminatorRule extends ARule {

	@Override
	public String getDescription() {
		return "Terminator tidak lengkap";
	}

	@Override
	public ArrayList<IElement> validate() {
		IElement start = null;
		IElement end = null;
		ArrayList<IElement> overTerminator = new ArrayList<>();
		for (IElement elem : getAllElements()) {
			if (elem instanceof Terminator) {
				Terminator ele = (Terminator) elem;
				if (ele.getText().equals(Terminator.START)) {
					if (start == null) {
						start = ele;
					} else {
						overTerminator.add(elem);
					}
				} else if (ele.getText().equals(Terminator.END)) {
					if (end == null) {
						end = ele;
					} else {
						overTerminator.add(elem);
					}
				}
			}
		}

		if (start == null || end == null) {
			return new ArrayList<IElement>();
		}
		return null;
	}

}
