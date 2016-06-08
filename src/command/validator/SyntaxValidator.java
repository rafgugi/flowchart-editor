package command.validator;

import command.SourceCodeTreeCommand;
import diagram.flowchart.Process;
import exception.SyntaxErrorException;
import diagram.flowchart.Judgment;
import interfaces.IDiagramElement;
import interfaces.IElement;
import widget.validation.ValidationItem;

/**
 * 1. Syntax error.
 */
public class SyntaxValidator extends AValidator {

	public String getDescription() {
		return "Sintaks kode salah";
	}

	@Override
	public void execute() {
		for (IElement element : getAllElements()) {
			if (element instanceof Judgment || element instanceof Process) {
				IDiagramElement node = (IDiagramElement) element;
				if (node.getText().trim().equals("")) {
					ValidationItem item = new ValidationItem();
					item.addProblem(element);
					item.setTitle(node + " belum terisi");
					addValidationItem(item);
				} else {
					String orig = node.getText();
					String text = "void main() { " + orig + "; }";
					SourceCodeTreeCommand validator = new SourceCodeTreeCommand(text);
					try {
						validator.execute();
					} catch (SyntaxErrorException e) {
						ValidationItem item = new ValidationItem();
						item.addProblem(element);
						item.setTitle("Syntax error: " + orig);
						item.setDescription(e.getMessage());
						addValidationItem(item);
					}
				}
			}
		}
	}

}
