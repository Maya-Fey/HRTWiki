/**
 * 
 */
package claire.hrt.wiki.data.except;

/**
 * For when you try to read from a map with a non-existant primary key
 * 
 * @author Claire
 */
public class NoSuchKeyException extends Exception {

	private static final long serialVersionUID = 4058347911371871532L;

	/**
	 * 
	 */
	public NoSuchKeyException() {}

	/**
	 * @param message
	 */
	public NoSuchKeyException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NoSuchKeyException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoSuchKeyException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NoSuchKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
