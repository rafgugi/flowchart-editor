package widget.validation;

import java.util.ArrayList;

import interfaces.IElement;
import interfaces.IValidationItem;

public class ValidationItem implements IValidationItem {
	
	private String title;
	private String description;
	private ArrayList<IElement> problems;

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
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
	public void addProblems(IElement element) {
		problems.add(element);
	}

}
