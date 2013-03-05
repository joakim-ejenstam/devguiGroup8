package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

@SuppressWarnings("serial")
public class DeletedListModel extends AbstractListModel {

	private ToDoItemModel itemModel;
	private List<ToDoItem> deleted;
	
	public DeletedListModel(ToDoItemModel listModel) {
		this.itemModel = listModel;
        this.deleted = new ArrayList<ToDoItem>(10);
		getDeletedItems();
	}
	
	@Override
	public Object getElementAt(int index) {
		return this.deleted.get(index);
	}
	
	@Override
	public int getSize() {
		return deleted.size();
	}
	
	public void getDeletedItems() {
		this.deleted = this.itemModel.getDeletedToDoItems();
	}
}
