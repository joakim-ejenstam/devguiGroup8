package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.Serializer;
import exceptions.ToDoItemExistsException;

/**
 * This model keeps the {@link ToDoItem}-objects in a XML-file
 * @author simon, Mattias
 *
 */
public class XMLFileToDoItemModel extends ToDoItemModel {
	
	private final String XMLFILEPATH = "data"+File.separator+"db.xml";
	
	private List<ToDoItem> tasks;//all ToDoItem-objects

	private List<Category> categories;//all Category-objects
	
	public XMLFileToDoItemModel(){
		//read the XML-file and fill the internal data structure with the ToDoItems
		this.parseXML();
	}
	
	
	/**
	 * Creates a date object from the string value
	 * @param format type of format
	 * @param date the date in a string format
	 * @return a parsed date object
	 */
	private Date dateParser(String format, String date){
		DateFormat df = new SimpleDateFormat(format);
		Date dateObj = null;
		try {
			dateObj = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateObj;
	}
	
	/**
	 * Parsing a date object to a string
	 * @param format type of format
	 * @param date represents the date
	 * @return parsed string containing date
	 */
	private String dateFormatter(String format, Date date){
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	
	/**
	 * Adds all specified attributes of a {@link ToDoItem} to an XML file
	 * @param doc represents a complete XML document
	 * @param item ToDoItem to be saved to the XML file
	 * @param todo current XML element
	 */
	private void addAttributesToXML(Document doc,ToDoItem item, Element todo){
		Element title			= new Element("title");
		Element desc 			= new Element("description");
		Element dueDate			= new Element("duedate");
		Element category		= new Element("category");
		Element priority 		= new Element("priority");
		Element creationDate 	= new Element("creationdate");
		Element done		 	= new Element("done");
		Element deleted		 	= new Element("deleted");
		
		//Add all attributes
		todo.appendChild(title);
		title.appendChild(item.getTitle());
		
		todo.appendChild(desc);
		desc.appendChild(item.getDescription());
		
		todo.appendChild(dueDate);
		String date1 = "";
		if (item.getDueDate() != null){
			date1 = this.dateFormatter("yyy-MM-dd", item.getDueDate());
		}
		dueDate.appendChild(date1);
		
		todo.appendChild(category);
		String cat = "";
		if(item.getCategory() != null){
			cat = item.getCategory().toString();
		}
		category.appendChild(cat);
		
		todo.appendChild(priority);
		priority.appendChild(Integer.toString(item.getPriority()));
		
		todo.appendChild(creationDate);
		String date2 = this.dateFormatter("yyy-MM-dd'T'HH:mm", item.getCreationDate());
		creationDate.appendChild(date2);
		
		todo.appendChild(done);
		String doneAttr = (true == item.isDone() ? "1" : "0");
		done.appendChild(doneAttr);
		
		todo.appendChild(deleted);
		String delAttr = (true == item.isDone() ? "1" : "0");
		deleted.appendChild(delAttr);
		
		try {
			FileOutputStream out = new FileOutputStream(XMLFILEPATH);
			Serializer ser = new Serializer(out);
	        ser.setIndent(4);
	        ser.write(doc);
	        out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * This method loads a xml-file that is used as a database and fills the data structure
	 * of this class with the ToDoItems.
	 * TODO: exception handling could be improved. :)
	 */
	private void parseXML(){		
		try{
			Builder builder = new Builder();			
			Document doc	= builder.build(XMLFILEPATH);
			Element root 	= doc.getRootElement();
			Elements todos	= root.getFirstChildElement("todoitems").getChildElements();
			ToDoItem task	= null;
			this.tasks 		= new ArrayList<ToDoItem>(todos.size());
			
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
				task = new ToDoItem();	// we cannot use this.createToDoItem(String title) here
										//as it would add the item as a new item to the xml-file
				task.setTitle(title.getValue());
				task.setDescription(desc.getValue());
				Date date1 = null;
				if (dueDate.getValue() != ""){
					 date1 = this.dateParser("yyy-MM-dd", dueDate.getValue());
				}
				task.setDueDate(date1);
				
				Category cat = null;
				if(category.getValue() != ""){
					cat = new Category((category.getValue())); //TODO: needs to be changes? to take the categories from this model instead of creating a new object?
				}
				task.setCategory(cat);
				
				task.setPriority(Integer.parseInt(priority.getValue()));
				
				Date date2 = this.dateParser("yyy-MM-dd'T'HH:mm", creationDate.getValue());
				task.setCreationDate(date2);
				boolean doneAttr = (1 == Integer.parseInt(done.getValue())) ? true : false;
				task.setDone(doneAttr);
				
				boolean delAttr = (1 == Integer.parseInt(deleted.getValue())) ? true : false;
				task.setDeleted(delAttr);
				
				
				this.tasks.add(task);
				setChanged();
				notifyObservers();
			}
		}catch(ParsingException ex){
			ex.printStackTrace();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Adds a {@link ToDoItem} to the XML file
	 * @param item current ToDoItem
	 */
	private void addItemXML(ToDoItem item){
		try {
			Builder builder = new Builder();			
			Document doc = builder.build(XMLFILEPATH);
			Element root 	= doc.getRootElement();
			Element todos	= root.getFirstChildElement("todoitems");
			Element todo 	= new Element("todoitem");
			//Add new todoitem to todos
			todos.appendChild(todo);
			
			this.addAttributesToXML(doc, item, todo);
			
		} catch (ParsingException | IOException e) { //TODO
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes and adds a new {@link ToDoItem} to the XML file
	 * @param index current index of the todos in the XML file
	 * @param item current ToDoItem to be written to the XML file
	 */
	private void updateItemXML(int index, ToDoItem item){
		try {
			Builder builder = new Builder();	
			Document doc = builder.build(XMLFILEPATH);
			Element root 	= doc.getRootElement();
			Elements todos	= root.getFirstChildElement("todoitems").getChildElements();
			todos.get(index).removeChildren();	
			
			this.addAttributesToXML(doc, item, todos.get(index));
			
		} catch (ParsingException | IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Changes just one field of a {@link ToDoItem} in the XML file
	 * @param item current ToDoItem to be edited
	 * @param field which field to be changed
	 * @param value new value for the field
	 */
	private void editItemXML(ToDoItem item, String field, String value){
		try {
			Builder builder = new Builder();			
			Document doc;
			doc = builder.build(XMLFILEPATH);
			Element root 	= doc.getRootElement();
			Elements todos	= root.getFirstChildElement("todoitems")
					.getChildElements();
			
			for(int i = 0; i< todos.size(); i++){
				Element todo = todos.get(i);
				if(todo.getFirstChildElement("title")
						.getValue().equals(item.getTitle())){
					
					todo.getFirstChildElement(field).removeChildren();
					todo.getFirstChildElement(field).appendChild(value);
					break;
				}
			}
			FileOutputStream out = new FileOutputStream(XMLFILEPATH);
		    Serializer ser = new Serializer(out);
		    ser.setIndent(4);
		    ser.write(doc);
		    out.close();
			
		} catch (ParsingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public List<ToDoItem> getAllToDoItems() {
		List<ToDoItem> undeletedItems = new ArrayList<ToDoItem>(this.getNumberOfToDoItems()); // just temporary to filter
		for(ToDoItem currentItem: this.tasks) {
			if(!currentItem.isDeleted())
				undeletedItems.add(currentItem);
		}
		return undeletedItems;
	}
	

	@Override
	public void updateToDoItem(int index, ToDoItem updatedItem) {
		this.tasks.remove(index);
		this.tasks.add(index, updatedItem);
		this.updateItemXML(index, updatedItem); // save to file
		setChanged();
		notifyObservers();
	}
	

	@Override
	public ToDoItem createToDoItem(String title) throws ToDoItemExistsException {
		ToDoItem newItem = new ToDoItem();
		newItem.setTitle(title);
		newItem.setCreationDate(new Date());
		if(this.tasks.contains(newItem))
			throw new ToDoItemExistsException();
		else {
			this.tasks.add(newItem);
			this.addItemXML(newItem); // save to file
			setChanged();
			notifyObservers();
		}
		return newItem;
	}

	@Override
	public void markToDoItemAsDone(ToDoItem item) {
		this.tasks.get(this.getIndexOfToItem(item)).setDone(true);
		this.editItemXML(item, "done", "1"); // save to file
		setChanged();
		notifyObservers();
	}

	@Override
	public void markToDoItemAsUndone(ToDoItem item) {
		this.tasks.get(this.getIndexOfToItem(item)).setDone(false);
		this.editItemXML(item, "done", "0"); // save to file
		setChanged();
		notifyObservers();
	}

	@Override
	public void deleteToDoItem(ToDoItem item) {
		this.tasks.get(this.getIndexOfToItem(item)).setDeleted(true);
		this.editItemXML(item, "deleted", "1"); // save to file
		setChanged();
		notifyObservers();
	}

	@Override
	public void restoreToDoItem(ToDoItem item) {
		this.tasks.get(this.getIndexOfToItem(item)).setDeleted(false);
		this.editItemXML(item, "deleted", "0"); // save to file
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


	@Override
	public List<ToDoItem> getDeletedToDoItems() {
		List<ToDoItem> deletedItems = new ArrayList<ToDoItem>(this.getNumberOfToDoItems()); // just temporary to filter
		for(ToDoItem currentItem: this.tasks) {
			if(currentItem.isDeleted())
				deletedItems.add(currentItem);
		}
		return deletedItems;
	}


	@Override
	public List<Category> getAllCategories() {
		return this.categories;
	}


	@Override
	public Category getCategory(int index) {
		return this.categories.get(index);
	}


	@Override
	public void addCategory(Category category) {
		this.categories.add(category);
		setChanged();
		notifyObservers();
	}
}

