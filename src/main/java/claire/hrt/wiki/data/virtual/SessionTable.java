/**
 * 
 */
package claire.hrt.wiki.data.virtual;

import claire.hrt.wiki.data.Session;
import claire.hrt.wiki.data.except.NoSuchKeyException;

/**
 * An interface for the session table.
 * 
 * The session table contains all active sessions, as well as records sessions.
 * 
 * @author Claire
 */
public interface SessionTable extends DataRecord {
	
	/**
	 * @return A brand new session with a unique ID
	 */
	Session newSession();
	
	/**
	 * @param id The session ID
	 * @return Whether or not the ID represents a valid session
	 */
	boolean isActiveSession(String id);
	
	/**
	 * @param id The session ID
	 * @return The session represented by the ID
	 * @throws NoSuchKeyException is the session ID does not represent a valid session
	 */
	Session getSessionByID(String id) throws NoSuchKeyException;

}
