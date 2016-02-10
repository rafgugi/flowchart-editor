package interfaces;

public interface IMainWindow extends IWindow {
	public IMenuBar getBar();
	public void setBar(IMenuBar bar);
	public IEditor getEditor();
	public void setEditor(IEditor editor);
	public IToolStrip getToolStrip();
	public void setToolStrip(IToolStrip toolstrip);
}
