package cscie97.asn4.ecommerce.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cscie97.asn4.ecommerce.authentication.AccessDeniedException;
import cscie97.asn4.ecommerce.authentication.AuthenticationException;
import cscie97.asn4.ecommerce.authentication.AuthenticationService;
import cscie97.asn4.ecommerce.authentication.AuthenticationServiceException;
import cscie97.asn4.ecommerce.authentication.InvalidAccessTokenException;
import cscie97.asn4.ecommerce.authentication.PrintStringVisitor;
import cscie97.asn4.ecommerce.collection.Collectible;
import cscie97.asn4.ecommerce.collection.Collection;
import cscie97.asn4.ecommerce.collection.CollectionException;
import cscie97.asn4.ecommerce.collection.Iterator;
import cscie97.asn4.ecommerce.product.ContentAddException;

/**
 * The Class TestDriver.
 */
public class TestDriver {
	
	//usage: java TestDriver countries.csv devices.csv products.csv collections.csv
	/**
	 * The main method.
	 *
	 * @param args[0] authentication.csv 
	 * @param args[1] countries.csv 
	 * @param args[2] devices.csv 
	 * @param args[3] products.csv 
	 * @param args[4] collections.csv
	 */
	public static void main(String[] args) 
	{
		if (args.length < 1) {
			System.out.println("No files to process.");
		} else {
			String authenticationFile = args[0];
			authImport(authenticationFile);
			
			if (args.length >= 2) {
				String countriesFile = args[1];
				countryImport(countriesFile);
			}
			if (args.length >= 3) {
				String devicesFile = args[2];
				deviceInport(devicesFile);
			}
			if (args.length >= 4) {
				String productsFile = args[3];
				productImport(productsFile);
				
			}
			if (args.length >= 5) {
				String collectionFile = args[4];
				collectionImport(collectionFile);
			}
			
			AuthenticationService authservice = AuthenticationService.getAuthenticationService();
			String GUID = null;
			
			System.out.println();
			System.out.println("Testing: Success is silent, errors are printed.");
			System.out.println();
			System.out.println("Change the password of the root user.");
			try {
				authservice.changePassword("root", "rootpassword", "newpassword");
			} catch (AuthenticationException e) {
				System.out.println("Error: could not log in.");
			}
			
			System.out.println("Attempt to log in with old password.");
			try {
				GUID = authservice.login("root", "rootpassword");
			} catch (AuthenticationException e) {
				System.out.println("Error: could not log in.");
			}
			
			System.out.println("Log in with new password.");
			try {
				GUID = authservice.login("root", "newpassword");
			} catch (AuthenticationException e) {
				System.out.println("Error: could not log in.");
			}
			
			System.out.println("Log out.");
			try {
				authservice.logout(GUID);
			} catch (InvalidAccessTokenException e) {
				System.out.println("Invalid Access Token: " + e.getMessage());
			}
			
			System.out.println("Attempt to add a new service.");
			try {
				authservice.newService("service_id", "service_name", "service_description", GUID);
			} catch (InvalidAccessTokenException e) {
				System.out.println("Invalid Access Token: " + e.getMessage());
			} catch (AccessDeniedException e) {
				System.out.println("Access Denied: " + e.getMessage());
			} catch (AuthenticationServiceException e) {
				System.out.println("(Authentication Service error: " + e.getMessage());
			}
			System.out.println("Printing inventory of Authentication Service...");
			PrintStringVisitor v = new PrintStringVisitor();
			v.visitAuthService(authservice);
		}
	}

	public static void authImport(String authenticationFile) {
		
		System.out.println("Importing Authentication data...");
		
		AuthenticationService authservice = AuthenticationService.getAuthenticationService();
		String GUID = null;
		Scanner input = null;
		
		try {
			GUID = authservice.login("root", "rootpassword");
		} catch (AuthenticationException e) {
			System.out.println("Could not log in to root user.");
			return;
		}
		try {
			input = new Scanner(new File(authenticationFile));
			while (input.hasNextLine()) {
				String s = input.nextLine();
				if ((s.isEmpty() == false) && (s.charAt(0) != '#') && 
						(s.equalsIgnoreCase("") == false)) {
					try {
						Importer.importAuth(s, GUID);
					} catch (InvalidAccessTokenException e) {
						System.out.println("Invalid Access Token on " + s + ": " + e.getMessage());
					} catch (AccessDeniedException e) {
						System.out.println("Access Denied on " + s + ": " + e.getMessage());
					} catch (AuthenticationServiceException e) {
						System.out.println("(Authentication Service error on " + s + ": " + e.getMessage());
					} catch (ImportException e) {
						System.out.println("Error on import " + s + ": " + e.getMessage());
					}
				}
			}
			input.close();
		} catch (FileNotFoundException e1) {
			System.out.println("File " + authenticationFile + " not found");
		}
	}
	
	public static void countryImport(String countriesFile) {
		
		System.out.println("Importing Country data...");
		
		AuthenticationService authservice = AuthenticationService.getAuthenticationService();
		String GUID = null;
		Scanner input = null;
		
		try {
			GUID = authservice.login("sam", "secret");
		} catch (AuthenticationException e) {
			System.out.println("Could not log in to product admin.");
			return;
		}
		
		try {
			input = new Scanner(new File(countriesFile));
			while (input.hasNextLine())
			{
				String s = input.nextLine();
				if ((s.isEmpty() == false) && (s.charAt(0) != '#') && 
						(s.equalsIgnoreCase("") == false)) {
					try {
						Importer.importCountries(s, GUID);
					} catch (InvalidAccessTokenException e) {
						System.out.println("Invalid Access Token on " + s + ": " + e.getMessage());
					} catch (AccessDeniedException e) {
						System.out.println("Access Denied on " + s + ": " + e.getMessage());
					}
				}					
			}
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("File " + countriesFile + " not found.");
			return;
		}
	}
	
	public static void deviceInport(String devicesFile) {
		
		System.out.println("Importing Device data...");
		
		AuthenticationService authservice = AuthenticationService.getAuthenticationService();
		String GUID = null;
		Scanner input = null;
		
		try {
			GUID = authservice.login("sam", "secret");
		} catch (AuthenticationException e) {
			System.out.println("Could not log in to product admin.");
			return;
		}
		
		try {
			input = new Scanner(new File(devicesFile));
			while (input.hasNextLine())
			{
				String s = input.nextLine();
				if ((s.isEmpty() == false) && (s.charAt(0) != '#') && 
						(s.equalsIgnoreCase("") == false)) {
					try {
						Importer.importDevices(s, GUID);
					} catch (InvalidAccessTokenException e) {
						System.out.println("Invalid Access Token on " + s + ": " + e.getMessage());
					} catch (AccessDeniedException e) {
						System.out.println("Access Denied on " + s + ": " + e.getMessage());
					}
				}					
			}
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("File " + devicesFile + " not found.");
			return;
		}
	}
	
	public static void productImport(String productsFile) {
		
		System.out.println("Importing Product data...");
		
		AuthenticationService authservice = AuthenticationService.getAuthenticationService();
		String GUID = null;
		Scanner input = null;
		
		try {
			GUID = authservice.login("joe", "1234");
		} catch (AuthenticationException e) {
			System.out.println("Could not log in to product dev.");
			return;
		}
		try {
			input = new Scanner(new File(productsFile));
			while (input.hasNextLine())
			{
				String s = input.nextLine();
				if ((s.isEmpty() == false) && (s.charAt(0) != '#') && 
						(s.equalsIgnoreCase("") == false)) {
					try {
						try {
							Importer.importContent(s, GUID);
						} catch (InvalidAccessTokenException e) {
							System.out.println("Invalid Access Token on " + s + ": " + e.getMessage());
						} catch (AccessDeniedException e) {
							System.out.println("Access Denied on " + s + ": " + e.getMessage());
						}
					} catch (ContentAddException e) {
						System.out.println("Error adding content.");
					}
				}					
			}
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("File " + productsFile + " not found.");
			return;
		}
	}
	
	public static void collectionImport(String collectionFile) {
		
		System.out.println("Importing Collection data...");
		
		AuthenticationService authservice = AuthenticationService.getAuthenticationService();
		String GUID = null;
		Scanner input = null;
		try {
			GUID = authservice.login("lucy", "4567");
		} catch (AuthenticationException e) {
			System.out.println("Could not log in to product dev.");
			return;
		}
		try {
			input = new Scanner(new File(collectionFile));
			while (input.hasNextLine())
			{
				String s = input.nextLine();
				if ((s.isEmpty() == false) && (s.charAt(0) != '#') && 
						(s.equalsIgnoreCase("") == false)) {
					if (s.indexOf("search_collection") >= 0) {
						// send to a function
						//collectionSearch(s);
					} else {
						try {
							try {
								Importer.importCollection(s, GUID);
							} catch (InvalidAccessTokenException e) {
								System.out.println("Invalid Access Token on " + s + ": " + e.getMessage());
							} catch (AccessDeniedException e) {
								System.out.println("Access Denied on " + s + ": " + e.getMessage());
							}
						} catch (CollectionException e) {
							e.printStackTrace();
						} catch (ImportException e) {
							e.printStackTrace();
						}
					}
				}
			}
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("File " + collectionFile + " not found.");
			return;
		}
	}
	
	/**
	 * Collection search.
	 *
	 * @param s the search string
	 */
	public static void collectionSearch(String s) {
		Collection c = null;
		try {
			c = Importer.searchCollections(s);
		} catch (CollectionException e) {
			e.printStackTrace();
		}
		if (c != null) {
			System.out.println("Processing Collection search command: " + s);
			Iterator i = null;
			try {
				i = c.makeIterator();
			} catch (CollectionException e) {
				e.printStackTrace();
			}
			while (i.isDone() == false) {
				i.next();
				Collectible collectible = i.currentItem();
				if (collectible.isCollection()) {
					System.out.println("Collection ID: " + collectible.getIdentifier());
				} else {
					System.out.println("Product ID: " + collectible.getIdentifier());
				}
				try {
					System.out.println("Name: " + collectible.getName());
					System.out.println("Description: " + collectible.getDescription());
				} catch (CollectionException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
