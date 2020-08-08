package claire.hrt.wiki.data.virtual;

import claire.hrt.wiki.data.LoginReturn;

/**
 * Table of users for authentication purposes
 * 
 * @author Claire
 */
public interface UserTable extends DataTable {
	
	void write(String username, char[] password);
	LoginReturn read(String username, char[] password);

}
