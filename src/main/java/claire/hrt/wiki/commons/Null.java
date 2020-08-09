/**
 * 
 */
package claire.hrt.wiki.commons;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import claire.hrt.wiki.commons.except.PreconditionViolationException;

/**
 * @author Claire
 *
 * Simple helper class to deal with library methods that don't use annotations
 */
public class Null {
	
	/**
	 * @param <T> The type of t
	 * @param t The object you want to assert as not null
	 * @return The object, unmodified
	 * @throws PreconditionViolationException if t is null
	 */
	@NonNull
	public static <T> T nonNull(@Nullable T t)
	{
		if(t == null) 
			throw new PreconditionViolationException("Incorrect assumption, t was actually null.");
		return t;
	}

}
