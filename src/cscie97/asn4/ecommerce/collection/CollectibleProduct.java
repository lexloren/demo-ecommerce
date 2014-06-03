package cscie97.asn4.ecommerce.collection;

import java.util.List;

import cscie97.asn4.ecommerce.product.Content;
import cscie97.asn4.ecommerce.product.Query;
import cscie97.asn4.ecommerce.product.Store;

/**
 * The CollectibleProduct class: a collectible-type wrapper for a product ID.
 */
public class CollectibleProduct extends Collectible {

	/**
	 * Instantiates a new collectible product.
	 *
	 * @param productid the productid
	 */
	public CollectibleProduct(String productid) {
		super(productid);
	}
	
	/**
	 * Gets the product from the Product API.
	 *
	 * @return the product
	 */
	public Content getProduct()
	{
		Store store = Store.getStore();
		return store.getItem(this.getIdentifier());
	}
	
	public boolean isCollection() {
		return false;
	}
	
	public void add(Collectible collectible, String GUID) throws CollectionException {
		throw new CollectionException(this.getIdentifier(), "Attempt to add collectible to item that is not a collection");
	}

	public void remove(Collectible collectible, String GUID) throws CollectionException {
		throw new CollectionException(this.getIdentifier(), "Attempt to add collectible to item that is not a collection");

	}

	public List<Collectible> getChildren() throws CollectionException {
		throw new CollectionException(this.getIdentifier(), "Attempt to add collectible to item that is not a collection");
	}

	public String getName() throws CollectionException {
		Store store = Store.getStore();
		Content item = store.getItem(this.getIdentifier());
		if (item == null) throw new CollectionException(this.getIdentifier(), "Product could not be found in store.");
		return item.getName();
	}

	public String getDescription() throws CollectionException {
		Store store = Store.getStore();
		Content item = store.getItem(this.getIdentifier());
		if (item == null) throw new CollectionException(this.getIdentifier(), "Product could not be found in store.");
		return item.getDescription();
	}

	public void addQuery(Query query) throws CollectionException {
		throw new CollectionException(this.getIdentifier(), "Attempt to add query to item that is not a collection");
	}
}
