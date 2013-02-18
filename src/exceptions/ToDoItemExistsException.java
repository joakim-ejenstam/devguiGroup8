package exceptions;

import model.ToDoItem;

/**
 * This exception is used to indicate, that the same {@link ToDoItem} already exists.
 * (In plain words: the only mandatory attribute is the title, thus this exception 
 * will be thrown if someone tries to create a second {@link ToDoItem} with an already
 * existing title.)
 * This exception does not add any extra functionality to the superclass exception.
 * @author simon
 *
 */
@SuppressWarnings("serial")
public class ToDoItemExistsException extends Exception {

	public ToDoItemExistsException() {
	}

	/**
	 * @param message
	 */
	public ToDoItemExistsException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ToDoItemExistsException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ToDoItemExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ToDoItemExistsException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
