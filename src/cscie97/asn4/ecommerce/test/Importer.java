package cscie97.asn4.ecommerce.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import cscie97.asn4.ecommerce.authentication.AccessDeniedException;
import cscie97.asn4.ecommerce.authentication.AuthenticationService;
import cscie97.asn4.ecommerce.authentication.AuthenticationServiceException;
import cscie97.asn4.ecommerce.authentication.InvalidAccessTokenException;
import cscie97.asn4.ecommerce.collection.Collectible;
import cscie97.asn4.ecommerce.collection.Collection;
import cscie97.asn4.ecommerce.collection.CollectionException;
import cscie97.asn4.ecommerce.collection.CollectionService;
import cscie97.asn4.ecommerce.product.ContentAddException;
import cscie97.asn4.ecommerce.product.Query;
import cscie97.asn4.ecommerce.product.Store;

/**
 * Imports Devices/Countries/Items/Collections and runs queries.
 */
public class Importer {

	private static CollectionService collectionservice;
	
	/**
	 * Import content.
	 *
	 * @param s the s
	 * @throws ContentAddException the content add exception
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 * @throws AuthenticationException the authentication exception
	 */
	public static void importContent(String s, String GUID) throws ContentAddException, InvalidAccessTokenException, AccessDeniedException
	{
		Store store = Store.getStore();
				
		String[] tokens = splitCSV(s);
			if (tokens.length >= 12)
			{
				String content_type = tokens[0]; 
				String content_id = tokens[1]; 
				String content_name = tokens[2]; 
				String content_description = tokens[3]; 
				String author = tokens[4]; 
				int rating = Integer.parseInt(tokens[5].trim());
				Set<String> categories = splitPipes(tokens[6]);
				Set<String> export_countries = splitPipes(tokens[7]);
				Set<String> supported_devices = splitPipes(tokens[8]);
				float price = Float.parseFloat(tokens[9].trim());
				Set<String> supported_languages = splitPipes(tokens[10]);
				String image_url = tokens[11]; 

				if ((content_type.equalsIgnoreCase("application") == true) && tokens.length >= 13)
				{
					int application_size = Integer.parseInt(tokens[12]);
					store.addItem(content_type, content_id, content_name,
									  content_description, author, rating,
									  categories, export_countries, 
									  supported_devices, price, supported_languages,
									  image_url, application_size, GUID);
				}
				else
				{
						store.addItem(content_type, content_id, content_name,
									  content_description, author, rating,
								      categories, export_countries, 
								      supported_devices, price, supported_languages,
								      image_url, GUID);	
				}
			}

	}
	
	/**
	 * Import devices.
	 *
	 * @param s the s
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 * @throws AuthenticationException the authentication exception
	 */
	public static void importDevices(String s, String GUID) throws InvalidAccessTokenException, AccessDeniedException
	{
		Store store = Store.getStore();
		
			String[] tokens = splitCSV(s);
			if (tokens.length >= 3)
			{
				store.addDevice(tokens[0], tokens[1], tokens[2], GUID);
			}
	}
			
	/**
	 * Import countries.
	 *
	 * @param s the s
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 * @throws AuthenticationException the authentication exception
	 */
	public static void importCountries(String s, String GUID) throws InvalidAccessTokenException, AccessDeniedException
	{
		Store store = Store.getStore();
		
			String[] tokens = splitCSV(s);
			if (tokens.length >= 3)
			{
				boolean open = false;
				if (tokens[2].equalsIgnoreCase("open")) open = true;
				store.addCountry(tokens[0], tokens[1], open, GUID);
			}
	}		
	
	/**
	 * Import collection.
	 *
	 * @param s the s
	 * @throws CollectionException the collection exception
	 * @throws ImportException the import exception
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 */
	public static void importCollection(String s, String GUID) throws CollectionException, ImportException, InvalidAccessTokenException, AccessDeniedException
	{
		collectionservice = CollectionService.getCollectionService();
		
			String[] tokens = splitCSV(s);

			// define_collection, <collection_type>, <collection_id>, <collection_name>, <collection_description>
			if ((tokens.length >= 5) && (tokens[0].equalsIgnoreCase("define_collection")))
			{
				collectionservice.newCollection(tokens[1], tokens[2], tokens[3], tokens[4], GUID);
			}
			
			// add_collection_content, <collection_id>, <content_type>, <content_id>
			else if ((tokens.length >= 4) && (tokens[0].equalsIgnoreCase("add_collection_content")))
			{
				addCollectionContent(s, GUID);
			}
			
			// set_dynamic_criteria, <collection_id>, <category list>, <text search>, <minimum rating>, 
			// <max price>, <language list>, <country code>, <device id>, <content type list>
			else if ((tokens.length >= 9) && (tokens[0].equalsIgnoreCase("set_dynamic_criteria")))
			{
				setDynamicCriteria(s, GUID);
			}
			else {
				throw new ImportException(s, "Requested action could not be found.");
			}				
	}
	
	public static void importAuth(String s, String GUID) throws InvalidAccessTokenException, AccessDeniedException, AuthenticationServiceException, ImportException {
		
		String[] tokens = splitCSV(s);
		AuthenticationService authservice = AuthenticationService.getAuthenticationService();

		// define_service, <service_id>, <service_name>, <service_description>
		if ((tokens.length >= 4) && (tokens[0].equalsIgnoreCase("define_service"))) {
			authservice.newService(tokens[1], tokens[2], tokens[3], GUID);
		}
		
		// define_permission, <service_id>, <permission_id>, <permission_name>, <permission_description>
		else if ((tokens.length >= 5) && (tokens[0].equalsIgnoreCase("define_permission"))) {
			authservice.newPermission(tokens[2], tokens[3], tokens[4], tokens[1], GUID);
		}
		// define_role, <role_id>, <role_name>, <role_description>
		else if ((tokens.length >= 4) && (tokens[0].equalsIgnoreCase("define_role"))) {
			authservice.newRole(tokens[1], tokens[2], tokens[3], GUID);
		}
		// add_entitlement_to_role, <role_id>, <entitlement_id>
		else if ((tokens.length >= 3) && (tokens[0].equalsIgnoreCase("add_entitlement_to_role"))) {
			authservice.addEntitlementToRole(tokens[2], tokens[1], GUID);
		}
		// create_user <user_id>, <user_name>, <login_name>, <password>
		else if ((tokens.length >= 4) && (tokens[0].equalsIgnoreCase("create_user"))) {
			authservice.newUser(tokens[1], tokens[2], tokens[3], tokens[4], GUID);
		}
		// add_entitlement_to_user,<userid>,<entitlementid>
		else if ((tokens.length >= 3) && (tokens[0].equalsIgnoreCase("add_entitlement_to_user"))) {
			authservice.addEntitlementToUser(tokens[1], tokens[2], GUID);
		} else {
			throw new ImportException(s, "Requested action could not be found.");
		}
	}
	
	
	
	/**
	 * Search collections.
	 *
	 * @param s the s
	 * @return the collection
	 * @throws CollectionException 
	 */
	public static Collection searchCollections(String s) throws CollectionException {
		String[] tokens = splitCSV(s);
		Collection collection = null;
		if ((tokens.length >= 2) && (tokens[0].equalsIgnoreCase("search_collection"))) {
			collection = collectionservice.searchCollections(tokens[1]);
		} else collection = collectionservice.searchCollections("");
		return collection;	
	}
	
	/**
	 * Adds the collection content.
	 *
	 * @param s the s
	 * @param GUID the guid
	 * @throws ImportException the import exception
	 * @throws CollectionException the collection exception
	 * @throws AuthenticationServiceException 
	 * @throws AccessDeniedException 
	 * @throws InvalidAccessTokenException 
	 */
	private static void addCollectionContent(String s, String GUID) throws ImportException, CollectionException, InvalidAccessTokenException, AccessDeniedException {
		
		String[] tokens = splitCSV(s);
		Collectible collection = null;
		
		collection = collectionservice.getItem(tokens[1]);
		if (collection == null) {
			throw new ImportException(s, "Collection could not be found.");
		} else if (collection.isCollection() == false) { 
			throw new ImportException(s, "Item matching identifier is not a collection.");
		} else {
			Collectible addition;
			addition = collectionservice.getItem(tokens[3]);
			if (addition == null) {
				if (tokens[2].equalsIgnoreCase("collection")) {
					// the collection we are trying to add does not exist
					throw new ImportException(s, "Collection could not be found.");
				} else if (tokens[2].equalsIgnoreCase("product")) {
					// create product
					addition = collectionservice.newProduct(tokens[3]);
				}
			}
			// add addition to collection
			collection.add(addition, GUID);
		}
	}
	
	/**
	 * Sets the dynamic criteria.
	 *
	 * @param s the s
	 * @param GUID the guid
	 * @throws ImportException the import exception
	 * @throws CollectionException 
	 */
	private static void setDynamicCriteria(String s, String GUID) throws ImportException, CollectionException {
		
		String[] tokens = splitCSV(s);
		Collectible collection = null;
		
		collection = collectionservice.getItem(tokens[1]);
		if (collection == null) {
			throw new ImportException(s, "Collection could not be found.");
		} else if (collection.isCollection() == false) { 
			throw new ImportException(s, "Item matching identifier is not a collection.");
		} else {
			Query query = new Query();
			
			// catgory list
			if (tokens[2].equals("") == false) query.setCategories(splitPipes(tokens[2]));
			
			//text search
			query.setTextSearch(tokens[3]);

			//minimum rating 
			if (tokens[4].equals("") == false) query.setMinRating(Integer.parseInt(tokens[4]));
			
			// max price
			if (tokens[5].isEmpty() == false) query.setMaxPrice(Float.parseFloat(tokens[5]));
			
			//language list
			query.setLanguages(splitPipes(tokens[6]));
			
			// country code, 
			query.setCountry(splitPipes(tokens[7]));
			
			//device id, 
			query.setDevice(splitPipes(tokens[8]));
			
			//content type list
			if (tokens.length > 9) query.setType(splitPipes(tokens[9]));
			
			collection.addQuery(query);
		}
		
	}
	
	
	/**
	 * Conventience method to split pipe-delimited Strings into a Set 
	 * of their substrings.
	 *
	 * @param s the string
	 * @return set of substrings
	 */
	private static Set<String> splitPipes(String s)
	{
		if (s.equalsIgnoreCase("") == false)
		{
			String[] tokens = s.split("\\|");
			return new HashSet<String>(Arrays.asList(tokens));
		}
		else return new HashSet<String>();
	}
	
	/**
	 * Conventience method to split csv Strings into an array 
	 * of trimmed substrings.
	 *
	 * @param s the string
	 * @return the array of substrings
	 */
	private static String[] splitCSV(String s)
	{
		
		String csvdelims = "(?<![^\\\\]\\\\),";
		String[] rawtokens = s.split(csvdelims);
		String[] tokens = new String[rawtokens.length];
		for (int i = 0; i < rawtokens.length; i++) {
			tokens[i] = rawtokens[i].trim();
		}
		return tokens;
		
	}
}
