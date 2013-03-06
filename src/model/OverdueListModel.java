package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;

/**
 * This class provides the list model for the items that are overdue to be used in the main view. 
 * 
 * @author Emma Rangert
 *
 */

@SuppressWarnings("serial")
public class OverdueListModel extends AbstractListModel {

	private ToDoItemModel itemModel;
	private List<ToDoItem> overdue;

	Date today;
	
	/**
	 * The constructor to create a list to do item model
	 * @param listModel the underlying model 
	 */
	public OverdueListModel(ToDoItemModel listModel) {
		this.itemModel = listModel;
        this.overdue = new ArrayList<ToDoItem>();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			today = df.parse(df.format(new Date()));

		} catch (ParseException e) {
			getOverdueItems();
		}
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
	
	/**
	 * Method for retrieving the overdue items.
	 */
	public void getOverdueItems() {
		List<ToDoItem> allItems = this.itemModel.getAllToDoItems();
        int bound = allItems.size();
        overdue = new ArrayList<ToDoItem>();

		for (int i = 0; i < bound; i++) {
            ToDoItem item = allItems.get(i);
            if (item.getDueDate() != null) {

			    if (item.getDueDate().compareTo(today) < 0) {
				    this.overdue.add(item);
			    }
            }
		}
        super.fireContentsChanged(this,0,bound);
	}
	
}
