package cscie97.asn4.ecommerce.collection;

import java.util.List;

import cscie97.asn4.ecommerce.authentication.AccessDeniedException;
import cscie97.asn4.ecommerce.authentication.InvalidAccessTokenException;
import cscie97.asn4.ecommerce.product.Query;


/**
 * The Collectible class: an abstract class embodying the composite pattern
 */
public abstract class Collectible {

	private String identifier;
	
	/**
	 * Instantiates a new collectible. Note that checking for duplicate 
	 * identifiers must be done outside of the constructor.
	 *
	 * @param identifier the identifier
	 */
	public Collectible(String identifier) 
	{
		this.identifier = identifier;
	}

	/**
	 * Gets the identifier.
	 *
	 * @return the identifier
	 */
	public String getIdentifier()
	{
		return identifier;
	}
	
	/**
	 * Adds a child Collectible.
	 *
	 * @param collectible
	 * @param GUID
	 * @throws CollectionException
	 * @throws InvalidAccessTokenException 
	 * @throws AccessDeniedException 
	 * @throws AuthenticationServiceException 
	 */
	public abstract void add(Collectible collectible, String GUID) throws CollectionException, InvalidAccessTokenException, AccessDeniedException;
	
	/**
	 * Removes a child Collectible.
	 *
	 * @param collectible
	 * @param GUID
	 * @throws CollectionException
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 * @throws AuthenticationServiceException 
	 */
	public abstract void remove(Collectible collectible, String GUID)  throws CollectionException, InvalidAccessTokenException, AccessDeniedException;
	
	/**
	 * Gets a list of child Collectibles.
	 *
	 * @return child Collectibles
	 * @throws CollectionException
	 */
	public abstract List<Collectible> getChildren()  throws CollectionException;
	
	/**
	 * Checks if is collection.
	 *
	 * @return true, if is collection
	 */
	public abstract boolean isCollection();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 * @throws CollectionException 
	 */
	public abstract String getName() throws CollectionException;

	/**
	 * Gets the description.
	 *
	 * @return the description
	 * @throws CollectionException 
	 */
	public abstract String getDescription() throws CollectionException;

	/**
	 * Adds a query object to a dynamic collection.
	 *
	 * @throws CollectionException 
	 */
	public abstract void addQuery(Query query) throws CollectionException;
}
