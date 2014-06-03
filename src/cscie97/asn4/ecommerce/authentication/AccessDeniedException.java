package cscie97.asn4.ecommerce.authentication;

/**
 * AccessDeniedException.
 */
public class AccessDeniedException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new access denied exception.
	 *
	 * @param message the error message
	 */
	public AccessDeniedException(String message) {
		super(message);
	}

}
