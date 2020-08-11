/**
 * 
 */
package claire.hrt.wiki.data.enumerate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author Claire
 */
public class TestUserRole {
	
	/**
	 * Test user role relative permission levels
	 */
	@Test
	@SuppressWarnings("static-method")
	public void testUserRoles()
	{
		assertTrue(UserRole.ROOT.hasAtLeast(UserRole.ROOT));
		assertTrue(UserRole.ROOT.hasAtLeast(UserRole.ADMIN));
		assertTrue(UserRole.ROOT.hasAtLeast(UserRole.EDITOR));
		assertTrue(UserRole.ROOT.hasAtLeast(UserRole.BANNED));
		assertFalse(UserRole.ADMIN.hasAtLeast(UserRole.ROOT));
		assertTrue(UserRole.ADMIN.hasAtLeast(UserRole.ADMIN));
		assertTrue(UserRole.ADMIN.hasAtLeast(UserRole.EDITOR));
		assertTrue(UserRole.ADMIN.hasAtLeast(UserRole.BANNED));
		assertFalse(UserRole.EDITOR.hasAtLeast(UserRole.ROOT));
		assertFalse(UserRole.EDITOR.hasAtLeast(UserRole.ADMIN));
		assertTrue(UserRole.EDITOR.hasAtLeast(UserRole.EDITOR));
		assertTrue(UserRole.EDITOR.hasAtLeast(UserRole.BANNED));
	}

	/**
	 * Tests persistence and resurrection
	 */
	@Test
	@SuppressWarnings("static-method")
	public void testPersistence()
	{
		for(UserRole role : UserRole.values())
			assertEquals(role, UserRole.fromInt(role.ordinal()));
	}
}
