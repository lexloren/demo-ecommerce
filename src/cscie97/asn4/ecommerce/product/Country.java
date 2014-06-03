package cscie97.asn4.ecommerce.product;

/**
 * Country
 * 
 * @author Lex Loren
 */
public class Country {

	/** The countrycode. */
	private String countrycode;
	
	/** The countryname. */
	private String countryname;
	
	/** The exportsopen. */
	private boolean exportsopen;
	
	/**
	 * Instantiates a new country. Note that this is a protected class. 
	 * Only the Store should use it.
	 *
	 * @param code 	two-letter country code
	 * @param name 	country name
	 * @param open 	exports open?
	 */
	protected Country(String code, String name, boolean open)
	{
		this.countrycode = code;
		this.countryname = name;
		this.exportsopen = open;
	}
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode()
	{
		return this.countrycode;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return this.countryname;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public boolean getStatus()
	{
		return this.exportsopen;
	}
}
