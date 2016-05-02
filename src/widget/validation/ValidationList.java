package widget.validation;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import interfaces.IValidationItem;
import interfaces.IValidationList;

public class ValidationList extends List implements IValidationList, SelectionListener {

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

		super.addSelectionListener(this);
	}

	@Override
	public void addItem(IValidationItem item) {
		validationItems.add(item);
		super.add(item.getTitle());
	}

	@Override
	public void removeItem(IValidationItem item) {
		int index = validationItems.indexOf(item);
		super.remove(index);
		validationItems.remove(item);
	}
	
	@Override
	public int getIndex(IValidationItem item) {
		return validationItems.indexOf(item);
	}

	@Override
	public IValidationItem newItem() {
		IValidationItem item = new ValidationItem();
		return item;
	}

	@Override
	public ArrayList<IValidationItem> getValidationItems() {
		return validationItems;
	}

	@Override
	public void reset() {
		while (validationItems.size() != 0) {
			int index = validationItems.size() - 1;
			removeItem(validationItems.get(index));
		}
	}

	@Override
	public void checkSubclass() {
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		int i = super.getSelectionIndex();
		if (i == -1) {
			return;
		}
		IValidationItem item = validationItems.get(i);
		System.out.println(item.getTitle());
		item.action();
		// this.removeItem(item);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

}
