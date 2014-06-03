package cscie97.asn4.ecommerce.product;

/**
 * Device.
 * 
 * @author Lex Loren
 */
public class Device {

	/** The deviceid. */
	private String deviceid;
	
	/** The devicename. */
	private String devicename;
	
	/** The manufacturer. */
	private String manufacturer;	
	
	/**
	 * Instantiates a new device. Note that this is a protected class. 
	 * Only the Store should use it.
	 *
	 * @param deviceid the deviceid
	 * @param name the name
	 * @param manufact the manufact

	 */
	protected Device(String deviceid, String name, String manufact)
	{
		this.deviceid = deviceid;
		this.devicename = name;
		this.manufacturer = manufact;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getID()
	{
		return this.deviceid;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return this.devicename;
	}
	
	/**
	 * Gets the maker.
	 *
	 * @return the maker
	 */
	public String getMaker()
	{
		return this.manufacturer;
	}
}
