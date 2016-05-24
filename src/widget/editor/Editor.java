package widget.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import diagram.Flowchart;
import exception.EmptySubEditorException;
import interfaces.IDiagram;
import interfaces.IEditor;
import interfaces.ISubEditor;
import interfaces.ITool;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import widget.window.MainWindow;

public class Editor extends TabFolder implements IEditor, SelectionListener {

	private List<ISubEditor> subEditors = new ArrayList<>();
	private ITool tool;

	public Editor(MainWindow parent) {
		super(parent, SWT.NONE);
		initialize();
	}

	@Override
	public void checkSubclass() {
	}

	protected void initialize() {
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.minimumHeight = 320;
		gridData.minimumWidth = 480;
		super.setLayoutData(gridData);

		ITool tool;
		try {
			tool = ((MainWindow) super.getParent()).getToolStrip().getTools().get(0);
			setActiveTool(tool);
		} catch (IndexOutOfBoundsException e) {
			MainWindow.getInstance().setStatus("Tool belum ada");
		}

		super.addSelectionListener(this);
	}

	@Override
	public void addSubEditor(ISubEditor subEditor) {
		subEditors.add(subEditor);
		this.setSelection((TabItem) subEditor);
	}

	@Override
	public void newSubEditor() {
		newSubEditor("SubEditor " + (subEditors.size() + 1));
	}

	@Override
	public void newSubEditor(String title) {
		newSubEditor(title, Flowchart.get());
	}

	@Override
	public void newSubEditor(String title, IDiagram diagram) {
		ISubEditor subEditor = new SubEditor(this);
		subEditor.setTitle(title);
		subEditor.setDiagram(diagram);
		addSubEditor(subEditor);
	}

	@Override
	public ISubEditor getActiveSubEditor() {
		int index = super.getSelectionIndex();
		if (index == -1) {
			throw new EmptySubEditorException();
		}
		ISubEditor ans = subEditors.get(index);
		if (ans.getDiagram() == null) {
			throw new EmptySubEditorException("Diagram type not specified");
		}
		return ans;
	}

	@Override
	public List<ISubEditor> getSubEditors() {
		return subEditors;
	}

	@Override
	public void close() {
		ISubEditor subEditor = getActiveSubEditor();
		if (subEditor == null) {
			throw new EmptySubEditorException();
		}
		subEditor.close();
		subEditors.remove(subEditor);
	}

	@Override
	public ITool getActiveTool() {
		return tool;
	}

	@Override
	public void setActiveTool(ITool tool) {
		this.tool = tool;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		MainWindow.getInstance().getValidationList().reset();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}

}
