package interfaces;

public interface IWindow extends IWidget {
	public void setTitle(String name);
	public String getTitle();
	public IMenuBar getBar();
	public void setBar(IMenuBar bar);
	IEditor getEditor();
	void setEditor(IEditor editor);
}
