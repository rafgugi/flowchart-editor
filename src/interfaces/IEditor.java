package interfaces;

import java.util.List;

public interface IEditor extends IWidget{
	public void addSubEditor(ISubEditor subEditor);
	public void newSubEditor();
	public ISubEditor getActiveSubEditor();
	public List<ISubEditor> getSubEditors();
	public void close();
	public ITool getActiveTool();
	public void setActiveTool(ITool tool);
}
