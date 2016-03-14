package exception;

public class InvalidFlowChartException extends RuntimeException {
	
	private static final long serialVersionUID = 3968084348957461104L;

	public InvalidFlowChartException(String message) {
		super(message);
	}

}