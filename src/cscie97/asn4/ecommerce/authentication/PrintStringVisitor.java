package cscie97.asn4.ecommerce.authentication;

/**
 * The Class PrintStringVisitor.
 */
public class PrintStringVisitor extends AuthVisitor {

	/**
	 * Instantiates a new prints the string visitor.
	 */
	public PrintStringVisitor() {
		super();
	}

	public void visitUser(User user) {
		System.out.println("User: " + user.getName() + ". ID: "+ user.getIdentifier() );
		System.out.println(user.getName() + " has the following login names:");
		for (String login : user.getLogins()) {
			System.out.println("\t" + login);
		}
		System.out.println(user.getName() + " has the following entitlements:");
		for (Entitlement priv : user.getEntitlements()) {
			System.out.print("\t" + priv.getIdentifier());
			if (priv.isRole()) System.out.println(" (role)");
			else System.out.println(" (permission)");
		}
	}

	public void visitService(Service service) {
		System.out.println("Service: " + service.getName() + ". ID: "+ service.getIdentifier() );
		System.out.println(service.getDescription());
	}

	public void visitRole(Role role) {
		System.out.println("Role: " + role.getName() + ". ID: "+ role.getIdentifier() );
		System.out.println(role.getDescription());
		System.out.println(role.getName() + " provides the following entitlements: ");
		for (Entitlement priv : role.getChildren()) {
			System.out.print("\t" + priv.getIdentifier());
			if (priv.isRole()) System.out.println(" (role)");
			else System.out.println(" (permission)");
		}
	}

	public void visitPermission(Permission permission) {
		System.out.println("Permission: " + permission.getName() + ". ID: "+ permission.getIdentifier() );
		System.out.println(permission.getDescription());
	}

	public void visitAuthService(AuthenticationService authservice) {
		System.out.println("Users");
		System.out.println("****************************");
		for (User user : authservice.getUsers()) {
			visitUser(user);
		}
		System.out.println();
		
		System.out.println("Services");
		System.out.println("****************************");
		for (Service service : authservice.getServices()) {
			visitService(service);
		}
		System.out.println();
		
		System.out.println("Entitlements");
		System.out.println("****************************");
		for (Entitlement e : authservice.getEntitlements()) {
			visitEntitlement(e);
		}

	}

}
