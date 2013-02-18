/**
 * 
 */
package exceptions;

/**
 * This exception can get thrown if something went wrong while creating the model.
 * A more detailed reason should be given in the message-string.
 * This exception does not add any extra functionality to the superclass exception.
 * @author simon
 *
 */
@SuppressWarnings("serial")
public class LoadModelException extends Exception {

	/**
	 * 
	 */
	public LoadModelException() {
	}

	/**
	 * @param message
	 */
	public LoadModelException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public LoadModelException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LoadModelException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public LoadModelException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
