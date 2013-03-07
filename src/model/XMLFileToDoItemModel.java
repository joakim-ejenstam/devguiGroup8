package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
import exceptions.LoadModelException;
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
	
	public XMLFileToDoItemModel() throws LoadModelException{
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
		String dDate = "";
		if (item.getDueDate() != null){
			dDate = this.dateFormatter("yyyy-MM-dd", item.getDueDate());
		}
		dueDate.appendChild(dDate);
		
		todo.appendChild(category);
		String cat = "";
		if(item.getCategory() != null){
			cat = item.getCategory().getLabel();
		}
		category.appendChild(cat);
		
		
		todo.appendChild(priority);
		priority.appendChild(Integer.toString(item.getPriority()));
		
		todo.appendChild(creationDate);
		String cDate = this.dateFormatter("yyyy-MM-dd'T'HH:mm", item.getCreationDate());
		creationDate.appendChild(cDate);
		
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
	 * @throws LoadModelException if the XML-file caused some problems...
	 */
	private void parseXML() throws LoadModelException{		
		try{
			Builder builder = new Builder();
			Document doc	= null;
			try {
			doc = builder.build(new File(XMLFILEPATH)); //let's try to open an existing db
			} catch(IOException e) {
				//we might run for the first time and just don't have a db yet. So let's create one...
				File file = new File(XMLFILEPATH);
				if (!file.exists()) {
					file.getParentFile().mkdirs();//path
					file.createNewFile();//file
				}
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
						+"<model>\n"
						+"\t<todoitems>\n"
						+"\t\t<todoitem>\n"
						+"\t\t\t<title>Enter all ToDo's</title>\n"
						+"\t\t\t<description>Use your new ToDo-application wisley.</description>\n"
						+"\t\t\t<duedate>"+new SimpleDateFormat("yyyy-mm-dd").format(new Date())+"</duedate>\n"
						+"\t\t\t<category>Private</category>\n"
						+"\t\t\t<priority>1</priority>\n"
						+"\t\t\t<creationdate>"+new SimpleDateFormat("yyyy-mm-dd'T'hh:mm").format(new Date())+"</creationdate>\n"
						+"\t\t\t<done>0</done>\n"
						+"\t\t\t<deleted>0</deleted>\n"
						+"\t\t</todoitem>\n"
						+"\t</todoitems>\n"
						+"\t<categories>\n"
						+"\t\t<category>Private</category>\n"
						+"\t\t<category>University</category>\n"
						+"\t</categories>\n"
						+"</model>\n");
				bw.close();
				doc = builder.build(new File(XMLFILEPATH)); //let's try a second time
			}
			Element root 	= doc.getRootElement();
			
			//Parse Categories
			this.categories = new ArrayList<Category>();
			Elements categories = root.getFirstChildElement("categories").getChildElements();
			for(int i = 0; i< categories.size(); i++){
				String cat = categories.get(i).getValue();
				this.addCategory(new Category(cat));
			}
			
			
			//Parse ToDoItems
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
				Date dDate = null;
				if (dueDate.getValue() != ""){
					dDate = this.dateParser("yyyy-MM-dd", dueDate.getValue());
				}
				task.setDueDate(dDate);
				
				
				if(category.getValue() != ""){
					for(Category cat : this.getAllCategories()){
						if (cat.getLabel().equals(category.getValue())){
							task.setCategory(cat);
						}	
					}
				}
				
				
				task.setPriority(Integer.parseInt(priority.getValue()));
				
				Date cDate = this.dateParser("yyyy-MM-dd'T'HH:mm", creationDate.getValue());
				task.setCreationDate(cDate);
				boolean doneAttr = (1 == Integer.parseInt(done.getValue())) ? true : false;
				task.setDone(doneAttr);
				
				boolean delAttr = (1 == Integer.parseInt(deleted.getValue())) ? true : false;
				task.setDeleted(delAttr);
				
				
				this.tasks.add(task);
				setChanged();
				notifyObservers();
			}
		} catch (ParsingException e) {
			throw new LoadModelException("The db-file "+XMLFILEPATH+" could not be parsed.",e.getCause());
		} catch (IOException e) {
			e.printStackTrace();
			throw new LoadModelException("The db-file "+XMLFILEPATH+" could not be loaded.",e.getCause());
		}
	}
	
	/**
	 * Adds a {@link ToDoItem} to the XML file
	 * @param item current ToDoItem
	 */
	private void addItemXML(ToDoItem item){
		try {
			Builder builder = new Builder();			
			Document doc = builder.build(new File(XMLFILEPATH));
			Element root 	= doc.getRootElement();
			Element todos	= root.getFirstChildElement("todoitems");
			Element todo 	= new Element("todoitem");
			//Add new todoitem to todos
			todos.appendChild(todo);
			
			this.addAttributesToXML(doc, item, todo);
			
		}catch (ParsingException e){
			e.printStackTrace();
		}catch (IOException e){
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
			Document doc = builder.build(new File(XMLFILEPATH));
			Element root 	= doc.getRootElement();
			Elements todos	= root.getFirstChildElement("todoitems").getChildElements();
			todos.get(index).removeChildren();	
			
			this.addAttributesToXML(doc, item, todos.get(index));
			
		}catch (ParsingException e){
			e.printStackTrace();
		}catch (IOException e){
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
			doc = builder.build(new File(XMLFILEPATH));
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
		newItem.setPriority(1);
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
		List<ToDoItem> undeletedItems = new ArrayList<ToDoItem>(this.getNumberOfToDoItems()); // just temporary to filter
		for(ToDoItem currentItem: this.tasks) {
			if(!currentItem.isDeleted())
				undeletedItems.add(currentItem);
		}
		return undeletedItems.get(index);
	}

	@Override
	public int getIndexOfToItem(ToDoItem item) {
		return this.tasks.indexOf(item);
	}

	@Override
	public int getNumberOfToDoItems() {
		List<ToDoItem> undeletedItems = new ArrayList<ToDoItem>(this.tasks.size()); // just temporary to filter
		for(ToDoItem currentItem: this.tasks) {
			if(!currentItem.isDeleted())
				undeletedItems.add(currentItem);
		}
		return undeletedItems.size();//only number of not deleted items
	}


	@Override
	public List<ToDoItem> getDeletedToDoItems() {
		List<ToDoItem> deletedItems = new ArrayList<ToDoItem>(this.getNumberOfDeletedToDoItems()); // just temporary to filter
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


	@Override
	public int getMinPriority() {
		int minPrio = Integer.MAX_VALUE;
		for (ToDoItem currItem : this.tasks) {
			if(currItem.getPriority() < minPrio)
				minPrio = currItem.getPriority();
		}
		return minPrio;
	}


	@Override
	public int getMaxPriority() {
		int maxPrio = Integer.MIN_VALUE;
		for (ToDoItem currItem : this.tasks) {
			if(currItem.getPriority() > maxPrio)
				maxPrio = currItem.getPriority();
		}
		return maxPrio;
	}


	@Override
	public Date getMaxDueDate() {
		Date maxDueDate = new Date(); //max should be greater than today
		for (ToDoItem currItem : this.tasks) {
			if(currItem.getDueDate().compareTo(maxDueDate) > 0) //is DueDate > (current) maxDueDate?
				maxDueDate = currItem.getDueDate();
		}
		return maxDueDate;
	}


	@Override
	public Date getMinDueDate() {
		Date minDueDate = new Date(); //min should be smaller than today
		for (ToDoItem currItem : this.tasks) {
			if(currItem.getDueDate().compareTo(minDueDate) < 0) //is DueDate < (current) maxDueDate?
				minDueDate = currItem.getDueDate();
		}
		return minDueDate;
	}


	@Override
	public int getNumberOfDeletedToDoItems() {
		List<ToDoItem> deletedItems = new ArrayList<ToDoItem>(this.tasks.size()); // just temporary to filter
		for(ToDoItem currentItem: this.tasks) {
			if(currentItem.isDeleted())
				deletedItems.add(currentItem);
		}
		return deletedItems.size();//only number of deleted items
	}
	
}

