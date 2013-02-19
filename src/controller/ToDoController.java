package controller;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.plaf.basic.BasicTreeUI;

import model.ToDoItem;
import model.ToDoItemModel;
import exceptions.ToDoItemExistsException;
import model.XMLFileToDoItemModel;
import view.MainView;

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
    private OkAction ok;
    private DeleteAction delete;
    private CancelAction cancel;

    public ToDoController(ToDoItemModel newModel){
        this.model = newModel;
        this.add = new AddAction("Add",createNavigationIcon("Back24"),"Press to add ToDoItem", KeyEvent.VK_A, this);
        this.edit = new EditAction("Edit",createNavigationIcon("Back24"),"Press to edit ToDoItem", KeyEvent.VK_E, this);
        this.delete = new DeleteAction("Delete",createNavigationIcon("Back24"),"Press to delete ToDoItem", KeyEvent.VK_D, this);
        this.ok = new OkAction("Save",createNavigationIcon("Back24"),"Press to save ToDoItem", KeyEvent.VK_O, this);
        this.cancel = new CancelAction("Cancel", createNavigationIcon("Back24"),"Press to abort", KeyEvent.VK_C, this);
    }

    public ToDoItem addItem(String title) {
        ToDoItem item = null;

        try{
        item = (ToDoItem)model.createToDoItem(title);
        } catch (ToDoItemExistsException e) {
            item = null;
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

    /**
     * Get method for the Add action.
     * @return
     */
    public AddAction getAddAction() {
        return add;
    }

    /**
     * Get method for the Edit action.
     * @return
     */
    public EditAction getEditAction() {
        return edit;
    }

    /**
     * Get method for the Delete action.
     * @return
     */
    public DeleteAction getDeleteAction() {
        return delete;
    }

    /**
     * Get method for the Cancel action.
     * @return
     */
    public CancelAction getCancelAction() {
        return cancel;
    }

    /**
     * Get method for the Ok action.
     * @return
     */
    public OkAction getOkAction() {
        return ok;
    }

    /**
     * This method is used to add the observer to the observable model.
     * @param arg
     */
    public void addObserver(MainView arg) {
        model.addObserver(arg);
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

