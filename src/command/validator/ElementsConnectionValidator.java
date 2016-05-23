package command.validator;

import java.util.ArrayList;
import java.util.Stack;

import interfaces.IDiagramElement;
import interfaces.IElement;
import main.Main;
import widget.validation.ValidationItem;

/**
 * 1. Not connected elements
 */
public class ElementsConnectionValidator extends AValidator {

	public String getDescription() {
		return "Ada elemen yang tidak terkoneksi";
	}

	@Override
	public void execute() {
		if (getAllElements().size() == 0) {
			return;
		}

		ArrayList<IElement> traversed = new ArrayList<>();
		Stack<IElement> checkThis = new Stack<>();
		
		ArrayList<IElement> highest = null;
		int streak = -1;

		/* Get most connected graph */ 
		for (IElement element : getAllElements()) {
			int currStreak = 0;
			if (traversed.contains(element)) {
				continue;
			}
			checkThis.push(element);
			ArrayList<IElement> currGraph = new ArrayList<>();
			while (!checkThis.isEmpty()) {
				IElement popped = checkThis.pop();
				if (!traversed.contains(popped)) {
					currStreak++;
					currGraph.add(popped);
					Main.log("Add conn: " + popped);
					traversed.add(popped);
					for (IElement elem : popped.getConnectedElements()) {
						checkThis.push(elem);
					}
				}
			}
			if (currStreak > streak) {
				streak = currStreak;
				highest = currGraph;
			}
		}

		/* Kick all elements that not connected */
		for (IElement element : getAllElements()) {
			if (!highest.contains(element)) {
				ValidationItem item = new ValidationItem();
				item.addProblem(element);
				String nama = null;
				if (element instanceof IDiagramElement) {
					IDiagramElement d = (IDiagramElement) element;
					if (!d.getText().equals("")) {
						nama = d.getText();
					}
				}
				if (nama == null) {
					nama = element.getClass().getSimpleName();
				}
				item.setTitle(nama + " tidak terkoneksi");
				
				addValidationItem(item);
			}
		}
	}

}
