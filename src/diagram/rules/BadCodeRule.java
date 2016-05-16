package diagram.rules;

import java.util.ArrayList;

import command.SourceCodeTreeCommand;
import diagram.flowchart.Process;
import diagram.flowchart.Judgment;
import interfaces.IDiagramElement;
import interfaces.IElement;

public class BadCodeRule extends ARule {

	@Override
	public String getDescription() {
		return "Sintaks kode salah";
	}

	@Override
	public ArrayList<IElement> validate() {
		for (IElement element : getAllElements()) {
			if (element instanceof Judgment || element instanceof Process) {
				IDiagramElement node = (IDiagramElement) element;
				String text = node.getText() + ";";
				SourceCodeTreeCommand validator = new SourceCodeTreeCommand(text);
				validator.execute();
			}
		}
		return null;
	}

}
