package model;

import javax.swing.AbstractListModel;

/**
 * This class provides the model of the list to be used in the main view. 
 * 
 * @author Emma Rangert
 *
 */

@SuppressWarnings("serial")
public class ListToDoItemModel extends AbstractListModel {

	private ToDoItemModel model;
    // language not necessary for the view yet since the list will only show data that the user has put in
	// private LocalizedTexts lang;  
	
	/**
	 * The constructor to create a list to do item model
	 * @param listModel the underlying model
	 */
	public ListToDoItemModel(ToDoItemModel listModel) {	//, LocalizedTexts newLang) {
		this.model = listModel;
		//this.lang = newLang;	same as above
	}
	
	/**
	 * Returns the value at the specified index.
	 * @param the index where to retrieve the value.
	 * @return the value at the specified index.
	 */
	@Override
	public Object getElementAt(int arg0) {
		return this.model.getToDoItem(arg0);
	}

	/**
	 * Method for calculating the size (length) of the list.
	 * @return the number of to do items in the list.
	 */
	@Override
	public int getSize() {
		return this.model.getNumberOfToDoItems();
	}
	
	

}
