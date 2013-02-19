/**
 * 
 */
package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is used to define that an attribute which is returned 
 * by a getter-method (of a ToDoItem) should be displayed in the table 
 * of ToDoItems.
 * Thus this annotation must be present above every getter thats value 
 * should be visible in the user interface in the table of ToDoItems.
 * @author simon
 *
 */
@Retention(RetentionPolicy.RUNTIME) //We need this Annotation at runtime
public @interface DisplayInTable {
	/**
	 * The ordering of the columns is determined by this parameter.
	 * All annotations need to have an number, starting with 0 and
	 * increasing by 1. This number defines the position of this attribute
	 * in the table rows.
	 * Thus the value of the getter with the annotation with the value 0
	 * will be displayed in the first table column and so on. 
	 * @return int the index of an attribute
	 */
	public int value();
}
