package exception;

public class PersistenceException extends RuntimeException {

	private static final long serialVersionUID = -2319022911183284935L;
	
	public PersistenceException(String message) {
		super(message);
	}

}
