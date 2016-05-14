package interfaces;

import java.util.ArrayList;

public interface IDiagramRule {

	public String getDescription();
	public ArrayList<IElement> validate();
}
