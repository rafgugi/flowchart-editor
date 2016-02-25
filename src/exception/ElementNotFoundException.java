package exception;

public class ElementNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -488402328467985015L;
	
	public ElementNotFoundException(String message) {
		super(message);
	}

}
