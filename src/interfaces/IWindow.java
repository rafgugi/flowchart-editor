package interfaces;

public interface IWindow extends IWidget {
	public IMenuBar getBar();
	public void setBar(IMenuBar bar);
	public void setTitle(String name);
	public String getTitle();
}
