package model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.table.AbstractTableModel;

import annotations.DisplayInTable;

/**
 * This model provides the data to the JTable of the main view, using a {@link ToDoItemModel}.
 * @author simon
 *
 */
@SuppressWarnings("serial")
public class TableToDoItemModel extends AbstractTableModel {

	//The underlying data model
	private ToDoItemModel model;
    private LocalizedTexts lang;
	
	/**
	 * constructor to initialize the model
	 * @param model the {@link ToDoItemModel} to set via dependency injection
	 */
	public TableToDoItemModel(ToDoItemModel model, LocalizedTexts newLang) {
		this.model = model;
		this.lang = newLang;
	}
	
	
	/**
	 * Determines the number of columns by the attributes to be displayed,
	 * using reflections and annotations.
	 * @see javax.swing.table.TableModel#getColumnCount()
	 * @return int the number of columns
	 */
	@Override
	public int getColumnCount() {
		int count = 0;
	    Method[] methods = ToDoItem.class.getDeclaredMethods(); 
	    for (Method method : methods) {
	        if (method.isAnnotationPresent(DisplayInTable.class)) {
	            count++;
	        }
	    }
	    return count;
	}

	/**
	 * returns the number of stored {@link ToDoItem}s.
	 * @see javax.swing.table.TableModel#getRowCount()
	 * @return int the number of rows
	 */
	@Override
	public int getRowCount() {
		return this.model.getNumberOfToDoItems();
	}

	/**
	 * This method returns the value of the given {@link ToDoItem} (by the first index) for the given column (the second parameter).
	 * The requested item is retrieved by the corresponding method of the model.
	 * The requested attribute is selected by the ordering given by the {@link DisplayInTable}-annotation as defined in the item.
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 * @return Object the value of the requested attribute from the requested item
	 */
	@Override
	public Object getValueAt(int arg0, int arg1) {
		 Method[] methods = ToDoItem.class.getDeclaredMethods(); 
		    for (Method method : methods) {
		        if (method.isAnnotationPresent(DisplayInTable.class) && method.getAnnotation(DisplayInTable.class).value() == arg1) {
		            try {
                        //System.out.println(this.model.getToDoItem(arg0).getTitle() + " " + this.model.getToDoItem(arg0).getCategory() + " " + arg1);
						return method.invoke(this.model.getToDoItem(arg0), (Object[])null); //it's save to use null, as we just call getters
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
		        }
		    }
		return null; //cannot (read: should not) happen
	}
	
	/**
	 * Method for setting the column names of the table. 
	 * @returns String the name of the column column.
	 */
	@Override
	public String getColumnName(int column) {
		//TODO the following definition should be happen in a file (for many different languages) 
		String[] columnLabels = {lang.getText("ui.mainview.table.column.title"),lang.getText("ui.mainview.table.column.category"),
				lang.getText("ui.mainview.table.column.priority"),lang.getText("ui.mainview.table.column.due"),
				lang.getText("ui.mainview.table.column.done")};
		
		try {
			return columnLabels[column];
		} catch (IndexOutOfBoundsException e) { //normally that shouldn't happen...
			return "";
		}
	}

	/**
	 * Method to determine default renderer of cell content. 
	 * Used to be able to show for example check box in the done column of the table instead of text "true/false".
	 * @return Class the class of the data in the cell
	 */
	@Override
	public Class<?> getColumnClass(int column) {
		return getValueAt(0,column).getClass();
	}
	
	/**
	 * Method for making the check boxes in the table check-able.
	 */
	@Override
    public boolean isCellEditable(int row, int col) {
        return (col == 4);
    }
	
	/**
	 * 
	 */
	// not needed for now?
	// TODO: add listener to table column with the check box
	@Override
	public void setValueAt(Object value, int row, int col) {
		super.setValueAt(value, row, col);
        if (isCellEditable(row, col)) {
            if (this.model.getToDoItem(row).isDone())
                this.model.markToDoItemAsUndone(this.model.getToDoItem(row));
            else
                this.model.markToDoItemAsDone(this.model.getToDoItem(row));
        }
		fireTableCellUpdated(row,col);
    }
	
	/**
	 * Returns the underlying data model, which is a {@link ToDoItemModel}. This is kind of ugly and it would
	 * probably be better to just extend the ToDoItemModel, to have all methods available directly.
	 * But as we already extend the AbstractTablemodel and multiple inheritance is not supported in Java, we
	 * cannot do that... 
	 * @return ToDoItemModel that is the underlying data model
	 */
	public ToDoItemModel getUnderlyingDataModel() {
		return this.model;
	}

}
