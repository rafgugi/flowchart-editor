package widget.validation;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;

import interfaces.IValidationItem;
import interfaces.IValidationList;
import main.Main;

public class ValidationList extends Group implements IValidationList, SelectionListener {

	private ArrayList<IValidationItem> validationItems = new ArrayList<>();
	private List list;

	public ValidationList(Composite parent) {
		super(parent, SWT.SHADOW_ETCHED_IN);
		initialize();
	}

	protected void initialize() {
		super.setText("Validation List");

		GridData gridGroup = new GridData(SWT.FILL, SWT.FILL, false, false);
		gridGroup.widthHint = 190;
		super.setLayoutData(gridGroup);

		GridLayout groupLayout = new GridLayout();
		groupLayout.numColumns = 1;
		super.setLayout(groupLayout);

		list = new List(this, SWT.NONE);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		list.setLayoutData(gridData);
		list.addSelectionListener(this);
	}

	@Override
	public void addItem(IValidationItem item) {
		validationItems.add(item);
		list.add(item.getTitle());
	}

	@Override
	public void removeItem(IValidationItem item) {
		int index = validationItems.indexOf(item);
		list.remove(index);
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
		int i = list.getSelectionIndex();
		if (i == -1) {
			return;
		}
		IValidationItem item = validationItems.get(i);
		Main.log(item.getTitle());
		item.action();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

}
