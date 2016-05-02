package interfaces;

public interface IMainWindow extends IWindow {
	public IMenuBar getBar();
	public IEditor getEditor();
	public IToolStrip getToolStrip();
	public IValidationList getValidationList();
}
