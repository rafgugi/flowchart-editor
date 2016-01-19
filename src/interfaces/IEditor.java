package interfaces;

import java.util.List;

public interface IEditor {
	public void addSubEditor(ISubEditor subEditor);
	public ISubEditor getActiveSubEditor();
	public List<ISubEditor> getSubEditors();
}
