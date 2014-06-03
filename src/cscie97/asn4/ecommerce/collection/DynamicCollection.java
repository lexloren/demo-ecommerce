package cscie97.asn4.ecommerce.collection;

import java.util.List;

import cscie97.asn4.ecommerce.product.Content;
import cscie97.asn4.ecommerce.product.Query;
import cscie97.asn4.ecommerce.product.Store;

/**
 * The Class DynamicCollection.
 */
public class DynamicCollection extends Collection {

	private Query criteria;
		
	/**
	 * Instantiates a new dynamic collection.
	 *
	 * @param identifier the identifier
	 * @param name the name
	 * @param description the description
	 * @param query the query
	 * @param GUID the guid
	 */
	public DynamicCollection(String identifier, String name,
			String description, Query query) {
		super(identifier, name, description);
		this.criteria = query;
	}

	/**
	 * Instantiates a new dynamic collection.
	 *
	 * @param identifier the identifier
	 * @param name the name
	 * @param description the description
	 */
	public DynamicCollection(String identifier, String name,
			String description) {
		super(identifier, name, description);
	}
	
	/**
	 * Adds a query, or overwrites the existing query.
	 *
	 * @param query the query
	 */
	public void addQuery(Query query) {
		this.criteria = query;
	}
	
	public Iterator makeIterator() {
		Store store = Store.getStore();
		List<Content> products = criteria.searchProducts(store);
		try {
			return new DynamicCIterator(products, this.getChildren());
		} catch (CollectionException e) {
			return null;
		}
	}

}
