package cscie97.asn4.ecommerce.product;

import java.util.Set;

/**
 * Content: A particular product available in the store. Note that 
 * this is an abstract class, and should be subclassed. 
 * 
 * @author Lex Loren
 */
public abstract class Content {
	
	/** The name. */
	private String name;
	
	/** The description. */
	private String description;
	
	/** The author. */
	private String author;
	
	/** The rating. */
	private int rating;
	
	/** The categories. */
	private Set<String> categories;
	
	/** The country. */
	private Set<Country> country;
	
	/** The device. */
	private Set<Device> device;
	
	/** The price. */
	private float price;
	
	/** The language. */
	private Set<String> language;
	
	/** The image. */
	private String image;
	
	/** The type. */
	private String type;
	
	/** The contentid. */
	private String contentid;
	
	/**
	 * Abstract method to instantiate a new content object.
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
	public Content(
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
			String image_url)
			throws ContentAddException
	{		
		if ((rating < 0) || (rating > 5))
		{
			throw new ContentAddException("Invalid Rating");
		}
		else if (export_countries.size() < 1)
		{
			throw new ContentAddException("At least one (1) country must be specified.");
		}
		else if (supported_devices.size() < 1)
		{
			throw new ContentAddException("At least one (1) device must be specified.");
		}
		else if (supported_languages.size() < 1)
		{
			throw new ContentAddException("At least one (1) language must be specified.");
		}
		else if (price < 0)
		{
			throw new ContentAddException("Price cannot be negative.");
		}
		else 
		{
			this.type = content_type.trim();
			this.contentid = content_id.trim();
			this.name = content_name.trim();
			this.description = content_description.trim();
			this.author = author.trim();
			this.rating = rating;
			this.categories = categories;
			this.country = export_countries;
			this.device = supported_devices;
			this.price = price;
			this.language = supported_languages;
			this.image = image_url.trim();
		}
	}
	
	
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * Gets the rating.
	 *
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Gets the categories.
	 *
	 * @return the categories
	 */
	public Set<String> getCategories() {
		return categories;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public Set<Country> getCountry() {
		return country;
	}
	
	/**
	 * Gets the device.
	 *
	 * @return the device
	 */
	public Set<Device> getDevice() {
		return device;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}
	
	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public Set<String> getLanguage() {
		return language;
	}
	
	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Gets the contentid.
	 *
	 * @return the contentid
	 */
	public String getContentid() {
		return contentid;
	}
}
