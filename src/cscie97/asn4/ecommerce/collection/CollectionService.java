package cscie97.asn4.ecommerce.collection;

import java.util.HashMap;
import java.util.Map;

import cscie97.asn4.ecommerce.authentication.AccessDeniedException;
import cscie97.asn4.ecommerce.authentication.AuthenticationService;
import cscie97.asn4.ecommerce.authentication.InvalidAccessTokenException;

/**
 * The CollectionService class: a Singleton which holds all Collections in memory 
 * and provides an interface for common tasks.
 */
public class CollectionService {

	private static final CollectionService service = new CollectionService();
	private Map<String, Collectible> collectibles;
	
	/**
	 * Gets the collection service.
	 *
	 * @return the collection service
	 */
	public static CollectionService getCollectionService() {
		return service;
	}
	
	/**
	 * Instantiates a new collection service.
	 */
	private CollectionService() {
		this.collectibles = new HashMap<String, Collectible>();
	}

	/**
	 * Search collections by keyword.
	 *
	 * @param key the keyword
	 * @return a collection of collections matching the result. 
	 * @throws CollectionException 
	 */
	public Collection searchCollections(String key) throws CollectionException {
		String searchid = "search:" + key;
		while (collectibles.containsKey(searchid) == true) {
			if (collectibles.get(searchid).isCollection() == true) {
				return (Collection) collectibles.get(searchid);
			} else searchid = searchid + "a";
		}
		Collection results = new StaticCollection(searchid, "search results",
			"results for search: " + key);

		
			for (Collectible c : collectibles.values()) {
				if (key.equalsIgnoreCase("") == false) {
					if ((c.isCollection() == true) && 
							(c.getName().toLowerCase().contains(key.toLowerCase())) || 
							(c.getDescription().toLowerCase().contains(key.toLowerCase())))  {
						results.add(c);
					}
				} else if (c.isCollection() == true) results.add(c);
			}
		return results;
	}
	
	/**
	 * Gets the item.
	 *
	 * @param identifier the identifier
	 * @return the Collectible with a matching identifier. Null if not found.
	 */
	public Collectible getItem(String identifier) {
		return collectibles.get(identifier);
	}
	
	/**
	 * Factory method for creating collections.
	 *
	 * @param type the type
	 * @param identifier the identifier
	 * @param name the name
	 * @param description the description
	 * @param GUID the guid
	 * @return the collection
	 * @throws CollectionException the collection exception
	 * @throws AuthenticationServiceException 
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 */
	public Collection newCollection(String type, String identifier, String name,
			String description, String GUID) throws CollectionException, InvalidAccessTokenException, AccessDeniedException {
		
		AuthenticationService authservice = AuthenticationService.getAuthenticationService();
		authservice.checkToken(GUID, "create_collection");
		
		if (collectibles.containsKey(identifier) == true) {
			throw new CollectionException(identifier, "Item with given ID already exists.");
		} 
		Collection newcollection = null;
		if (type.equalsIgnoreCase("static") == true) {
			newcollection = new StaticCollection(identifier, name, description);
		} else if (type.equalsIgnoreCase("dynamic") == true) {
			newcollection =  new DynamicCollection(identifier, name, description);
		} else {
			throw new CollectionException(identifier, "Collection type could not be determined.");
		}	
		
		collectibles.put(identifier, newcollection);
		return newcollection;
	}
	
	/**
	 * New product.
	 *
	 * @param productid the productid
	 * @return the collectible product
	 * @throws CollectionException the collection exception
	 */
	public CollectibleProduct newProduct(String productid) throws CollectionException {
		if (collectibles.containsKey(productid) == true) {
			throw new CollectionException(productid, "Item with given ID already exists.");
		} 
		CollectibleProduct product = new CollectibleProduct(productid);
		collectibles.put(productid, product);
		return product;
	}
	
	/**
	 * Gets an iterator for the collection matching the given identifier. 
	 * If passed an empty string, returns an iterator for all collections. 
	 *
	 * @param collectionid the collectionid
	 * @return the iterator
	 * @throws CollectionException 
	 */
	public Iterator getIterator(String collectionid) throws CollectionException {
		Collectible c = null;
		if (collectionid.equalsIgnoreCase("")){
			c = searchCollections("");
		} else c = collectibles.get(collectionid);
		if (c.isCollection() == false) { 
			return null;
		} else return ((Collection) c).makeIterator();
	}
}
