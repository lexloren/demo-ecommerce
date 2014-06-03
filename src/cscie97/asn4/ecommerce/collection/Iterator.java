package cscie97.asn4.ecommerce.collection;

import java.util.List;
import java.util.ListIterator;

/**
 * The Iterator class.
 */
public abstract class Iterator {

	private Collectible currentItem;
	protected List<Collectible> flatList;
	protected ListIterator<Collectible> iterator;
	
	/**
	 * Resets the iterator.
	 */
	public void reset() {
		iterator = flatList.listIterator();
	}
	
	/**
	 * Points currentItem() to the next item.
	 */
	public void next() {
		if (iterator.hasNext() == true) {
			currentItem = iterator.next();
		}
	}
	
	/**
	 * Returns the current item.
	 *
	 * @return the collectible
	 */
	public Collectible currentItem() {
		return currentItem;
	}
	
	/**
	 * Checks if is done.
	 *
	 * @return true, if is done
	 */
	public boolean isDone() {
		return !(iterator.hasNext());
	}
	
	/**
	 * Adds a child collectible and, if appropriate, all its children. Prevents endless loops.
	 *
	 * @param child the child
	 * @throws CollectionException 
	 */
	protected void addChild(Collectible child) throws CollectionException {
		if (child.isCollection() == true && (flatList.contains(child) == false))
		{
			flatList.add(child);
			Iterator i = ((Collection) child).makeIterator();
			while (i.isDone() == false) {
				i.next();
				Collectible collectible = i.currentItem();
				addChild(collectible);
			}			
		} else flatList.add(child);
	}
}
