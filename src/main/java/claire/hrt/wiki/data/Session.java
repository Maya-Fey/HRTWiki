/**
 * 
 */
package claire.hrt.wiki.data;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.annotation.Nullable;

import claire.hrt.wiki.commons.Null;
import claire.hrt.wiki.commons.except.PreconditionViolationException;

/**
 * An object that represents a session. Contains relevant data about the session
 * and any logged in user.
 * 
 * Mutable.
 * 
 * @author Claire
 */
public class Session {
	
	private final String key;
	
	private final Map<String, Object> properties = new HashMap<>();
	
	private long lastUsed, expiresAfter = -1;
	
	@Nullable 
	private User user;
	
	/**
	 * @param key
	 */
	public Session(String key)
	{
		this.lastUsed = System.currentTimeMillis();
		this.key = key;
	}
	
	/**
	 * @return The session key
	 */
	public String getKey()
	{
		return this.key;
	}
	
	/**
	 * @return The last time this session was used to load a page
	 */
	public long getLastUsed()
	{
		return this.lastUsed;
	}
	
	/**
	 * Updates the last used value of this session.
	 */
	public void use()
	{
		this.revalidate();
		this.lastUsed = System.currentTimeMillis();
	}
	
	/**
	 * @return Whether this session has an associated user.
	 */
	public boolean isAuthenticated()
	{
		this.revalidate();
		return this.user != null;
	}
	
	/**
	 * @return The user associated with this session, if it is authenticated
	 * @throws PreconditionViolationException If this is an unauthenticated session. (use
	 * <code>isAuthenticated()</code> first before calling)
	 */
	public User getUser()
	{
		this.revalidate();
		if(this.user == null)
			throw new PreconditionViolationException("This session has not been authenticated and has no associated user.");
		return Null.nonNull(this.user);
	}
	
	/**
	 * Sets this session as an authenticated session
	 * 
	 * @param user The user that this session has been authenticated as
	 * @param expiresAfter The longest period of inactivity this session can go through while
	 * still remaining authenticated. If <code>expiresAfter</code> milliseconds have passed between
	 * now and the most recent request, this session is no longer considered valid.
	 */
	public void setAuthenticated(User user, long expiresAfter)
	{
		this.expiresAfter = expiresAfter;
		this.user = user;
	}
	
	/**
	 * Removes the authentication on this session
	 */
	public void unauthenticate()
	{
		this.expiresAfter = -1;
		this.user = null;
	}
	
	/**
	 * @param name A property name
	 * @return Whether or not the name has an associated value.
	 */
	public boolean hasProperty(String name)
	{
		return this.properties.containsKey(name);
	}
	
	/**
	 * @param <T> The type of the value
	 * @param name The name of the property
	 * @return The property value, casted to the given type
	 */
	@SuppressWarnings("unchecked")
	public <T> T getValueAs(String name)
	{
		return (T) this.properties.get(name);
	}
	
	/**
	 * @param name The name of the property
	 * @param value The value to assign to it
	 */
	public void setProperty(String name, String value)
	{
		this.properties.put(name, value);
	}
	
	private void revalidate()
	{
		if(this.user != null && this.expiresAfter > 0) 
			if((System.currentTimeMillis() - this.lastUsed) > this.expiresAfter)
				this.user = null;
	}

}
