/**
 * 
 */
package claire.hrt.wiki.data;

import java.util.HashMap;
import java.util.Map;

import claire.hrt.wiki.commons.except.PreconditionViolationException;
import claire.hrt.wiki.data.enumerate.UserRole;

/**
 * A user of the system
 * 
 * @author Claire
 */
public class User {
	
	private static final Map<String, String> PROPERTY_DEFAULTS = new HashMap<>();
	
	static {
		//Default properties go here
	}
	
	private final String username;
	
	private final String displayName;
	private final String pronouns;
	
	private final UserRole permissions;
	
	private final Map<String, String> properties;
	
	/**
	 * @param username
	 * @param displayName
	 * @param pronouns
	 * @param permissions
	 * @param properties
	 */
	User(String username, String displayName, String pronouns, UserRole permissions, Map<String, String> properties)
	{
		this.username = username;
		this.displayName = displayName;
		this.pronouns = pronouns;
		this.permissions = permissions;
		this.properties = properties;
	}

	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return this.username;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() 
	{
		return this.displayName;
	}

	/**
	 * @return the pronouns
	 */
	public String getPronouns() 
	{
		return this.pronouns;
	}

	/**
	 * @return the permissions
	 */
	public UserRole getPermissions() 
	{
		return this.permissions;
	}

	/**
	 * @return the properties
	 */
	public Map<String, String> getProperties() 
	{
		return this.properties;
	}
	
	/**
	 * @param propertyName
	 * @return The value of the property for this user, or the default if it is unset
	 */
	public String getProperty(String propertyName)
	{
		if(!PROPERTY_DEFAULTS.containsKey(propertyName))
			throw new PreconditionViolationException("No default defined for " + propertyName);
		if(this.properties.containsKey(propertyName))
			return this.properties.get(propertyName);
		return PROPERTY_DEFAULTS.get(propertyName);
	}

}
