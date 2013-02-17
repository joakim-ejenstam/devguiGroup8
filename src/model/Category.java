package model;

/**
 * Class for a category for {@link ToDoItem}s.
 * At the moment a category is just a label (as a String). But later on, we could enhance it with a color and so on...
 * 
 * @author simon
 */
public class Category {

	private String label;

	/**
	 * default constructor (just added explicit, as we have another one...)
	 */
	public Category() {
		
	}
	/**
	 * constructor that sets the attributes of the category
	 * @param label the label of the new category.
	 */
	public Category(String label) {
		this.label = label;
	}
	
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
}
