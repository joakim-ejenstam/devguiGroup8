package model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;

import model.Category;
import model.ToDoItem;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

import exceptions.ToDoItemExistsException;

/**
 * This model loads the {@link ToDoItem}-objects from a XML-file
 * TODO: nothing is implemented yet!
 * @author simon, Mattias
 *
 */
public class XMLFileToDoItemModel extends ToDoItemModel {
	
	private ArrayList<ToDoItem> tasks;
	
	public XMLFileToDoItemModel(){
		this.tasks = this.parseXML();
	}
	
	@Override
	public ArrayList<ToDoItem> getAllToDoItems() {
		return tasks;
	}
	
	private static ArrayList<ToDoItem> parseXML(){
		
		tasks = new ArrayList<ToDoItem>();
		Builder builder = new Builder();
		
		try{
			Document doc	= builder.build("data\\db.xml");
			Element root 	= doc.getRootElement();
			Elements todos	= root.getFirstChildElement("todoitems").getChildElements();
			
			for(int i = 0; i< todos.size(); i++){
				ToDoItem task = new ToDoItem();
				
				Element title			 = todos.get(i).getFirstChildElement("title");
				Element desc 			 = todos.get(i).getFirstChildElement("description");
				Element dueDate			 = todos.get(i).getFirstChildElement("duedate");
				Element category		 = todos.get(i).getFirstChildElement("category");
				Element priority 		 = todos.get(i).getFirstChildElement("priority");
				Element creationDate 	 = todos.get(i).getFirstChildElement("creationdate");
				Element done		 	 = todos.get(i).getFirstChildElement("done");
				Element deleted		 	 = todos.get(i).getFirstChildElement("deleted");

				
				//Add the attributes for the current task
				task.setTitle(title.getValue());
				task.setDescription(desc.getValue());
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					task.setDueDate(df.parse(dueDate.getValue()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				task.setCategory(Category.valueOf(category.getValue()));
				task.setPriority(Integer.parseInt(priority.getValue()));
				
				DateFormat df2 = new SimpleDateFormat("yyy-MM-dd HH:mm");
				try {
					task.setCreationDate(df2.parse(creationDate.getValue()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				boolean doneAttr = (1 == Integer.parseInt(done.getValue())) ? true : false;
				task.setDone(doneAttr);
				
				boolean delAttr = (1 == Integer.parseInt(deleted.getValue())) ? true : false;
				task.setDeleted(delAttr);
				
				tasks.add(task);
			}
		}catch(ParsingException ex){
			ex.printStackTrace();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		return tasks;
	}

	@Override
	public void updateToDoItem(ToDoItem oldItem, ToDoItem newItem) {
		// TODO Auto-generated method stub

	}

	@Override
	public ToDoItem createToDoItem(String title) throws ToDoItemExistsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void markToDoItemAsDone(ToDoItem item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void markToDoItemAsUndone(ToDoItem item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteToDoItem(ToDoItem item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void restoreToDoItem(ToDoItem item) {
		// TODO Auto-generated method stub

	}

}
