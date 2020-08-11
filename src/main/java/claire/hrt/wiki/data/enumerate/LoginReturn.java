package claire.hrt.wiki.data.enumerate;

/**
 * The return for a login operation
 * 
 * @author Claire
 */
public enum LoginReturn {
	
	/**
	 * If the username is not found
	 */
	NO_SUCH_USER, 
	
	/**
	 * If the username is found, but the password isn't correct.
	 */
	INVALID_PASSWORD, 
	
	/**
	 * If the username was found, and the password is correct
	 */
	LOGIN_SUCCESS;

}
