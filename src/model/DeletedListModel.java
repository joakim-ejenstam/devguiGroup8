package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

@SuppressWarnings("serial")
public class DeletedListModel extends AbstractListModel {

	private ToDoItemModel itemModel;
	private List<ToDoItem> deleted;
	
	/**
	 * The constructor to create a list to do item model
	 * @param listModel the underlying model
	 */
	public DeletedListModel(ToDoItemModel listModel) {
		this.itemModel = listModel;
        this.deleted = new ArrayList<ToDoItem>();
		getDeletedItems();
	}
	
	/**
	 * Returns the value at the specified index.
	 * @param row index where to retrieve the value.
	 * @return the value at the specified index.
	 */
	@Override
	public Object getElementAt(int index) {
		return this.deleted.get(index);
	}
	
	/**
	 * Method for calculating the size (length) of the list.
	 * @return the number of to do items in the list.
	 */
	@Override
	public int getSize() {
		return deleted.size();
	}
	
	/**
	 * Method for retrieving the deleted to do items.
	 */
	public void getDeletedItems() {
		this.deleted = this.itemModel.getDeletedToDoItems();
	}
}
