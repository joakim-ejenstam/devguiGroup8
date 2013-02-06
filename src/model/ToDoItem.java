package model;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;

/**
 * JavaBean that represents a ToDo-list item.
 * @author simon
 *
 */
@SuppressWarnings("serial") //TODO: we should make sure within the model, that not
							//		two items with the same content are created!
public class ToDoItem implements Serializable, Comparable<ToDoItem> {

	private String	title;
	private String	description;
	private Date	dueDate;
	private int		priority;
	private EnumSet<Category>	categories;
	private boolean	done;
	private boolean	deleted;
	private Date	creationDate;
	
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title of the ToDo-item to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description of the ToDo-item to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	/**
	 * @return the List of all categories
	 */
	public EnumSet<Category> getCategories() {
		return categories;
	}
	/**
	 * @param categories the list of all categories to set
	 */
	public void setCategories(EnumSet<Category> categories) {
		this.categories = categories;
	}
	/**
	 * @param category the category to add to the list of categories
	 */
	public void addCategory(Category category) {
		this.categories.add(category);
	}
	/**
	 * @return the status if item is done
	 */
	public boolean isDone() {
		return done;
	}
	/**
	 * @param done the status if item is done to set
	 */
	public void setDone(boolean done) {
		this.done = done;
	}
	/**
	 * @return the status if item is deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}
	/**
	 * @param deleted the status if item is deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	@Override
	public int compareTo(ToDoItem o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//TODO: should we override 
	// - boolean equals(Object arg0)
	// - int hashCode()
	// to use the advantage of the HashSet (used in the models)
}
