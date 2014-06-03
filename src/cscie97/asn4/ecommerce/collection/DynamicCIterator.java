package cscie97.asn4.ecommerce.collection;

import java.util.ArrayList;
import java.util.List;

import cscie97.asn4.ecommerce.product.Content;

/**
 * The Class DynamicCIterator.
 */
public class DynamicCIterator extends Iterator {

	/**
	 * Instantiates a new iterator for a dynamic ccollection.
	 *
	 * @param products a list of Content objects
	 * @param list a list of child collections
	 * @throws CollectionException
	 */
	public DynamicCIterator(List<Content> products, List<Collectible> list) throws CollectionException {
		flatList = new ArrayList<Collectible>();
		
		for (Content product: products)
		{
			addChild(new CollectibleProduct(product.getContentid()));
		}

		for (Collectible child: list)
		{
			addChild(child);
		}
		iterator = flatList.listIterator();
	}
	


}
