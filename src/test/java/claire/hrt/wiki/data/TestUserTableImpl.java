/**
 * 
 */
package claire.hrt.wiki.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import claire.hrt.wiki.data.enumerate.UserRole;
import claire.hrt.wiki.data.except.DuplicateKeyException;
import claire.hrt.wiki.data.except.NoSuchKeyException;
import claire.hrt.wiki.data.virtual.DataContext;
import claire.hrt.wiki.data.virtual.UserTable;

/**
 * @author Claire
 */
public class TestUserTableImpl {
	
	/**
	 * Tests a basic read and write
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testReadWrite()
	{
		UserTable table = new UserTableImpl();
		User user = new User("username", "Display Name", "they/them", UserRole.EDITOR, new HashMap<String, String>());
		try {
			table.addUser(user);
		} catch (DuplicateKeyException e) {
			fail(e);
		}
		User res = null;
		try {
			res = table.getUserByName("username");
		} catch (NoSuchKeyException e) {
			fail(e);
		}
		assertEquals(user, res);
	}
	
	/**
	 * Tests that duplicate writes fail
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testNoDupes()
	{
		UserTable table = new UserTableImpl();
		User user = new User("username", "Display Name", "they/them", UserRole.EDITOR, new HashMap<String, String>());
		User user2 = new User("username", "Display Name", "they/them", UserRole.EDITOR, new HashMap<String, String>());
		try {
			table.addUser(user);
		} catch (DuplicateKeyException e) {
			fail(e);
		}
		assertThrows(DuplicateKeyException.class, () -> { table.addUser(user2); });
	}
	
	/**
	 * Tests that bad reads throw an exception
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testNoBadReads()
	{
		UserTable table = new UserTableImpl();
		assertThrows(NoSuchKeyException.class, () -> { table.getUserByName("username"); });
		assertThrows(NoSuchKeyException.class, () -> { table.updateUser(new User("username", "Display Name", "they/them", UserRole.EDITOR, new HashMap<String, String>())); });
	}
	
	/**
	 * Tests an update
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testUpdate()
	{
		UserTable table = new UserTableImpl();
		User user = new User("username", "Display Name", "they/them", UserRole.EDITOR, new HashMap<String, String>());
		try {
			table.addUser(user);
		} catch (DuplicateKeyException e) {
			fail(e);
		}
		User res = null;
		try {
			res = table.getUserByName("username");
		} catch (NoSuchKeyException e) {
			fail(e);
		}
		assertEquals(user, res);
		user = user.changeName("Hello there").changePronouns("she/her").changeRole(UserRole.ADMIN);
		try {
			table.updateUser(user);
			res = table.getUserByName("username");
		} catch (NoSuchKeyException e) {
			fail(e);
		}
		assertEquals(user, res);
	}
	
	/**
	 * Test persistence
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testPersistence()
	{
		try {
			DataContext context = DataTestHelper.TEST_DATA_CONTEXT;
			UserTable table = new UserTableImpl();
			assertFalse(table.isInitialized());	
			User user1 = new User("User1", "Display One", "they/them", UserRole.EDITOR, new HashMap<String, String>()).setProperty("testproperty", "test///value,,,thing");
			User user2 = new User("User2", "Display Two", "she/her", UserRole.BANNED, new HashMap<String, String>());
			table.addUser(user1);
			table.addUser(user2);
			table.flush(context);
			table = new UserTableImpl();
			table.initialize(context);
			assertTrue(table.isInitialized());
			assertEquals(user1, table.getUserByName("User1"));
			assertEquals(user2, table.getUserByName("User2"));
		} catch(Exception e) {
			fail(e);
		}
	}

}
