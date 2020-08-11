package claire.hrt.wiki.data.enumerate;

import claire.hrt.wiki.commons.Null;

/**
 * The role of a user, which specifies their permission level
 * 
 * @author Claire
 */
public enum UserRole {
	
	/**
	 * Root user, can do all that admins can plus create or deperm other admins, as well as other special actions
	 */
	ROOT(Integer.MAX_VALUE), 
	
	/**
	 * Admin, can do all that editors can plus access the raw site admin interface as well as create or deperm other editors
	 */
	ADMIN(10000), 
	
	/**
	 * Can edit pages
	 */
	EDITOR(1000),
	
	/**
	 * Banned
	 */
	BANNED(Integer.MIN_VALUE);
	
	/**
	 * The raw permission value of this role
	 */
	public final int permissionLevel;
	
	private UserRole(int permissionLevel)
	{
		this.permissionLevel = permissionLevel;
	}
	
	/**
	 * @param role The minimum permissions required
	 * @return Whether the user has the permissions of the given role, or more permissions.
	 */
	public boolean hasAtLeast(UserRole role)
	{
		return this.permissionLevel >= role.permissionLevel;
	}
	
	private static final UserRole[] roles = UserRole.values();
	/**
	 * @param i A UserRole ordinal
	 * @return The UserRole represented by the ordinal
	 */
	public static UserRole fromInt(int i)
	{
		return Null.nonNull(roles[i]);
	}
	
}
