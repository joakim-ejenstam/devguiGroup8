/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Observable;

import exceptions.ToDoItemExistsException;

/**
 * This is a default abstract model for {@link ToDoItem}.s to specify the methods 
 * all Models have to implement.
 * I would have preferred an Interface over an abstract class,
 * but Observable is a Class and not an Interface, thus we need
 * a class here to be able to extend it...
 * All {@link ToDoItem}s should be stored internally in an ArrayList or similar, providing an index! 
 * @author simon
 *
 */
public abstract class ToDoItemModel extends Observable {
	
	/**
	 * This method doesn't provide any ordering, but just returns all known {@link ToDoItem}-objects.
	 * @return ArrayList<ToDoItem> the Collection of all {@link ToDoItem}s.
	 */
	public abstract ArrayList<ToDoItem> getAllToDoItems();
	
	/**
	 * Returns a single {@link ToDoItem}.
	 * @param index the index of the {@link ToDoItem} to return
	 * @return ToToItem the {@link ToDoItem} requested
	 */
	public abstract ToDoItem getToDoItem(int index);
	
	/**
	 * gives a sum of all stored {@link ToDoItem}s
	 * @return int the number of ToDoItems
	 */
	public abstract int getNumberOfToDoItems();
	
	/**
	 * Gives us the index of an {@link ToDoItem} in the internal data structure.
	 * @param item the {@link ToDoItem} of which we want the index
	 * @return int the index of the item
	 */
	public abstract int getIndexOfToItem(ToDoItem item);
	
	/**
	 * Use this method to update an already existing {@link ToDoItem}-object, e.g. after changing an attribute.
	 * This method can of course also be used to fill new attributes, not filled before
	 * @param index the index of the {@link ToDoItem} which should be updated
	 * @param updatedItem the new {@link ToDoItem} that got updated
	 */
	public abstract void updateToDoItem(int index, ToDoItem updatedItem);
	
	/**
	 * Creates a new {@link ToDoItem}-object. The only mandatory attribute is the title.
	 * @param title the title of the {@link ToDoItem} to create
	 * @return ToDoItem the {@link ToDoItem} newly created
	 * @throws ToDoItemExistsException if already another {@link ToDoItem} with the same title exists
	 */
	public abstract ToDoItem createToDoItem(String title) throws ToDoItemExistsException;
	
	/**
	 * 
	 * @param item the {@link ToDoItem} to mark as done.
	 */
	public abstract void markToDoItemAsDone(ToDoItem item);
	
	/**
	 * This is the method to use if you have marked a wrong {@link ToDoItem} as done.
	 * @param item the {@link ToDoItem} to mark as undone
	 */
	public abstract void markToDoItemAsUndone(ToDoItem item);
	
	/**
	 * This method marks an {@link ToDoItem}-object as deleted, it won't get really deleted...
	 * @param item the {@link ToDoItem} to delete.
	 */
	public abstract void deleteToDoItem(ToDoItem item);
	
	/**
	 * You can undelete a {@link ToDoItem}-object with this method 
	 * (if you have marked it as deleted and want to undo that).
	 * @param item the {@link ToDoItem} to restore.
	 */
	public abstract void restoreToDoItem(ToDoItem item);
	
}
