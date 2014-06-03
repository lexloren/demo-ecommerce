package cscie97.asn4.ecommerce.collection;

import java.util.ArrayList;
import java.util.List;

import cscie97.asn4.ecommerce.authentication.AccessDeniedException;
import cscie97.asn4.ecommerce.authentication.AuthenticationService;
import cscie97.asn4.ecommerce.authentication.InvalidAccessTokenException;

/**
 * The Collection class: a Collectible which can contain children.
 */
public abstract class Collection extends Collectible {

	private String name;
	private String description;
	protected List<Collectible> children;
	
	/**
	 * Instantiates a new collection.
	 *
	 * @param identifier the identifier
	 * @param name the name
	 * @param description the description
	 */
	public Collection(String identifier, String name,
			String description) {
		super(identifier);
		this.name = name;
		this.description = description;
		this.children = new ArrayList<Collectible>();
	}

	/**
	 * Makes a subclass-specific iterator.
	 *
	 * @return the iterator
	 * @throws CollectionException 
	 */
	public abstract Iterator makeIterator() throws CollectionException;
	
	public void add(Collectible collectible, String GUID) throws InvalidAccessTokenException, AccessDeniedException {
		AuthenticationService authservice = AuthenticationService.getAuthenticationService();
		authservice.checkToken(GUID, "add_content");
		children.add(collectible);
	}
	
	protected void add(Collectible collectible) {
		children.add(collectible);
	}
	
	public void remove(Collectible collectible, String GUID) throws InvalidAccessTokenException, AccessDeniedException {
		AuthenticationService authservice = AuthenticationService.getAuthenticationService();
		authservice.checkToken(GUID, "add_content");
		children.remove(collectible);
	}

	public List<Collectible> getChildren() {
		return children;
	}

	public boolean isCollection() {
		return true;
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

}
