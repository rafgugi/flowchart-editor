package exception;

/**
 * Exception on persistence. Invalid file type, encoding or decoding error, etc.
 */
public class PersistenceException extends FlowchartEditorException {

	private static final long serialVersionUID = -2319022911183284935L;

	public PersistenceException(String message) {
		super(message);
	}

}
