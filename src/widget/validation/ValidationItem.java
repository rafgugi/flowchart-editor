package widget.validation;

import java.util.ArrayList;

import interfaces.IElement;
import interfaces.ISubEditor;
import interfaces.IValidationItem;
import widget.window.MainWindow;

public class ValidationItem implements IValidationItem {
	
	private String title;
	private String description;
	private ArrayList<IElement> problems = new ArrayList<>();

	@Override
	public void action() {
		ISubEditor subEditor = MainWindow.getInstance().getEditor().getActiveSubEditor(); 
		subEditor.deselectAll();
		for (IElement e : problems) {
			e.select();
		}
		subEditor.draw();
		MainWindow.getInstance().setStatus(getTitle());
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String desc) {
		this.description = desc;
	}

	@Override
	public ArrayList<IElement> getProblems() {
		return problems;
	}

	@Override
	public void addProblem(IElement element) {
		problems.add(element);
	}

}
