package claire.hrt.wiki.data.virtual;

import claire.hrt.wiki.data.enumerate.LoginReturn;
import claire.hrt.wiki.data.except.DuplicateKeyException;
import claire.hrt.wiki.data.except.NoSuchKeyException;

/**
 * Table of users for authentication purposes
 * 
 * @author Claire
 */
public interface AuthenticationTable extends DataRecord {
	
	/**
	 * @param username A username as a string
	 * @return Whether this username exists in the authentication table
	 */
	boolean exists(String username);
	
	/**
	 * Writes a new user to the authentication table using a raw password.
	 * 
	 * @param username A username that does not already exist
	 * @param salt The salt to go with the username
	 * @param password A password as a 256-bit PBKDF2 value
	 * @throws DuplicateKeyException If the given username already exists in the table
	 */
	void writeRaw(String username, byte[] salt, char[] password) throws DuplicateKeyException;
	
	/**
	 * Writes a new user to the authentication table
	 * 
	 * @param username A username that does not already exist
	 * @param password A password
	 * @throws DuplicateKeyException If the given username already exists in the table
	 */
	void write(String username, char[] password) throws DuplicateKeyException;
	
	/**
	 * Overwrites the password of a user already present in the table
	 * 
	 * @param username A valid username
	 * @param password The new password you'd like to associate with it
	 * @throws NoSuchKeyException If the username does not already exist within the table
	 */
	void overwrite(String username, char[] password) throws NoSuchKeyException;
	
	/**
	 * Confirms that the relevant username-password pair is valid
	 * 
	 * @param username A username
	 * @param password A password 
	 * @return The status of a login operation on the username-password pair
	 */
	LoginReturn read(String username, char[] password);

}
