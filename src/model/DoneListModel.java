package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

/**
 * 
 * @author Emma Rangert
 *
 */

@SuppressWarnings("serial")
public class DoneListModel extends AbstractListModel {

	private ToDoItemModel itemModel;
	private List<ToDoItem> done;
	
	/**
	 * The constructor to create a list to do item model
	 * @param listModel the underlying model
	 */
	public DoneListModel(ToDoItemModel listModel) {	
		this.itemModel = listModel;
        this.done = new ArrayList<ToDoItem>();
		getDoneItems();
	}
	
	/**
	 * Returns the value at the specified index.
	 * @param row index where to retrieve the value.
	 * @return the value at the specified index.
	 */
	@Override
	public Object getElementAt(int index) {
        return this.done.get(index);
	}

	/**
	 * Method for calculating the size (length) of the list.
	 * @return the number of to do items in the list.
	 */
	@Override
	public int getSize() {
		return done.size();
	}

	/**
	 * Method for retrieving the done items
	 */
	public void getDoneItems() {
		List<ToDoItem> allItems = this.itemModel.getAllToDoItems();
		int bound = allItems.size();
        this.done = new ArrayList<ToDoItem>();

		for (int i = 0; i < bound; i++) {
            ToDoItem item = allItems.get(i);
            
            if (item.isDone()) {
            	this.done.add(item);
                super.fireContentsChanged(this,0,bound);
            }
		}
	}
	
	
}
