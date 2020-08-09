package claire.hrt.wiki.commons.except;

public class PreconditionViolationException extends Error {

	private static final long serialVersionUID = 764236203625490841L;

	public PreconditionViolationException() {}

	public PreconditionViolationException(String message) {
		super(message);
	}

	public PreconditionViolationException(Throwable cause) {
		super(cause);
	}

	public PreconditionViolationException(String message, Throwable cause) {
		super(message, cause);
	}

	public PreconditionViolationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
