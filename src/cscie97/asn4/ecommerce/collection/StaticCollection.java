package cscie97.asn4.ecommerce.collection;

import cscie97.asn4.ecommerce.product.Query;

/**
 * The StaticCollection class.
 */
public class StaticCollection extends Collection {
	
	/**
	 * Instantiates a new static collection.
	 *
	 * @param identifier the identifier
	 * @param name the name
	 * @param description the description
	 * @param GUID the guid
	 */
	public StaticCollection(String identifier, String name,
			String description) {
		super(identifier, name, description);
	}	
	
	public Iterator makeIterator() throws CollectionException {
		return new StaticCIterator(this.getChildren());
	}

	public void addQuery(Query query) throws CollectionException {
		throw new CollectionException(this.getIdentifier(), "Static Collections cannot contain queries.");
	}
}
