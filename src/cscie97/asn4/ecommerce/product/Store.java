package cscie97.asn4.ecommerce.product;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cscie97.asn4.ecommerce.authentication.AccessDeniedException;
import cscie97.asn4.ecommerce.authentication.AuthenticationService;
import cscie97.asn4.ecommerce.authentication.InvalidAccessTokenException;

/**
 * Store: The cold, dark heart of the Mobile Application Store and 
 * its API. The Store object itself is a singleton, and it keeps 
 * references to every country, device, and product alive in memory. 
 * 
 * @author Lex Loren. 
 */
public class Store {

	/** The country list. */
	private Set<Country> countryList;
	
	/** The device list. */
	private Set<Device> deviceList;
	
	/** The content list. */
	private Map<String, Content> contentList;
	
	/** The Constant singleton. */
	private static final Store singleton = new Store();
	
	/**
	 * Instantiates a new store.
	 */
	private Store()
	{
		countryList = new HashSet<Country>();
		deviceList = new HashSet<Device>();
		contentList = new HashMap<String, Content>();
	}
	
	/**
	 * Gets a reference to the store.
	 *
	 * @return the store singleton
	 */
	public static Store getStore()
	{
		return singleton;
	}
	
	/**
	 * Adds a country to the Store's roster. eventual authentication 
	 * support is presumed.
	 *
	 * @param code 	two-letter country code
	 * @param name 
	 * @param open 	export status is open?
	 * @param GUID 	GUID w/ appropriate permissions
	 * @return 		String reference needed to refer to this country 
	 * 				in adding a new product to the Store.
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 * @throws AuthenticationServiceException 
	 * @throws AuthenticationException 
	 */
	public String addCountry(String code, String name, boolean open, String GUID) throws InvalidAccessTokenException, AccessDeniedException 
	{
		AuthenticationService authservice = AuthenticationService.getAuthenticationService();
		authservice.checkToken(GUID, "create_country");
		Country country = new Country(code.trim(), name.trim(), open);
		countryList.add(country);
		return country.getCode();
	}
	
	/**
	 * Adds a device to the Store's roster. eventual authentication 
	 * support is presumed.
	 *
	 * @param deviceid 	the deviceid
	 * @param name 
	 * @param manufact 	manufacturer's name/ID
	 * @param GUID 		GUID w/ appropriate permissions
	 * @return 			String reference needed to refer to this country 
	 * 					in adding a new product to the Store.
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 * @throws AuthenticationException 
	 */
	public String addDevice(String deviceid, String name, String manufact, String GUID) throws InvalidAccessTokenException, AccessDeniedException
	{
		AuthenticationService authservice = AuthenticationService.getAuthenticationService();
		authservice.checkToken(GUID, "create_device");
		Device device = new Device(deviceid.trim(), name.trim(), manufact);
		deviceList.add(device);	
		return device.getID();
	}
	
	/**
	 * Adds the item.
	 *
	 * @param content_type 			please specify what type of item
	 * @param content_id 			should be unique, although not currently enforced
	 * @param content_name 			the content_name
	 * @param content_description 	as one big string
	 * @param author the author		
	 * @param rating the rating		between 0 and 5, enforced
	 * @param categories 			
	 * @param export_countries 		must already exist in country list
	 * @param supported_devices 	must already exist in device list
	 * @param price the price		with 0 being free; cannot be less than 0
	 * @param supported_languages 	
	 * @param image_url 			proper formation not currently enforced
	 * @param GUID 					GUID w/ appropriate permissions
	 * @return 						A reference to the new item
	 * @throws ContentAddException
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 * @throws AuthenticationException
	 */
	public Content addItem(
			String content_type, 
			String content_id, 
			String content_name,
			String content_description,
			String author,
			int rating,
			Set<String> categories,
			Set<String> export_countries,
			Set<String> supported_devices,
			float price,
			Set<String> supported_languages,
			String image_url,
			String GUID) 
			throws ContentAddException, InvalidAccessTokenException, AccessDeniedException
	{
		
		AuthenticationService authservice = AuthenticationService.getAuthenticationService();
		authservice.checkToken(GUID, "create_product");
		Content item = null;
		Set<Device> device_list = getDevices(supported_devices);
		Set<Country> country_list = getCountries(export_countries);
		if (content_type.equalsIgnoreCase("wallpaper") == true)
		{
				item = new Wallpaper(
						content_type,
						content_id,
						content_name,
						content_description,
						author,
						rating, 
						categories,
						country_list,
						device_list,
						price,
						supported_languages,
						image_url);
			
		}
		else if (content_type.equalsIgnoreCase("ringtone") == true)
		{
				item = new Ringtone(
						content_type,
						content_id,
						content_name,
						content_description,
						author,
						rating, 
						categories,
						country_list,
						device_list,
						price,
						supported_languages,
						image_url);
			 
		}
		else 
		{
			throw new ContentAddException("Item type could not be found.");
		}
		if (item != null)
		{
			contentList.put(content_id, item);
		}
		return item;
	}


	/**
	 * Adds the item.
	 *
	 * @param content_type 			please specify what type of item
	 * @param content_id 			should be unique, although not currently enforced
	 * @param content_name 			the content_name
	 * @param content_description 	as one big string
	 * @param author the author		
	 * @param rating the rating		between 0 and 5, enforced
	 * @param categories 			
	 * @param export_countries 		must already exist in country list
	 * @param supported_devices 	must already exist in device list
	 * @param price the price		with 0 being free; cannot be less than 0
	 * @param supported_languages 	must already exist in langague list
	 * @param image_url 			proper formation not currently enforced
	 * @param application_size 		the application size in bytes
	 * @param GUID 					GUID w/ appropriate permissions
	 * @return 						A reference to the new item
	 * @throws ContentAddException
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 * @throws AuthenticationException
	 */
	public Content addItem(
			String content_type, 
			String content_id, 
			String content_name,
			String content_description,
			String author,
			int rating,
			Set<String> categories,
			Set<String> export_countries,
			Set<String> supported_devices,
			float price,
			Set<String> supported_languages,
			String image_url,
			int application_size,
			String GUID)
			throws ContentAddException, InvalidAccessTokenException, AccessDeniedException
	{
		
		AuthenticationService authservice = AuthenticationService.getAuthenticationService();
		authservice.checkToken(GUID, "create_product");
		Content item = null;
		Set<Device> device_list = getDevices(supported_devices);
		Set<Country> country_list = getCountries(export_countries);
		if (content_type.equalsIgnoreCase("application") == true)
		{
				item = new Application(
						content_type,
						content_id,
						content_name,
						content_description,
						author,
						rating, 
						categories,
						country_list,
						device_list,
						price,
						supported_languages,
						image_url, 
						application_size);

		}
		else 
		{
			throw new ContentAddException("Item type could not be found.");
		}
		
		if (item != null)
		{
			contentList.put(content_id, item);
		}
		return item;
	}
	
	/**
	 * Gets a list of devices matching the codes passed in.
	 *
	 * @param device_codes 	codes to find devices to
	 * @return 				the devices
	 */
	private Set<Device> getDevices(Set<String> device_codes)
	{
		Set<Device> matchingdevices = new HashSet<Device>();
		for (String name : device_codes)
		{
			for (Device device : deviceList)
			{
				if (device.getID().equalsIgnoreCase(name) == true)
				{
					matchingdevices.add(device);
				}
			}
		}
		return matchingdevices;
	}
	
	/**
	 * Gets a list of countries matching the codes passed in.
	 *
	 * @param country_codes 	
	 * @return the countries
	 */
	private Set<Country> getCountries(Set<String> country_codes)
	{
		Set<Country> matchingcountries = new HashSet<Country>();
		for (String name : country_codes)
		{
			for (Country country : countryList)
			{
				if (country.getCode().equalsIgnoreCase(name) == true)
				{
					matchingcountries.add(country);
				}
			}
		}
		return matchingcountries;
	}

	/**
	 * Gets the content list.
	 *
	 * @return the contentList
	 */
	public Set<Content> getContentList() {
		 return new HashSet<Content>(contentList.values());
	}
	
	public Content getItem(String id) {
		return contentList.get(id);
	}
}