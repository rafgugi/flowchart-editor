package interfaces;

public interface IWindow extends IWidget {
	public void setTitle(String name);
	public String getTitle();
	public IMenuBar getBar();
	public void setBar(IMenuBar bar);
	public IEditor getEditor();
	public void setEditor(IEditor editor);
	public void show();
	public void setToolStrip(IToolStrip toolstrip);
	public IToolStrip getToolStrip();
}
