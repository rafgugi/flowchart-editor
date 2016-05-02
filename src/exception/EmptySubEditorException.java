package exception;

public class EmptySubEditorException extends FlowchartEditorException {

	private static final long serialVersionUID = 139162432526801917L;

	public EmptySubEditorException(String message) {
		super(message);
	}
	
	public EmptySubEditorException() {
		this("SubEditor not found.");
	}

}
