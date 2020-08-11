/**
 * 
 */
package claire.hrt.wiki.data.enumerate;

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
		assertFalse(UserRole.ADMIN.hasAtLeast(UserRole.ROOT));
		assertTrue(UserRole.ADMIN.hasAtLeast(UserRole.ADMIN));
		assertTrue(UserRole.ADMIN.hasAtLeast(UserRole.EDITOR));
		assertFalse(UserRole.EDITOR.hasAtLeast(UserRole.ROOT));
		assertFalse(UserRole.EDITOR.hasAtLeast(UserRole.ADMIN));
		assertTrue(UserRole.EDITOR.hasAtLeast(UserRole.EDITOR));
	}

}
