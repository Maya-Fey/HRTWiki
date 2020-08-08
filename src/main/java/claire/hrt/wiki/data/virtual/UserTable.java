package claire.hrt.wiki.data.virtual;

/**
 * Table of users for authentication purposes
 * 
 * @author Claire
 */
public interface UserTable extends DataTable {
	
	void write(String username, String password);
	boolean read(String username, String password);

}
