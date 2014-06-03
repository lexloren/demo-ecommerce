package cscie97.asn4.ecommerce.authentication;

import java.util.HashSet;
import java.util.Set;

/**
 * The Role class.
 */
public class Role extends Entitlement {

	private Set<Entitlement> entitlements;
	
	/**
	 * Instantiates a new role.
	 *
	 * @param identifier the identifier
	 * @param name the name
	 * @param description the description
	 */
	public Role(String identifier, String name, String description) {
		super(identifier, name, description);
		entitlements = new HashSet<Entitlement>();
	}

	public void add(Entitlement e) {
		this.entitlements.add(e);
	}

	public Set<Entitlement> getChildren() {
		return this.entitlements;
	}

	public void acceptVisitor(AuthVisitor v) {
		v.visitRole(this);
	}

	public boolean isRole() {
		return true;
	}

}
