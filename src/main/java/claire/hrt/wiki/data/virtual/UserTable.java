package claire.hrt.wiki.data.virtual;

import claire.hrt.wiki.data.User;
import claire.hrt.wiki.data.except.DuplicateKeyException;
import claire.hrt.wiki.data.except.NoSuchKeyException;

/**
 * A table for users
 * 
 * @author Claire
 */
public interface UserTable extends DataRecord {
	
	/**
	 * @param username A valid username
	 * @return The user associated with that username
	 * @throws NoSuchKeyException If the username is not present in the database
	 */
	User getUserByName(String username) throws NoSuchKeyException;
	
	/**
	 * @param user The user you'd like to add
	 * @throws DuplicateKeyException If a user with that username is already in the table
	 */
	void addUser(User user) throws DuplicateKeyException;
	
	/**
	 * Updates a user using a given username
	 * 
	 * @param user The user you'd like to replace
	 * @throws NoSuchKeyException If that user is not in the table
	 */
	void updateUser(User user) throws NoSuchKeyException;

}
