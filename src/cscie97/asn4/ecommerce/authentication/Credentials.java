package cscie97.asn4.ecommerce.authentication;

/**
 * The Credentials class.
 */
public class Credentials {

	/** The username. */
	private String username;
	
	/** The password. */
	private String password;
	
	/**
	 * Instantiates a new credentials.
	 *
	 * @param username the username
	 * @param password the password
	 */
	public Credentials(String username, String password) {
		this.username = username;
		this.password = hashPassword(password);
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
		if (username.equalsIgnoreCase(this.username) == false) return false;
		else if (this.password.equals(hashPassword(password)) == true) return true;
		else throw new AuthenticationException("Username or password do not match.");
	}
	
	/**
	 * Creates a horrifically insecure hash of the password.
	 *
	 * @param password the password
	 * @return the hashed password
	 */
	private String hashPassword(String password) {
		return Integer.toString(password.hashCode());
	}

	/**
	 * New password.
	 *
	 * @param password the password
	 */
	public void newPassword(String password) {
		this.password = hashPassword(password);
	}
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
}
