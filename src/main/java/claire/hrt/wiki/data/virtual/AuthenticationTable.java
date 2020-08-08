package claire.hrt.wiki.data.virtual;

import claire.hrt.wiki.data.LoginReturn;
import claire.hrt.wiki.data.except.DuplicateKeyException;
import claire.hrt.wiki.data.except.NoSuchKeyException;

/**
 * Table of users for authentication purposes
 * 
 * @author Claire
 */
public interface AuthenticationTable extends DataTable {
	
	boolean exists(String username);
	void write(String username, char[] password) throws DuplicateKeyException;
	void overwrite(String username, char[] password) throws NoSuchKeyException;
	LoginReturn read(String username, char[] password);

}
