package cscie97.asn4.ecommerce.collection;

/**
 * The Class CollectionException.
 */
public class CollectionException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String identifier;
	private String errorMessage;

	
	/**
	 * Instantiates a new collection exception.
	 *
	 * @param identifier the identifier
	 * @param errorMessage the error message
	 */
	public CollectionException(String identifier, String errorMessage) {
		this.identifier = identifier;
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the identifier.
	 *
	 * @return the identifier
	 */
	public String getIdentifier() {
		return this.identifier;
	}

	public String getMessage() {
		return this.errorMessage;
	}
}
