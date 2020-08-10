/**
 * 
 */
package claire.hrt.wiki.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import claire.hrt.wiki.commons.Null;
import claire.hrt.wiki.data.except.DuplicateKeyException;
import claire.hrt.wiki.data.except.NoSuchKeyException;
import claire.hrt.wiki.data.virtual.AuthenticationTable;

/**
 * Tests authentication table
 * 
 * @author Claire
 */
public class TestAuthenticationTableImpl {

	/**
	 * Tests that registration functions as expected
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRegisterAndLogin()
	{
		AuthenticationTable auth = new AuthenticationTableImpl();
		try {
			auth.write("Claire", Null.nonNull("clairespassword".toCharArray()));
		} catch (DuplicateKeyException e) { fail(e); }
		assertEquals(LoginReturn.LOGIN_SUCCESS, auth.read("Claire", Null.nonNull("clairespassword".toCharArray())));
	}
	
	/**
	 * Tests that the system detects non-existent users
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testBadUsername()
	{
		AuthenticationTable auth = new AuthenticationTableImpl();
		assertEquals(LoginReturn.NO_SUCH_USER, auth.read("Claire", Null.nonNull("clairespassword".toCharArray())));
		try {
			auth.write("Claire", Null.nonNull("clairespassword".toCharArray()));
		} catch (DuplicateKeyException e) { fail(e); }
		assertThrows(NoSuchKeyException.class, () -> { auth.overwrite("Claire2", Null.nonNull("clairespassword".toCharArray())); });
	}
	
	/**
	 * Tests that the system detects incorrect passowords
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testBadPassword()
	{
		AuthenticationTable auth = new AuthenticationTableImpl();
		try {
			auth.write("Claire", Null.nonNull("clairespassword".toCharArray()));
		} catch (DuplicateKeyException e) { fail(e); }
		assertEquals(LoginReturn.INVALID_PASSWORD, auth.read("Claire", Null.nonNull("badpassword".toCharArray())));
	}
	
	/**
	 * Tests that adding a same user twice is disallowed
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testDoubleWriteDisallow()
	{
		AuthenticationTable auth = new AuthenticationTableImpl();
		try {
			auth.write("Claire", Null.nonNull("clairespassword".toCharArray()));
		} catch (DuplicateKeyException e) { fail(e); }
		assertThrows(DuplicateKeyException.class, () -> { auth.write("Claire", Null.nonNull("clairespassword".toCharArray())); });
	}
	
	/**
	 * Tests that overwrite functions as expected
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testOverwrite()
	{
		AuthenticationTable auth = new AuthenticationTableImpl();
		try {
			auth.write("Claire", Null.nonNull("clairespassword".toCharArray()));
		} catch (DuplicateKeyException e) { fail(e); }
		try {
			auth.overwrite("Claire", Null.nonNull("anotherpassword".toCharArray()));
		} catch (NoSuchKeyException e) { fail(e); }
		assertEquals(LoginReturn.LOGIN_SUCCESS, auth.read("Claire", Null.nonNull("anotherpassword".toCharArray())));
	}
	
	
}
