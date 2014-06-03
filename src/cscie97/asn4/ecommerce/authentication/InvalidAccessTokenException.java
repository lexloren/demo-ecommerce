package cscie97.asn4.ecommerce.authentication;

/**
 * The Class InvalidAccessTokenException.
 */
public class InvalidAccessTokenException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new invalid access token exception.
	 *
	 * @param message the message
	 */
	public InvalidAccessTokenException(String message) {
		super(message);
	}
}
