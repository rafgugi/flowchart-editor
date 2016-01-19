package interfaces;

public interface IMenuItem extends IWidget {
	public void execute();
	public String getTitle();
	public void setTitle(String name);
}
