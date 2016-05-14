package diagram.rules;

import java.util.ArrayList;
import java.util.Stack;

import interfaces.IElement;
import main.Main;

public class NotConnectedElementRule extends ARule {

	@Override
	public String getDescription() {
		return "Ada elemen yang tidak terkoneksi";
	}

	@Override
	public ArrayList<IElement> validate() {
		if (getAllElements().size() == 0) {
			return null;
		}
		IElement firstElement = getAllElements().get(0);

		ArrayList<IElement> connected = new ArrayList<>();

		Stack<IElement> checkThis = new Stack<>();
		checkThis.push(firstElement);

		while (!checkThis.isEmpty()) {
			IElement popped = checkThis.pop();
			if (!connected.contains(popped)) {
				Main.log("Add conn: " + popped);
				connected.add(popped);
				for (IElement elem : popped.getConnectedElements()) {
					checkThis.push(elem);
				}
			}
		}

		if (getAllElements().size() != connected.size()) {
			if (connected.size() < getAllElements().size() / 2) {
				return connected;
			} else {
				ArrayList<IElement> invert = new ArrayList<>();
				for (IElement elem : getAllElements()) {
					if (!connected.contains(elem)) {
						invert.add(elem);
					}
				}
				return invert;
			}
		}
		return null;
	}

}
