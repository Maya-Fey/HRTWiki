/**
 * 
 */
package claire.hrt.wiki.data;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import claire.hrt.wiki.data.enumerate.UserRole;

/**
 * @author Claire
 */
public final class TestUser {

	/**
	 * Test persistence
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testPersistence()
	{
		User user = new User("username", "Display Name", "they/them", UserRole.EDITOR, new HashMap<String, String>());
		user = user.setProperty("testproperty", "My///complicated,,,property => has a value");
		String persist = user.persist();
		User resurrected = new User(persist);
		
	}
	
}
