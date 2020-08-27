/**
 * 
 */
package claire.hrt.wiki.data;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.annotation.Nullable;

import claire.hrt.wiki.commons.Null;
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
		PROPERTY_DEFAULTS.put("testproperty", "test");
	}
	
	private final String username;
	
	private final String displayName;
	private final String pronouns;
	
	private final UserRole permissions;
	
	private final Map<String, String> properties;
	
	/**
	 * @param persisted
	 */
	User(String persisted)
	{
		Map<String, String> reconstituted = DataHelper.mapFromString(persisted);
		this.username = reconstituted.get("username");
		this.displayName = reconstituted.get("displayName");
		this.pronouns = reconstituted.get("pronouns");
		this.permissions = UserRole.fromInt(Integer.parseInt(reconstituted.get("permissions")));
		this.properties = DataHelper.mapFromString(reconstituted.get("properties"));
	}
	
	/**
	 * @param username
	 * @param displayName
	 * @param pronouns
	 * @param permissions
	 * @param properties
	 */
	public User(String username, String displayName, String pronouns, UserRole permissions, Map<String, String> properties)
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
	 * Returns this user with a new name
	 * 
	 * @param name The new name for this user
	 * @return This user with the changed name
	 */
	public User changeName(String name)
	{
		return new User(this.username, name, this.pronouns, this.permissions, this.properties);
	}
	
	/**
	 * Returns this user with new pronouns
	 * 
	 * @param newpronouns The new pronouns for this user
	 * @return This user with the changed pronouns
	 */
	public User changePronouns(String newpronouns)
	{
		return new User(this.username, this.displayName, newpronouns, this.permissions, this.properties);
	}
	
	/**
	 * Returns this user with a new role
	 * 
	 * @param role The new role for this user
	 * @return This user with the changed role
	 */
	public User changeRole(UserRole role)
	{
		return new User(this.username, this.displayName, this.pronouns, role, this.properties);
	}
	
	/**
	 * @param key The property key
	 * @param value The property value
	 * @return This user with the property set
	 */
	public User setProperty(String key, String value)
	{
		if(!PROPERTY_DEFAULTS.containsKey(key))
			throw new PreconditionViolationException("No default defined for " + key);
		Map<String, String> nproperties = new HashMap<>(this.properties);
		nproperties.put(key, value);
		return new User(this.username, this.displayName, this.pronouns, this.permissions, nproperties);
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
	
	/**
	 * @return A string representation of this object capable of resurrecting it
	 */
	public String persist()
	{
		Map<String, String> map = new HashMap<>();
		map.put("username", this.username);
		map.put("displayName", this.displayName);
		map.put("pronouns", this.pronouns);
		map.put("permissions", Null.nonNull(Integer.toString(this.permissions.ordinal())));
		map.put("properties", DataHelper.mapToString(this.properties));
		return DataHelper.mapToString(map);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.displayName.hashCode();
		result = prime * result + this.permissions.hashCode();
		result = prime * result + this.pronouns.hashCode();
		result = prime * result + this.properties.hashCode();
		result = prime * result + this.username.hashCode();
		return result;
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if(!this.displayName.equals(other.displayName)) {
			return false;
		}
		if(this.permissions != other.permissions) {
			return false;
		}
		if(!this.pronouns.equals(other.pronouns)) {
			return false;
		}
		if(!this.properties.equals(other.properties)) {
			return false;
		}
		if(!this.username.equals(other.username)) {
			return false;
		}
		return true;
	}

}
