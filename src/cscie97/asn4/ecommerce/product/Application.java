package cscie97.asn4.ecommerce.product;

import java.util.Set;

/**
 * Application: A type of content, with size.
 * 
 * @author Lex Loren
 */
public class Application extends Content {

	/** The size. */
	private int size;
	
	/**
	 * Instantiates a new application.
	 *
	 * @param content_type 			this will generally be "application"
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
	 * @param size 					the size in bytes
	 * @throws ContentAddException 
	 */
	public Application(
			String content_type,
			String content_id,
			String content_name,
			String content_description,
			String author,
			int rating, 
			Set<String> categories,
			Set<Country> export_countries,
			Set<Device> supported_devices,
			float price,
			Set<String> supported_languages,
			String image_url,
			int size)
			throws ContentAddException
	{
		super(content_type, 
				content_id, 
				content_name, 
				content_description,
				author, 
				rating, 
				categories, 
				export_countries,
				supported_devices, 
				price, 
				supported_languages, 
				image_url);
		this.size = size;
	}
	
	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public float getSize() {
		return size;
	}
	
}
