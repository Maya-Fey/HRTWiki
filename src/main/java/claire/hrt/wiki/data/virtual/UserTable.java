package claire.hrt.wiki.data.virtual;

import claire.hrt.wiki.data.LoginReturn;
import claire.hrt.wiki.data.except.DuplicateKeyException;

/**
 * Table of users for authentication purposes
 * 
 * @author Claire
 */
public interface UserTable extends DataTable {
	
	void write(String username, char[] password) throws DuplicateKeyException;
	LoginReturn read(String username, char[] password);

}
