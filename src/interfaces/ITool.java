package interfaces;

public interface ITool extends IWidget {
	public String getToolName();
	public void execute();
	public void select();
	public void deselect();
}
