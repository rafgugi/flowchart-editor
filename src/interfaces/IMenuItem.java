package interfaces;

public interface IMenuItem extends IWidget {
	public String getTitle();
	public void setTitle(String title);
	public void execute();
}
