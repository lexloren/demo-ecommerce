package cscie97.asn4.ecommerce.authentication;

/**
 * The AuthVisitor class.
 */
public abstract class AuthVisitor {

	/**
	 * Visit user.
	 *
	 * @param user the user
	 */
	public abstract void visitUser(User user);
	
	/**
	 * Visit service.
	 *
	 * @param service the service
	 */
	public abstract void visitService(Service service);
	
	/**
	 * Visit role.
	 *
	 * @param role the role
	 */
	public abstract void visitRole(Role role);
	
	/**
	 * Visit permission.
	 *
	 * @param permission the permission
	 */
	public abstract void visitPermission(Permission permission);
	
	/**
	 * Visit authservice.
	 *
	 * @param authservice the authservice
	 */
	public abstract void visitAuthService(AuthenticationService authservice);
	
	/**
	 * Visit entitlement.
	 *
	 * @param e the eentitlement
	 */
	public void visitEntitlement(Entitlement e) {
		if (e.isRole()) this.visitRole((Role) e);
		else this.visitPermission((Permission) e);
	}
}
