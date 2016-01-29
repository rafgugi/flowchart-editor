package interfaces;

import java.util.List;

public interface IEditor extends IWidget{
	public void addSubEditor(ISubEditor subEditor);
	void newSubEditor();
	public ISubEditor getActiveSubEditor();
	public List<ISubEditor> getSubEditors();
}
