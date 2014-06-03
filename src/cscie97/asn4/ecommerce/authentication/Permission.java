package cscie97.asn4.ecommerce.authentication;

import java.util.Set;

/**
 * The Permission class.
 */
public class Permission extends Entitlement {

	private Service service;
	
	/**
	 * Instantiates a new permission.
	 *
	 * @param identifier the identifier
	 * @param name the name
	 * @param description the description
	 * @param service the service
	 */
	public Permission(String identifier, String name, String description, Service service) {
		super(identifier, name, description);
		this.service = service;
	}

	public void add(Entitlement e) throws AuthenticationServiceException {
		 throw new AuthenticationServiceException("Attempt to add entitlement " + e.getIdentifier() + 
				 "to Permission " + this.getIdentifier());
	}

	public Set<Entitlement> getChildren() throws AuthenticationServiceException {
		throw new AuthenticationServiceException("Attempt to add children of Permission " + this.getIdentifier());
	}

	public void acceptVisitor(AuthVisitor v) {
		v.visitPermission(this);
	}

	public Service getService() {
		return service;
	}

	public boolean isRole() {
		return false;
	}


}
