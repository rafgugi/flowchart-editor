package widget.validation;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import interfaces.IValidationItem;
import interfaces.IValidationList;

public class ValidationList extends List implements IValidationList {
	
	private ArrayList<IValidationItem> validationItems = new ArrayList<>();

	public ValidationList(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	@Override
	public void initialize() {
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, false, false);
		gridData.widthHint = 180;
		super.setLayoutData(gridData);
		super.setLayoutData(gridData);
	}

	@Override
	public void addItem(IValidationItem item) {
		validationItems.add(item);
	}

	@Override
	public IValidationItem newItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<IValidationItem> getValidationItems() {
		return validationItems;
	}

	@Override
	public void reset() {
		while (validationItems.size() != 0) {
			validationItems.remove(validationItems.size() - 1);
		}
	}
	
	@Override
	public void checkSubclass() {
	}

}
