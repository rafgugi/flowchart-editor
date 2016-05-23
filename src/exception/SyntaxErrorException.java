package exception;

public class SyntaxErrorException extends FlowchartEditorException {

	private static final long serialVersionUID = 7441638559184681685L;
	private int line;
	private int charPosition;

	public SyntaxErrorException(String message, int line, int charPosition) {
		super(message);
		this.line = line;
		this.charPosition = charPosition;
	}

	public SyntaxErrorException(String message) {
		this(message, -1, -1);
	}

	public int getLine() {
		return line;
	}

	public int getCharPosition() {
		return charPosition;
	}

}
