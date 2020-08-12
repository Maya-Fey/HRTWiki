/**
 * 
 */
package claire.hrt.wiki.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import claire.hrt.wiki.commons.except.PreconditionViolationException;
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
		assertEquals(user, resurrected);
	}
	
	/**
	 * Test property checking
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testNoInvalidProperty()
	{
		User user = new User("username", "Display Name", "they/them", UserRole.EDITOR, new HashMap<String, String>());
		assertThrows(PreconditionViolationException.class, () -> { user.setProperty("badproperty", ""); });
		assertThrows(PreconditionViolationException.class, () -> { user.getProperty("badproperty"); });
	}
	
	/**
	 * Test that the object is immutable
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testImmutability()
	{
		User user = new User("username", "Display Name", "they/them", UserRole.EDITOR, new HashMap<String, String>());
		User user2 = user.changeName("Other Name");
		assertNotEquals(user, user2);
		assertFalse(user == user2);
		user = user2.changePronouns("he/him");
		assertNotEquals(user, user2);
		assertFalse(user == user2);
		user2 = user.changeRole(UserRole.ADMIN);
		assertNotEquals(user, user2);
		assertFalse(user == user2);
		user = user2.setProperty("testproperty", "testvalue");
		assertNotEquals(user, user2);
		assertFalse(user == user2);
	}
	
	/**
	 * Test that the object is immutable
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetters()
	{
		User user = new User("username", "Display Name", "they/them", UserRole.EDITOR, new HashMap<String, String>());
		assertEquals("username", user.getUsername());
		assertEquals("Display Name", user.getDisplayName());
		assertEquals("they/them", user.getPronouns());
		assertEquals(UserRole.EDITOR, user.getPermissions());
	}
	
	/**
	 * Test that property getters work as expected
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testPropertyGetters()
	{
		User user = new User("username", "Display Name", "they/them", UserRole.EDITOR, new HashMap<String, String>());
		assertEquals("test", user.getProperty("testproperty"));
		user = user.setProperty("testproperty", "value");
		assertEquals("value", user.getProperty("testproperty"));
	}
	
}
