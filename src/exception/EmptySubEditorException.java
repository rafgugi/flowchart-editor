package exception;

/**
 * Exception on empty sub-editor when trying to get one. Made this because
 * sub-editor is used by a lot of function.
 */
public class EmptySubEditorException extends FlowchartEditorException {

	private static final long serialVersionUID = 139162432526801917L;

	public EmptySubEditorException(String message) {
		super(message);
	}

	public EmptySubEditorException() {
		this("SubEditor not found.");
	}

}
