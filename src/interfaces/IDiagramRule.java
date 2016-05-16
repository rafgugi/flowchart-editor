package interfaces;

import java.util.ArrayList;

public interface IDiagramRule {

	/**
	 * Get description on validation error
	 *
	 * @return description
	 */
	public String getDescription();

	/**
	 * Get list of error diagram element
	 *
	 * @return collection of element
	 */
	public ArrayList<IElement> validate();

}
