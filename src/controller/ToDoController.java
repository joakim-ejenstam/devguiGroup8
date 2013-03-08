package controller;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Locale;

import javax.swing.*;

import model.*;
import view.MainView;
import view.ToDoTableRenderer;
import exceptions.ToDoItemExistsException;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-02-14
 * Time: 22:12
 */
public class ToDoController extends ComponentAdapter {
    private ToDoItemModel model;
    private AddAction add;
    private EditAction edit;
    private OkAction ok;
    private DeleteAction delete;
    private CancelAction cancel;
    private ChangeLanguageAction language;
    private AboutAction about;
    private SetDoneAction setDone;
    private Config conf;
    private LocalizedTexts lang;
    private MainView view;
	private ShowGraphAction showGraphAction;


    /**
     * Constructor for the controller. Creates all actions and sets object fields from input parameters.
     * @param newModel The model of the current application
     * @param newLang The language object of the application
     */
    // The icons used in this application comes from:
    // Copyright (c) 2007 Axialis Software
    // http://www.axialis.com
    public ToDoController(ToDoItemModel newModel, LocalizedTexts newLang){
        this.model = newModel;
        this.lang = newLang;
        this.add =
                new AddAction(
                        lang.getText("ui.mainview.menu.edit.add"),
                        createNavigationIcon("/Add"),
                        lang.getText("ui.mainview.addAction"),
                        new Integer(lang.getText("ui.mainview.menu.edit.add.mnemonic").charAt(0)), this, lang);
        this.edit =
                new EditAction(
                        lang.getText("ui.mainview.menu.edit.edit"),
                        createNavigationIcon("/Overlay-edit"),
                        lang.getText("ui.mainview.editAction"),
                        new Integer(lang.getText("ui.mainview.menu.edit.edit.mnemonic").charAt(0)), this, lang);
        this.delete =
                new DeleteAction(
                        lang.getText("ui.mainview.menu.edit.delete"),
                        createNavigationIcon("/Trash"),
                        lang.getText("ui.mainview.deleteAction"),
                        new Integer(lang.getText("ui.mainview.menu.edit.delete.mnemonic").charAt(0)), this);
        this.ok =
                new OkAction(
                		lang.getText("ui.editview.button.ok"),
                        null,
                        lang.getText("ui.mainview.okAction"),
                        new Integer(lang.getText("ui.editview.button.ok.mnemonic").charAt(0)), this);
        this.cancel =
                new CancelAction(
                        lang.getText("ui.editview.button.cancel"),
                        null,
                        lang.getText("ui.mainview.cancelAction"),
                        new Integer(lang.getText("ui.editview.button.cancel.mnemonic").charAt(0)), this);
        this.language =
                new ChangeLanguageAction(
                        lang.getText("ui.mainview.menu.file.changeLanguage"),
                        createNavigationIcon("/Compile"),
                        lang.getText("ui.mainview.languageAction"),
                        new Integer(lang.getText("ui.mainview.menu.file.changeLanguage.mnemonic").charAt(0)), this, lang);
        this.about =
                new AboutAction(
                        lang.getText("ui.mainview.menu.help.about"),
                        createNavigationIcon("/Info"),
                        lang.getText("ui.mainview.aboutAction"),
                        new Integer(lang.getText("ui.mainview.menu.help.about.mnemonic").charAt(0)), this);
        
        this.setDone =
                new SetDoneAction(
                        lang.getText("ui.tableview.setdone"),
                        createNavigationIcon("/Ok"),
                        lang.getText("ui.tableview.setdone"),
                        new Integer(lang.getText("ui.tableview.setdone.mnemonic").charAt(0)), this);
        
        this.showGraphAction =
        		new ShowGraphAction(
        				lang.getText("ui.mainview.menu.file.item.showGraph"),
                        createNavigationIcon("/Chart1"),
                        lang.getText("ui.mainview.menu.file.item.showGraph"),
                        new Integer(lang.getText("ui.mainview.menu.file.item.showGraph.mnemonic").charAt(0)), this);
    }

    /**
	 * @return the model
	 */
	public ToDoItemModel getModel() {
		return this.model;
	}

	/**
	 * return the conf
	 */
	public Config getConf() {
		return this.conf;
	}

	/**
     * Method to add a new item to the model.
     * @param title title string input by the user
     * @return null or the new item
     */
    public ToDoItem addItem(String title) {
        ToDoItem item = null;

        try{
        item = (ToDoItem)model.createToDoItem(title);
        } catch (ToDoItemExistsException e) {
            item = null;
        }

        return item;
    }

    /**
     * Fetches all the categories.
     * @return list with categories
     */
    public List<Category> getCategories(){
    	return model.getAllCategories();
    }

    /**
     * Method to get item to delete or inject to the editframe
     * @param index The place in the table where the item resides.
     * @return the requested item or null.
     */
    public ToDoItem getEditItem(int index) {
        return model.getToDoItem(index);
    }

    public void removeItem(int index) {
        ToDoItem item = model.getToDoItem(index);
        model.deleteToDoItem(item);
    }

    public void setSelected(int index) {
        ToDoItem item = model.getToDoItem(index);
        if(item.isDone())
            model.markToDoItemAsUndone(item);
        else
            model.markToDoItemAsDone(item);
    }

    /**
     * Update the corresponding item in the model.
     * @param newItem Item supplied by the edit frame.
     */
    public void updateEditItem(ToDoItem newItem) {
        model.updateToDoItem(model.getIndexOfToItem(newItem), newItem);
    }

    /**
     * Get method for the Add action.
     * @return
     */
    public AddAction getAddAction() {
        return add;
    }

    /**
     * Set method for the config object.
     * @param newConf
     */
    public void setConfig(Config newConf) {
        this.conf = newConf;
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
     * Get method for the about action
     * @return
     */
    public AboutAction getAboutAction() {
        return about;
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

    public SetDoneAction getDoneAction() {
        return setDone;
    }

    /**
     * Get method for the change language action.
     * @return
     */
    public ChangeLanguageAction getLanguageAction() {
        return language;
    }

    /**
     * This method is used to add the observer to the observable model.
     * @param arg
     */
    public void addObserver(MainView arg) {
        this.view = arg;
        model.addObserver(view);
    }

    /**
     * This is a method to add the reference to the current view table to the edit and
     * delete actions. Can be updated to take containers instead.
     * @param newTable the views table.
     */
    public void setTable(JTable newTable) {
        edit.setTable(newTable);
        delete.setTable(newTable);
        setDone.setTable(newTable);
    }

    /**
     * This method is used by the controller to update the language of all the actions it has control over.
     * @param arg the locale passed as the argument.
     */
    public void updateLanguage(Locale arg) {
        Locale.setDefault(arg);
        conf.setProp("locale",arg.getLanguage());
        lang.refreshTexts();
        setLanguage();
    }

    /**
     * Method to get the image from the datapath.
     * @param imageName string name of the image file
     * @return null or the image file.
     */
    public static ImageIcon createNavigationIcon(String imageName) {
        String imgLocation = ""
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
     * Method to set the language of the application. Will call all update methods in all the classes
     * the controller have control over.
     */
    private void setLanguage() {
        this.edit.updateLanguage(lang);
        this.add.updateLanguage(lang);
        this.delete.updateLanguage(lang);
        this.language.updateLanguage(lang);
        this.cancel.updateLanguage(lang);
        this.about.updateLanguage(lang);
        this.ok.updateLanguage(lang);
        view.table.setModel(new TableToDoItemModel(model, lang));
        view.table.getColumnModel().getColumn(3).setCellRenderer(new ToDoTableRenderer());
        this.view.updateLanguage(lang);
        this.setDone.updateLanguage(lang);
        this.showGraphAction.updateLanguage(lang);
    }
    
    /**
     * This method detects all movements of observed windows and saves the new position to the config,
     * so that we can show them at the same position at next start.
     * @see ComponentAdapter#componentMoved(ComponentEvent)
     */
    @Override
    public void componentMoved(ComponentEvent e) {
    	//save position of MainWindow
//    	if(e.getComponent().getClass().equals(MainView.class)){ //as we have a local jframe *inside* the mainview, that doesn't work
    		this.conf.setProp("windowXPos",Integer.toString(e.getComponent().getX()));
    		this.conf.setProp("windowYPos",Integer.toString(e.getComponent().getY()));
//    	}
    }
    
    /**
     * This method detects if a observed window is resized and saves the new position to the config,
     * so that we can show them with the same size at next start.
     * @see ComponentAdapter#componentResized(ComponentEvent)
     */
    @Override
    public void componentResized(ComponentEvent e) {
    	//save position of MainWindow
//    	if(e.getComponent().getClass().equals(MainView.class)){ //as we have a local jframe *inside* the mainview, that doesn't work
    		this.conf.setProp("windowHeight",Integer.toString(e.getComponent().getHeight()));
    		this.conf.setProp("windowWidth",Integer.toString(e.getComponent().getWidth()));
//    	}
    }

    /**
     * Method to supplie the actions with the text load file
     * @return the objects language object.
     */
	public LocalizedTexts getLanguage() {
		return this.lang;
	}

	/**
	 * returns the ShowGraphAction
	 * @return the action object
	 */
	public ShowGraphAction getShowGraphAction() {
		return showGraphAction;
	}



	
}

