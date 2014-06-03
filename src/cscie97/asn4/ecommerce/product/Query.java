package cscie97.asn4.ecommerce.product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Query: To Query the Store, users (or their representatives) 
 * instatiate a Query object and then add apropriate parameters to 
 * it. For convenience, all methods adding parameters to a Query 
 * object return a reference to that object, allowing calls to be 
 * chained together.
 * 
 * The csvStringQuery() method, which translates a single String into 
 * a Query object, is also provided.
 */
public class Query {

	/** The categories. */
	private Set<String> categories; 
	
	/** The text search. */
	private String textSearch;
	
	/** The max price. */
	private float maxPrice;
	
	/** The min rating. */
	private int minRating;
	
	/** The languages. */
	private Set<String> languages;
	
	/** The country. */
	private Set<String> country;
	
	/** The device. */
	private Set<String> device;
	
	/** The type. */
	private Set<String> type;
	
	/**
	 * Instantiates a new query.
	 */
	public Query() 
	{
		this.categories = new HashSet<String>();
		this.maxPrice = -1;
		this.minRating = -1;
		this.languages = new HashSet<String>();
		this.country = new HashSet<String>();
		this.device = new HashSet<String>();
		this.type = new HashSet<String>();
	}

	/**
	 * Search products.
	 *
	 * @param store 
	 * @return array list of matching items
	 */
	public ArrayList<Content> searchProducts(Store store) 
	 { 
		Query query = this; 
		ArrayList<Content> resultList = new ArrayList<>();
		     
		 for (Content item: store.getContentList()) 
		 {
			 boolean addItem = true;
			     
			 // check categories
			 if (query.getCategories().size() > 0) 
			 {
				 Set<String> cats = new HashSet<String>(query.getCategories());
				 cats.retainAll(item.getCategories());
				 addItem = (query.getCategories().size() > 0);
			 }
			 // check text search
			 if (addItem == true) 
			 {
				 String text = query.getTextSearch().trim();
				 if (text != null && !text.isEmpty());
			     { 
			    	 addItem = (item.getName().toLowerCase().contains(text.toLowerCase()) ||  
			    			 	(item.getDescription().toLowerCase().contains(text.toLowerCase())));
			     }
			 }     
			 // check minimum rating...
			 if (addItem == true) 
			 {
				 if ((query.getMinRating() >= 0) && (query.getMinRating() <= 5))
				 {
					 addItem = (item.getRating() >= query.getMinRating());
				 }
			 }
			 // check max price...
			 if (addItem == true) 
			 {
				 if (query.getMaxPrice() >= 0)
				 {
					 addItem = (item.getPrice() <= query.getMaxPrice());
				 }
			 }    
			 // check language list
			 if (addItem == true) 
			 {
				 if (query.getLanguages().size() > 0) 
				 {
					 Set<String> langs = new HashSet<String>(query.getLanguages());
					 langs.retainAll(item.getLanguage());
					 addItem = (query.getLanguages().size() > 0);
				 } 
			 }    
			 // check country code
			 if (addItem == true) 
			 {
				 if (query.getCountry().size() > 0) 
				 {
					 Set<String> countries = new HashSet<String>(query.getCountry());
					 countries.retainAll(item.getCountry());
					 addItem = (query.getCountry().size() > 0);
				 }
			 }    
			 // check device id
			 if (addItem == true) 
			 {
				 if (query.getDevice().size() > 0) 
				 {
					 Set<String> devices = new HashSet<String>(query.getDevice());
					 devices.retainAll(item.getDevice());
					 addItem = (query.getDevice().size() > 0);
				 }
			 }    
			 // check content type list
			 if (addItem == true) 
			 {
				 if (query.getType().size() > 0) 
				 {
					 addItem = query.getType().contains(item.getType());
				 }
			 }
			 // finally, if all checks passed, add the product to the result set.
			 if (addItem == true) 
			 {
				 resultList.add(item);
			 }
		 }
		 return resultList; 
	 }
	
	/**
	 * Converts a CSV String to a Query object
	 *
	 * @param s the CSV String
	 * @return results of the Query
	 */
	
	/**
	 * Gets the categories.
	 *
	 * @return the categories
	 */
	public Set<String> getCategories() {
		return categories;
	}

	/**
	 * Adds the category.
	 *
	 * @param category the category
	 * @return the query
	 */
	public Query addCategory(String category)
	{
		this.categories.add(category);
		return this;
	}
	
	/**
	 * Sets the categories.
	 *
	 * @param categories the categories to set
	 * @return the query
	 */
	public Query setCategories(Set<String> categories) {
		this.categories = categories;
		return this;
	}

	/**
	 * Gets the text search.
	 *
	 * @return the textSearch
	 */
	public String getTextSearch() {
		return textSearch;
	}

	/**
	 * Sets the text search.
	 *
	 * @param textSearch the textSearch to set
	 * @return the query
	 */
	public Query setTextSearch(String textSearch) {
		this.textSearch = textSearch;
		return this;
	}

	/**
	 * Gets the max price.
	 *
	 * @return the maxPrice
	 */
	public float getMaxPrice() {
		return maxPrice;
	}

	/**
	 * Sets the max price.
	 *
	 * @param maxPrice the maxPrice to set
	 * @return the query
	 */
	public Query setMaxPrice(float maxPrice) {
		this.maxPrice = maxPrice;
		return this;
	}

	/**
	 * Gets the min rating.
	 *
	 * @return the minRating
	 */
	public int getMinRating() {
		return minRating;
	}

	/**
	 * Sets the min rating.
	 *
	 * @param minRating the minRating to set
	 * @return the query
	 */
	public Query setMinRating(int minRating) {
		this.minRating = minRating;
		return this;
	}

	/**
	 * Gets the languages.
	 *
	 * @return the languages
	 */
	public Set<String> getLanguages() {
		return languages;
	}

	/**
	 * Adds the language.
	 *
	 * @param language the language
	 * @return the query
	 */
	public Query addLanguage(String language)
	{
		this.languages.add(language);
		return this;
	}
	
	/**
	 * Sets the languages.
	 *
	 * @param languages the languages to set
	 * @return the query
	 */
	public Query setLanguages(Set<String> languages) {
		this.languages = languages;
		return this;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public Set<String> getCountry() {
		return country;
	}

	/**
	 * Adds the country.
	 *
	 * @param country the country
	 * @return the query
	 */
	public Query addCountry(String country)
	{
		this.country.add(country);
		return this;
	}
	
	/**
	 * Sets the country.
	 *
	 * @param country the country to set
	 * @return the query
	 */
	public Query setCountry(Set<String> country) {
		this.country = country;
		return this;
	}

	/**
	 * Gets the device.
	 *
	 * @return the device
	 */
	public Set<String> getDevice() {
		return device;
	}

	/**
	 * Adds the device.
	 *
	 * @param device the device
	 * @return the query
	 */
	public Query addDevice(String device)
	{
		this.device.add(device);
		return this;
	}
	
	/**
	 * Sets the device.
	 *
	 * @param device the device to set
	 * @return the query
	 */
	public Query setDevice(Set<String> device) {
		this.device = device;
		return this;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Set<String> getType() {
		return type;
	}
	
	/**
	 * Adds the type.
	 *
	 * @param type the type
	 * @return the query
	 */
	public Query addType(String type)
	{
		this.type.add(type);
		return this;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the type to set
	 * @return the query
	 */
	public Query setType(Set<String> type) {
		this.type = type;
		return this;
	}
	
	
}
