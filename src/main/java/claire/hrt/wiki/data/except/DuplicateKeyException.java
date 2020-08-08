/**
 * 
 */
package claire.hrt.wiki.data.except;

/**
 * An exception for when duplicate primary  keys are added to a data structure.
 * 
 * @author Claire
 */
public class DuplicateKeyException extends Exception {

	private static final long serialVersionUID = -4594248830866460068L;

	/**
	 * 
	 */
	public DuplicateKeyException() {}

	/**
	 * @param message
	 */
	public DuplicateKeyException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DuplicateKeyException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DuplicateKeyException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DuplicateKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
