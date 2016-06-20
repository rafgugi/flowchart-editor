package exception;

/**
 * Exception on null element when trying to access them. I don't know if this is
 * necessary, may be I have to delete this unnecessary exception.
 */
public class ElementNotFoundException extends FlowchartEditorException {

	private static final long serialVersionUID = -488402328467985015L;

	public ElementNotFoundException(String message) {
		super(message);
	}

	public ElementNotFoundException() {
		super("Element not found.");
	}

}
