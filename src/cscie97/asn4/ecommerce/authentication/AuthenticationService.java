package cscie97.asn4.ecommerce.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The AuthenticationService.
 * 
 * The AuthenticationService is a singleton object, holding all data 
 * in memory. It is, in part, an interface between client users and 
 * the rest of the classes in the authentication package. All restricted 
 * methods of the AuthenticationService use the AuthenticationService 
 * Access Token and verification methods. When the AuthenticationService 
 * singleton is first instantiated, it creates a Service object for 
 * its own service, all permissions for its service, and creates a 
 * user with those permissions.
 * 
 */
public class AuthenticationService {

	private Map<String, User> users;
	private Map<String, Entitlement> entitlements;
	private Map<String, Service> services;
	private Map<String, AccessToken> tokens;
	private static final AuthenticationService service = new AuthenticationService();

	/**
	 * Instantiates a new authentication service.
	 */
	private AuthenticationService() {
		users = new HashMap<String, User>();
		entitlements = new HashMap<String, Entitlement>();
		services = new HashMap<String, Service>();
		tokens = new HashMap<String, AccessToken>();

		makeAdmin();
	}

	/**
	 * Gets the authentication service.
	 *
	 * @return the authentication service
	 */
	public static AuthenticationService getAuthenticationService() {
		return service;
	}

	/**
	 * Makes a new user.
	 *
	 * @param identifier the identifier
	 * @param name the name
	 * @param username the username
	 * @param password the password
	 * @param GUID the String Access Token
	 * @throws AuthenticationServiceException the authentication service exception
	 * @throws InvalidAccessTokenException the invalid access token exception
	 * @throws AccessDeniedException the access denied exception
	 */
	public void newUser(String identifier, String name, String username, String password, String GUID) 
			throws AuthenticationServiceException, InvalidAccessTokenException, AccessDeniedException {
		
		checkToken(GUID, "create_user");
				
		for (User user:this.users.values()) {
			if (user.getIdentifier().equalsIgnoreCase(identifier)) throw new AuthenticationServiceException("User with identifier " + identifier + " already exists.");
			for (String usedlogin:user.getLogins()) {
				if (usedlogin.equalsIgnoreCase(username)) throw new AuthenticationServiceException("Username  " + username + " already exists.");
			}
		}
		
		User newUser = new User(identifier, name, username, password);
		users.put(identifier, newUser);	
	}

	/**
	 * Makes a new service.
	 *
	 * @param identifier the identifier
	 * @param name the name
	 * @param description the description
	 * @param GUID the guid
	 * @throws InvalidAccessTokenException the invalid access token exception
	 * @throws AccessDeniedException the access denied exception
	 * @throws AuthenticationServiceException the authentication service exception
	 */
	public void newService(String identifier, String name, String description, String GUID) throws InvalidAccessTokenException, AccessDeniedException, AuthenticationServiceException  {
		
		checkToken(GUID, "define_service");
		
		if (this.services.containsKey(identifier) == true) throw new AuthenticationServiceException("Service " + identifier + " already exists.");
		Service newservice = new Service(identifier, name, description);
		services.put(identifier, newservice);
	}

	/**
	 * Makes a new permission.
	 *
	 * @param identifier the identifier
	 * @param name the name
	 * @param description the description
	 * @param serviceid the serviceid
	 * @param GUID the guid
	 * @throws InvalidAccessTokenException the invalid access token exception
	 * @throws AccessDeniedException the access denied exception
	 * @throws AuthenticationServiceException the authentication service exception
	 */
	public void newPermission(String identifier, String name, String description, String serviceid, String GUID) throws InvalidAccessTokenException, AccessDeniedException, AuthenticationServiceException  {
		
		checkToken(GUID, "define_permission");
		if (this.entitlements.containsKey(identifier) == true) throw new AuthenticationServiceException("Entitlement " + identifier + " already exists.");
		Service service = services.get(serviceid);
		if (service == null) throw new AuthenticationServiceException("Service " + serviceid + " not found.");
		
		Permission newpermission = new Permission(identifier, name, description, service);
		entitlements.put(identifier, newpermission);
	}

	/**
	 * Makes a new role.
	 *
	 * @param identifier the identifier
	 * @param name the name
	 * @param description the description
	 * @param GUID the guid
	 * @throws AuthenticationServiceException the authentication service exception
	 * @throws InvalidAccessTokenException the invalid access token exception
	 * @throws AccessDeniedException the access denied exception
	 */
	public void newRole(String identifier, String name, String description, String GUID) throws AuthenticationServiceException, InvalidAccessTokenException, AccessDeniedException  {
		
		checkToken(GUID, "define_role");
		if (this.entitlements.containsKey(identifier) == true) throw new AuthenticationServiceException("Entitlement " + identifier + " already exists.");
		
		Role newrole = new Role(identifier, name, description);
		entitlements.put(identifier, newrole);
	}

	/**
	 * Adds the entitlement to user.
	 *
	 * @param userid the userid
	 * @param entitlementid the entitlementid
	 * @param GUID the guid
	 * @throws InvalidAccessTokenException the invalid access token exception
	 * @throws AccessDeniedException the access denied exception
	 * @throws AuthenticationServiceException the authentication service exception
	 */
	public void addEntitlementToUser(String userid, String entitlementid, String GUID) throws InvalidAccessTokenException, AccessDeniedException, AuthenticationServiceException  {
		
		checkToken(GUID, "add_entitlement_to_user");
		User user = this.users.get(userid);
		Entitlement entitlement = this.entitlements.get(entitlementid);
		if (entitlement == null) throw new AuthenticationServiceException("Entitlement " + entitlementid + " not found");
		if (user == null) throw new AuthenticationServiceException("User " + userid + " not found");
		user.addEntitlement(entitlement);
	}

	/**
	 * Adds the credentials to user.
	 *
	 * @param userid the userid
	 * @param username the username
	 * @param password the password
	 * @param GUID the guid
	 * @throws InvalidAccessTokenException the invalid access token exception
	 * @throws AccessDeniedException the access denied exception
	 * @throws AuthenticationServiceException the authentication service exception
	 */
	public void addCredentialsToUser(String userid, String username, String password, String GUID) throws InvalidAccessTokenException, AccessDeniedException, AuthenticationServiceException  {
		checkToken(GUID, "add_credential_to_user");
		User user = this.users.get(userid);
		if (user == null) throw new AuthenticationServiceException("User " + userid + " not found");
		
		for (User u:this.users.values()) {
			for (String usedlogin:u.getLogins()) {
				if (usedlogin.equalsIgnoreCase(username)) throw new AuthenticationServiceException("Username  " + username + " already exists.");
			}
		}
		Credentials credentials = new Credentials(username, password);
		user.addCredentials(credentials);
	}
	
	/**
	 * Adds the entitlement to role.
	 *
	 * @param entitlementid the entitlementid
	 * @param roleid the roleid
	 * @param GUID the guid
	 * @throws InvalidAccessTokenException
	 * @throws AccessDeniedException
	 * @throws AuthenticationServiceException
	 */
	public void addEntitlementToRole(String entitlementid, String roleid, String GUID) throws InvalidAccessTokenException, AccessDeniedException, AuthenticationServiceException {
		checkToken(GUID, "add_entitlement");
		Entitlement role = this.entitlements.get(roleid);
		Entitlement entitlement = this.entitlements.get(entitlementid);
		if (entitlement == null) throw new AuthenticationServiceException("Entitlement " + entitlementid + " not found");
		if (role == null) throw new AuthenticationServiceException("Role " + roleid + " not found");
		role.add(entitlement);
	}
	
	/**
	 * Login.
	 *
	 * @param username the username
	 * @param password the password
	 * @return the AccessToken String
	 * @throws AuthenticationException
	 */
	public String login(String username, String password) throws AuthenticationException {
		for (User u:this.users.values()) {
				if (u.isValid(username, password)) {
					AccessToken token = new AccessToken(u);
					tokens.put(token.getIdentifier(), token);
					return token.getIdentifier();
				}
			}
		throw new AuthenticationException("Login " + username + " not found.");
		
	}

	/**
	 * Check token.
	 *
	 * @param token the token
	 * @param permissionid the permissionid
	 * @return true, if user has the appropriate permission
	 * @throws InvalidAccessTokenException
	 * @throws AccessDeniedException
	 */
	public boolean checkToken(String token, String permissionid) throws InvalidAccessTokenException, AccessDeniedException {
		AccessToken tokenreq  = tokens.get(token);
		if (tokenreq == null) throw new InvalidAccessTokenException("Access Token not found."); 
		if (tokenreq.grantsPermission(permissionid) == false) throw new AccessDeniedException("Access Token does not grant the permission " + permissionid);	
		return true;
	}

	/**
	 * Accept visitor.
	 *
	 * @param v the v
	 */
	public void acceptVisitor(AuthVisitor v) {
		v.visitAuthService(this);
	}

	/**
	 * Logout.
	 *
	 * @param GUID the guid
	 * @throws InvalidAccessTokenException the invalid access token exception
	 */
	public void logout(String GUID) throws InvalidAccessTokenException {
		AccessToken tokenreq  = tokens.get(GUID);
		if (tokenreq == null) throw new InvalidAccessTokenException("Access Token not found.");
		tokenreq.invalidate();
	}
	
	/**
	 * Change password.
	 *
	 * @param username the username
	 * @param oldPassword the old password
	 * @param newPassword the new password
	 * @throws AuthenticationException
	 */
	public void changePassword(String username, String oldPassword, String newPassword) throws AuthenticationException {
		
		User toChange = null;
		for (User u: users.values()) {
			if (u.isValid(username, oldPassword)) toChange = u;	
		}
		if (toChange == null) throw new AuthenticationException("User with login " + username + " not found.");
		toChange.changePassword(username, newPassword);
	}
	
	public  Collection<User> getUsers() {
		return users.values();
		
	}

	public Collection<Entitlement> getEntitlements() {
		return entitlements.values();
	}
	
	public Collection<Service> getServices() {
		return services.values();
	}
		
	/**
	 * Make admin.
	 */
	private void makeAdmin(){
		
		User rootUser = new User("root", "Root Admin", "root", "rootpassword");
		users.put("root", rootUser);
		
		Service s = new Service("authentication_service", "Authentication Service", 
				"Manage Authentication Configuration and Control Access to Restricted Service Interfaces");
				services.put("authentication_service", s);
		
		List<Permission> admins = new ArrayList<Permission>();
		admins.add(new Permission("define_service", "Define Service Permission", "Permission to create a new service", s));
		admins.add(new Permission("define_permission", "Define Permission Permission", "Permission to create a new permission", s));
		admins.add(new Permission("define_role", "Define Role Permission", "Permission to create a new role", s));
		admins.add(new Permission("add_entitlement", "Add entitlement to role permission", "Permission to add an entitlement to a role", s));
		admins.add(new Permission("create_user", "Create User Permission", "Permission to create create a user", s));
		admins.add(new Permission("add_credential_to_user", "Add Credential to User Permission", "Permission to add credentials to a user", s));
		admins.add(new Permission("add_entitlement_to_user", "Add Entitlement to User Permission", "Permission to add entitlements to a user", s));
		
		for (Permission p:admins) {
			entitlements.put(p.getIdentifier(), p);
			rootUser.addEntitlement(p);
		}
	}
}
