package model;

import java.util.List;

import javax.swing.AbstractListModel;

/**
 * This class provides the model of the list to be used in the main view. 
 * 
 * @author Emma Rangert
 *
 */

@SuppressWarnings("serial")
public class ListToDoItemModel extends AbstractListModel {

	private ToDoItemModel itemModel;

	/**
	 * The constructor to create a list to do item model
	 * @param listModel the underlying model
	 */
	public ListToDoItemModel(ToDoItemModel listModel) {	//, LocalizedTexts newLang) {
		this.itemModel = listModel;
		//this.lang = newLang;	same as above
	}
	
	/**
	 * Returns the value at the specified index.
	 * @param the index where to retrieve the value.
	 * @return the value at the specified index.
	 */
	@Override
	public Object getElementAt(int row) {
        if(this.itemModel.getToDoItem(row).isDone())
            return this.itemModel.getToDoItem(row).getTitle();
        return "";
	}

	/**
	 * Method for calculating the size (length) of the list.
	 * @return the number of to do items in the list.
	 */
	@Override
	public int getSize() {
		return this.itemModel.getNumberOfToDoItems();
	}
	
	
	public List<ToDoItem> getDeletedItems() {
		return this.itemModel.getDeletedToDoItems();
	}

}
