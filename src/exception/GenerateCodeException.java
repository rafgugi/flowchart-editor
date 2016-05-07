package exception;

/**
 * Exception on generate code from flowchart. There are some algorithm performed
 * to generate source code from flowchart.
 */
public class GenerateCodeException extends FlowchartEditorException {

	private static final long serialVersionUID = -2141716577646362147L;

	public GenerateCodeException(String message) {
		super(message);
	}

}
