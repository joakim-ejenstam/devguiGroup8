package controller;
import model.ToDoItem;
import model.XMLFileToDoItemModel;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-02-14
 * Time: 22:12
 * To change this template use File | Settings | File Templates.
 */
public class ToDoController {
    private XMLFileToDoItemModel model;
    private AddAction add;
    private EditAction edit;

    public ToDoController(XMLFileToDoItemModel Xmodel){
        this.model = Xmodel;
        //this.add = new AddAction();
        //this.edit = new EditAction();
    }

    /**
     * Method for adding a new item to the model. This method will sanitize input if needed.
     */
    public void addItem(String title) {
        ToDoItem item = model.createToDoItem(title);
        return item;
    }


    public void getEditItem(int index) {
        //model.getItem(index);

    }

    public void updateEditItem(ToDoItem newItem) {
        //model.updateTodoItem(newItem)
    }
}
