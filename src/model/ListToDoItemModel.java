package model;

import javax.swing.AbstractListModel;

@SuppressWarnings("serial")
public class ListToDoItemModel extends AbstractListModel {

	private ToDoItemModel model;
    // language not necessary for the view yet since the list will only show data that the user has put in
	// private LocalizedTexts lang;  
	
	public ListToDoItemModel(ToDoItemModel lModel) {	//, LocalizedTexts newLang) {
		this.model = lModel;
		//this.lang = newLang;	same as above
	}
	
	@Override
	public Object getElementAt(int arg0) {
		return this.model.getToDoItem(arg0);
	}

	@Override
	public int getSize() {
		return this.model.getNumberOfToDoItems();
	}
	
	

}
