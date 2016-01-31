package widget.tab;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TabFolder;
import interfaces.IEditor;
import interfaces.ISubEditor;
import interfaces.ITool;
import widget.window.MainWindow;

public class Editor extends TabFolder implements IEditor {

	private List<ISubEditor> subEditors;
	private ITool tool;

	public Editor(MainWindow parent, int style) {
		super(parent, style);
		subEditors = new ArrayList<>();
		initialize();
	}

	public Editor(MainWindow parent) {
		this(parent, SWT.NONE);
	}

	@Override
	public void checkSubclass() {
	}

	@Override
	public void initialize() {
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.minimumHeight = 320;
		gridData.minimumWidth = 480;
		super.setLayoutData(gridData);
		
		ITool tool;
		try {
			tool = ((MainWindow)super.getParent()).getToolStrip().getTools().get(0);
			setActiveTool(tool);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Tool belum ada");
		}
	}

	@Override
	public void addSubEditor(ISubEditor subEditor) {
		subEditors.add(subEditor);
	}

	@Override
	public void newSubEditor() {
		ISubEditor subEditor = new SubEditor(this);
		subEditor.setTitle("SubEditor " + (subEditors.size() + 1));
		addSubEditor(subEditor);
	}

	@Override
	public ISubEditor getActiveSubEditor() {
		int index = this.getSelectionIndex();
		if (index == -1) {
			throw new ArrayIndexOutOfBoundsException();
		}
		ISubEditor ans = subEditors.get(index);
		return ans;
	}

	@Override
	public List<ISubEditor> getSubEditors() {
		return subEditors;
	}

	@Override
	public void close() {
		try {
			ISubEditor subEditor = getActiveSubEditor();
			subEditor.close();
			subEditors.remove(subEditor);
		} catch (ArrayIndexOutOfBoundsException e) {
			MainWindow.getInstance().setStatus("Editor is empty");
		}
	}

	@Override
	public ITool getActiveTool() {
		return tool;
	}

	@Override
	public void setActiveTool(ITool tool) {
		this.tool = tool;
	}

}
