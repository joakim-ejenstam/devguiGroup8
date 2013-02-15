package model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import exceptions.ToDoItemExistsException;

/**
 * This model loads the {@link ToDoItem}-objects from a XML-file
 * @author simon, Mattias
 *
 */
public class XMLFileToDoItemModel extends ToDoItemModel {
	
	private ArrayList<ToDoItem> tasks;
	
	public XMLFileToDoItemModel(){
		//read the XML-file and fill the internal data structure with the ToDoItems
		this.parseXML();
	}
	
	/**
	 * This method loads a xml-file that is used as a database and fills the data structure
	 * of this class with the ToDoItems.
	 * TODO: exception handling could be improved. :)
	 */
	private void parseXML(){		
		try{
			Builder builder = new Builder(); //TODO: what's that?			
			Document doc	= builder.build("data\\db.xml");
			Element root 	= doc.getRootElement();
			Elements todos	= root.getFirstChildElement("todoitems").getChildElements();
			
			this.tasks = new ArrayList<ToDoItem>(todos.size());
			
			for(int i = 0; i< todos.size(); i++){
				Element title			 = todos.get(i).getFirstChildElement("title");
				Element desc 			 = todos.get(i).getFirstChildElement("description");
				Element dueDate			 = todos.get(i).getFirstChildElement("duedate");
				Element category		 = todos.get(i).getFirstChildElement("category");
				Element priority 		 = todos.get(i).getFirstChildElement("priority");
				Element creationDate 	 = todos.get(i).getFirstChildElement("creationdate");
				Element done		 	 = todos.get(i).getFirstChildElement("done");
				Element deleted		 	 = todos.get(i).getFirstChildElement("deleted");

				
				//Add/parse the attributes for the current task
				
				// as we have to inform the observers, we use the add method (instead of duplicating code)
				ToDoItem task = this.createToDoItem(title.getValue());
				task.setDescription(desc.getValue());
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					task.setDueDate(df.parse(dueDate.getValue()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				task.setCategory(Category.valueOf(category.getValue()));
				task.setPriority(Integer.parseInt(priority.getValue()));
				
				DateFormat df2 = new SimpleDateFormat("yyy-MM-dd'T'HH:mm");
				try {
					task.setCreationDate(df2.parse(creationDate.getValue()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				boolean doneAttr = (1 == Integer.parseInt(done.getValue())) ? true : false;
				task.setDone(doneAttr);
				
				boolean delAttr = (1 == Integer.parseInt(deleted.getValue())) ? true : false;
				task.setDeleted(delAttr);
				
				// use our own method to add the task, as we have to inform the observers and don't want to duplicate that code
				this.updateToDoItem(this.getIndexOfToItem(task), task);
			}
		}catch(ParsingException ex){
			ex.printStackTrace();
		}catch(IOException ex){
			ex.printStackTrace();
		} catch (ToDoItemExistsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<ToDoItem> getAllToDoItems() {
		return this.tasks;
	}

	@Override
	public void updateToDoItem(int index, ToDoItem updatedItem) {
		this.tasks.remove(index);
		this.tasks.add(index, updatedItem);
		// TODO save to file (here or in separate method; only this item or write complete list?)
		setChanged();
		notifyObservers();
	}

	@Override
	public ToDoItem createToDoItem(String title) throws ToDoItemExistsException {
		ToDoItem newItem = new ToDoItem();
		newItem.setTitle(title);
		if(this.tasks.contains(newItem))
			throw new ToDoItemExistsException();
		else {
			this.tasks.add(newItem);
			// TODO save to file (here or in separate method; only this item or write complete list?)
			setChanged();
			notifyObservers();
		}
		return newItem;
	}

	@Override
	public void markToDoItemAsDone(ToDoItem item) {
		this.tasks.get(this.getIndexOfToItem(item)).setDone(true);
		// TODO save to file (here or in separate method; only this item or write complete list?)
		setChanged();
		notifyObservers();
	}

	@Override
	public void markToDoItemAsUndone(ToDoItem item) {
		this.tasks.get(this.getIndexOfToItem(item)).setDone(false);
		// TODO save to file (here or in separate method; only this item or write complete list?)
		setChanged();
		notifyObservers();
	}

	@Override
	public void deleteToDoItem(ToDoItem item) {
		this.tasks.get(this.getIndexOfToItem(item)).setDeleted(true);
		// TODO save to file (here or in separate method; only this item or write complete list?)
		setChanged();
		notifyObservers();
	}

	@Override
	public void restoreToDoItem(ToDoItem item) {
		this.tasks.get(this.getIndexOfToItem(item)).setDeleted(false);
		// TODO save to file (here or in separate method; only this item or write complete list?)
		setChanged();
		notifyObservers();
	}

	@Override
	public ToDoItem getToDoItem(int index) {
		return this.tasks.get(index);
	}

	@Override
	public int getIndexOfToItem(ToDoItem item) {
		return this.tasks.indexOf(item);
	}

	@Override
	public int getNumberOfToDoItems() {
		return this.tasks.size();
	}

}
