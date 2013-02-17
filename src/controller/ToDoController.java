package controller;

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

    public ToDoController(XMLFileToDoItemModel Xmodel){
        this.model = Xmodel;
    }

    /**
     * Method for adding a new item to the model. This method will sanitize input if needed.
     */
    public void addItem(String title) {
        //model.createToDoItem(title);
    }


    public void editItem(String title) {
        //model.getItem(title);

    }
}
