package cscie97.asn4.ecommerce.product;

/**
 * ContentAddException: For everything that can go wrong when you try 
 * to add content to the store.
 * 
 * @author Lex Loren
 */
public class ContentAddException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new exception.
	 *
	 * @param message the message
	 */
	public ContentAddException(String message)
	{
		super(message);
	}
}
