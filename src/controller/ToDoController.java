package controller;
import exceptions.ToDoItemExistsException;
import model.ToDoItem;
import model.XMLFileToDoItemModel;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-02-14
 * Time: 22:12
 */
public class ToDoController {
    private XMLFileToDoItemModel model;
    private AddAction add;
    private EditAction edit;

    public ToDoController(XMLFileToDoItemModel Xmodel){
        this.model = Xmodel;
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
}
