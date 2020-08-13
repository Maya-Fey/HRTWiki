/**
 * 
 */
package claire.hrt.wiki.data;

import java.util.Objects;

import org.eclipse.jdt.annotation.Nullable;

/**
 * @author Claire
 */
public class SessionKey {

	private final String key;
	
	/**
	 * Constructs a session key from raw data
	 * 
	 * @param key The key as a string
	 */
	SessionKey(String key)
	{
		this.key = key;
	}

	/**
	 * @return the key
	 */
	public String getKey() 
	{
		return this.key;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.key);
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
		SessionKey other = (SessionKey) obj;
		return Objects.equals(this.key, other.key);
	}
	
}
