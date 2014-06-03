package cscie97.asn4.ecommerce.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class StaticCIterator.
 */
public class StaticCIterator extends Iterator {

	/**
	 * Instantiates a new iterator for a static collection.
	 *
	 * @param list a list of child collections
	 * @throws CollectionException
	 */
	public StaticCIterator(List<Collectible> list) throws CollectionException {
		
		flatList = new ArrayList<Collectible>();
		
		for (Collectible child: list)
		{
			addChild(child);
		}
		iterator = flatList.listIterator();
	}
}
