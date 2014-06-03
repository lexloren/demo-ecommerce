package cscie97.asn4.ecommerce.authentication;

import java.util.Date;
import java.util.UUID;

/**
 * The AccessToken.
 * 
 * The AccessToken object contains both a String access token/GUID and 
 * 	its associated metadata. Note that the AccessToken does not actually 
 * 	store a list of permissions. Rather, they store a reference to the 
 * 	owning user. When the token is used, the user's permissions are 
 * 	checked dynamically. 
 *
 */
public class AccessToken {

	private String identifier;
	private Date lastUsed;
	private User user;
	private boolean active;
	
	/* the token's lifetime in milliseconds */
	private static final long lifetime = 600000;
		
	/**
	 * Instantiates a new access token.
	 *
	 * @param u the user
	 */
	public AccessToken(User u) {
		this.lastUsed = new Date();
		this.active = true;
		this.user = u;
		this.identifier = UUID.randomUUID().toString();
	}
	
	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive(){
		if (active == false) return false;
		Date now = new Date();
		if ((now.getTime() - lastUsed.getTime()) > lifetime) {
			active = false;
			return false;
		}
		return true;
	}
	
	/**
	 * Checks whether the token grants the given permission.
	 *
	 * @param permissionid the permission
	 * @return true, if successful
	 * @throws InvalidAccessTokenException 
	 */
	public boolean grantsPermission(String permissionid) throws InvalidAccessTokenException {
		if (this.isActive()== false) throw new InvalidAccessTokenException("Access Token expired.");
		try {
			return user.hasPermission(permissionid);
		} catch (AuthenticationServiceException e) {
			return false;
		}
	}
	
	/**
	 * Marks the token as inactive. (AKA logs the user out.)
	 */
	public void invalidate() {
		active = false;
	}

	public String getIdentifier() {
		return identifier;
	}

}
