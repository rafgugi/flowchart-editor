package interfaces;

public interface IWindow extends IWidget {
	public String getTitle();
	public void setTitle(String name);
	public IMenuBar getBar();
	public void setBar(IMenuBar bar);
	public IEditor getEditor();
	public void setEditor(IEditor editor);
	public IToolStrip getToolStrip();
	public void setToolStrip(IToolStrip toolstrip);
	public void show();
}
