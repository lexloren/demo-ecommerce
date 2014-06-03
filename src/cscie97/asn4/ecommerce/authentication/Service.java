package cscie97.asn4.ecommerce.authentication;

/**
 * The Service class.
 */
public class Service {

	private String identifier;
	private String name;
	private String description;
	
	
	/**
	 * Instantiates a new service object.
	 *
	 * @param identifier the identifier
	 * @param name the name
	 * @param description the description
	 */
	public Service(String identifier, String name, String description) {
		this.identifier = identifier;
		this.name = name;
		this.description = description;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void AcceptVisitor(AuthVisitor v) {
		v.visitService(this);
	}
}
