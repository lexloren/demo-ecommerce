package cscie97.asn4.ecommerce.test;

/**
 * The Class ImportException.
 */
public class ImportException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The input string. */
	private String inputString;
	
	/** The error message. */
	private String errorMessage;
	
	/**
	 * Instantiates a new import exception.
	 *
	 * @param inputString the input string
	 * @param errorMessage the error message
	 */
	public ImportException(String inputString, String errorMessage) {
		this.inputString = inputString;
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Gets the input string.
	 *
	 * @return the input
	 */
	public String getInputString() {
		return inputString;
	}

}
