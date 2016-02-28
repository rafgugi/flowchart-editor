package interfaces;

public interface IEditableElement extends IElement {
	public void createEditPoints();
	public void drag(int x1, int y1, int x2, int y2, IElement e);
}
