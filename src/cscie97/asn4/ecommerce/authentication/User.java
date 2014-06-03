package cscie97.asn4.ecommerce.authentication;

import java.util.HashSet;
import java.util.Set;

/**
 * The User class.
 */
public class User {

	private String identifier;
	private String name;
	private Set<Credentials> credentials;
	private Set<Entitlement> entitlements;	

	private Set<Entitlement> checked;
		
	/**
	 * Instantiates a new user.
	 *
	 * @param identifier the identifier
	 * @param name the name
	 * @param username the username
	 * @param password the password
	 */
	public User(String identifier, String name, String username, String password) {
		this.identifier = identifier;
		credentials = new HashSet<Credentials>();
		entitlements = new HashSet<Entitlement>();
		setName(name);
		addCredentials(new Credentials(username, password));
	}

	/**
	 * Adds the entitlement.
	 *
	 * @param e the e
	 */
	public void addEntitlement(Entitlement e) {
		entitlements.add(e);
	}
	
	/**
	 * Adds the credentials.
	 *
	 * @param c the c
	 */
	public void addCredentials(Credentials c) {
		credentials.add(c);
	}
	
	/**
	 * Checks if is valid.
	 *
	 * @param username the username
	 * @param password the password
	 * @return true, if is valid
	 * @throws AuthenticationException the authentication exception
	 */
	public boolean isValid(String username, String password) throws AuthenticationException {
		for (Credentials c:credentials){
			if (c.isValid(username, password) == true) return true;
		}
		return false;
	}
	
	/**
	 * Checks for permission.
	 *
	 * @param permissionid the permissionid
	 * @return true, if successful
	 * @throws AuthenticationServiceException
	 */
	public boolean hasPermission(String permissionid) throws AuthenticationServiceException {
		
		checked = new HashSet<Entitlement>();
		return permissionInSet(permissionid, this.getEntitlements());
	}
	
	/**
	 * Permission in set.
	 *
	 * @param permissionid the permissionid
	 * @param e the e
	 * @return true, if successful
	 * @throws AuthenticationServiceException the authentication service exception
	 */
	private boolean permissionInSet(String permissionid, Set<Entitlement> e) throws AuthenticationServiceException {
		for (Entitlement entitlement : e) {
			if (entitlement.isRole()) {
				if (checked.contains(entitlement) == false) {
					checked.add(entitlement);
					if (permissionInSet(permissionid, entitlement.getChildren())) return true;
				}
			} else if (entitlement.getIdentifier().equalsIgnoreCase(permissionid)) return true;
		}
		return false;
	}

	public Set<String> getLogins() {
		Set<String> usernames = new HashSet<String>();
		for (Credentials cred:this.credentials) {
			usernames.add(cred.getUsername());
		}
		return usernames;
	}

	/**
	 * Change password.
	 *
	 * @param username the username
	 * @param newPassword the new password
	 */
	public void changePassword(String username, String newPassword) {
		for (Credentials c:credentials){
			if (c.getUsername().equalsIgnoreCase(username)) {
				c.newPassword(newPassword);
			}
		}
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Entitlement> getEntitlements() {
		return entitlements;
	}
	
	/**
	 * Accept visitor.
	 *
	 * @param v the v
	 */
	public void AcceptVisitor(AuthVisitor v) {
		v.visitUser(this);
	}
		
}
