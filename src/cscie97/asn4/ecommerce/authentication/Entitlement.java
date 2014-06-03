package cscie97.asn4.ecommerce.authentication;

import java.util.Set;

/**
 * The Entitlement class.
 */
public abstract class Entitlement {

	private String identifier;
	private String name;
	private String description;
	
	/**
	 * Instantiates a new entitlement.
	 *
	 * @param identifier the identifier
	 * @param name the name
	 * @param description the description
	 */
	public Entitlement(String identifier, String name, String description) {
		this.identifier = identifier;
		this.setName(name);
		this.setDescription(description);
	}
	
	/**
	 * Adds an entitlement.
	 *
	 * @param e the e
	 * @throws AuthenticationServiceException the authentication service exception
	 */
	public abstract void add(Entitlement e) throws AuthenticationServiceException;

	/**
	 * Gets the child entitlements.
	 *
	 * @return the children
	 * @throws AuthenticationServiceException the authentication service exception
	 */
	public abstract Set<Entitlement> getChildren() throws AuthenticationServiceException;
	
	/**
	 * Checks if is the entitlement is a role.
	 *
	 * @return true, if it is a role
	 */
	public abstract boolean isRole();

	/**
	 * Accept visitor.
	 *
	 * @param v the visitor
	 */
	public abstract void acceptVisitor(AuthVisitor v);

	public String getIdentifier() {
		return identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

}
