package controller;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import model.ToDoItem;
import model.ToDoItemModel;
import exceptions.ToDoItemExistsException;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-02-14
 * Time: 22:12
 */
public class ToDoController {
    private ToDoItemModel model;
    private AddAction add;
    private EditAction edit;

    public ToDoController(ToDoItemModel model2){
        this.model = model2;
        this.add = new AddAction("Add",createNavigationIcon("Back24"),"Press to add ToDoItem", KeyEvent.VK_A, this);
        this.edit = new EditAction("Edit",createNavigationIcon("Back24"),"Press to edit ToDoItem", KeyEvent.VK_E, this);
        this.edit = new EditAction("Delete",createNavigationIcon("Back24"),"Press to delete ToDoItem", KeyEvent.VK_D, this);
    }

    /**
     * Method for adding a new item to the model. This method will sanitize input if needed.
     */

    public ToDoItem addItem(String title) {
        ToDoItem item = null;

        try{
        item = (ToDoItem)model.createToDoItem(title);
        } catch (ToDoItemExistsException e) {
            e.printStackTrace();
        }

        return item;
    }


    public ToDoItem getEditItem(int index) {
        //model.getItem(index);
        return null;
    }

    public void updateEditItem(ToDoItem newItem) {
        //model.updateTodoItem(newItem)
    }

    public AddAction getAddAction() {
        return add;
    }

    public EditAction getEditAction() {
        return edit;
    }
    /** MAJOR TEMPORARY CODE!!!! Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createNavigationIcon(String imageName) {
        String imgLocation = "toolbarButtonGraphics/navigation/"
                + imageName
                + ".gif";
        java.net.URL imageURL = ToDoController.class.getResource(imgLocation);

        if (imageURL == null) {
            System.err.println("Resource not found: "
                    + imgLocation);
            return null;
        } else {
            return new ImageIcon(imageURL);
        }
    }
    
    /**
     * This Method will return the current used {@link ToDoItemModel} (which could be by chance a XML-Model :) ) to e.g. the view
     * @author simon
     * @return {@link ToDoItemModel} the Model used in the controller
     */
    public ToDoItemModel getModel() {
    	return this.model;
    }
}
