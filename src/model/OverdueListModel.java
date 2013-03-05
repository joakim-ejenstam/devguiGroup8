package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;

/**
 * This class provides the model of the list to be used in the main view. 
 * 
 * @author Emma Rangert
 *
 */

@SuppressWarnings("serial")
public class OverdueListModel extends AbstractListModel {

	private ToDoItemModel itemModel;
	private List<ToDoItem> overdue;
	final Date currDate = new Date();

	/**
	 * The constructor to create a list to do item model
	 * @param listModel the underlying model
	 */
	public OverdueListModel(ToDoItemModel listModel) {
		this.itemModel = listModel;
        this.overdue = new ArrayList<ToDoItem>(10);
		getOverdueItems();
	}
	
	/**
	 * Returns the value at the specified index.
	 * @param row index where to retrieve the value.
	 * @return the value at the specified index.
	 */
	@Override
	public Object getElementAt(int row) {
        return this.overdue.get(row);
	}

	/**
	 * Method for calculating the size (length) of the list.
	 * @return the number of to do items in the list.
	 */
	@Override
	public int getSize() {
        return overdue.size();
    }
	
	public void getOverdueItems() {
		List<ToDoItem> allItems = this.itemModel.getAllToDoItems();
        int bound = allItems.size();
        
        System.out.println("number of items" + bound);
        System.out.println("currdate = " + currDate);
		for (int i = 0; i < bound; i++) {
            System.out.println("Debug: Time to check if item added to list");
            ToDoItem item = allItems.get(i);
            if (item.getDueDate() != null) {
			    if (item.getDueDate().compareTo(currDate) < 0) {
                    System.out.println("Debug: Overdue item added to list");
				    this.overdue.add(item);
			    }
            }
		}
	}
}
