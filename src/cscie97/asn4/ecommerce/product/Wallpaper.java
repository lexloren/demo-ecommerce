package cscie97.asn4.ecommerce.product;

import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Class Wallpaper.
 */
public class Wallpaper extends Content {

	/**
	 * Instantiates a new wallpaper.
	 *
	 * @param content_type 			
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
	 * @throws ContentAddException the content add exception
	 */
	public Wallpaper(String content_type, String content_id,
			String content_name, String content_description, String author,
			int rating, Set<String> categories,
			Set<Country> export_countries,
			Set<Device> supported_devices, float price,
			Set<String> supported_languages, String image_url)
			throws ContentAddException {
		super(content_type, content_id, content_name, content_description,
				author, rating, categories, export_countries,
				supported_devices, price, supported_languages, image_url);
		// TODO Auto-generated constructor stub
	}

}
