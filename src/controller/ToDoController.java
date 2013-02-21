package controller;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;

import model.Category;
import model.LocaliziedTexts;
import model.TableToDoItemModel;
import model.ToDoItem;
import model.ToDoItemModel;
import view.MainView;
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
    private Config conf;
    private LocaliziedTexts lang;
    private MainView view;


    public ToDoController(ToDoItemModel newModel, LocaliziedTexts newLang){
        this.model = newModel;
        this.lang = newLang;
        this.add =
                new AddAction(
                        lang.getText("ui.mainview.menu.edit.add"),
                        createNavigationIcon("/Add16"),
                        "Press to add ToDoItem",
                        KeyEvent.VK_A, this);
        this.edit =
                new EditAction(
                        lang.getText("ui.mainview.menu.edit.edit"),
                        createNavigationIcon("/Edit16"),
                        "Press to edit ToDoItem",
                        KeyEvent.VK_E, this);
        this.delete =
                new DeleteAction(
                        lang.getText("ui.mainview.menu.edit.delete"),
                        createNavigationIcon("/Delete16"),
                        "Press to delete ToDoItem",
                        KeyEvent.VK_D, this);
        this.ok =
                new OkAction(
                        "Ok",
                        createNavigationIcon("uknownicon"),
                        "Press to save ToDoItem",
                        KeyEvent.VK_O, this);
        this.cancel =
                new CancelAction(
                        "Cancel",
                        createNavigationIcon("unkownicon"),
                        "Press to abort",
                        KeyEvent.VK_C, this);
        this.language =
                new ChangeLanguageAction(
                        lang.getText("ui.mainview.menu.file.changeLanguage"),
                        createNavigationIcon("/Information16"),
                        "Press to change language",
                        KeyEvent.VK_L, this);
        this.about =
                new AboutAction(
                        lang.getText("ui.mainview.menu.help.about"),
                        createNavigationIcon("/About16"),
                        "Press to get info",
                        KeyEvent.VK_F, this);
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

    /**
     * Fetches all the categories.
     * @return list with categories
     */
    public List<Category> getCategories(){
    	return model.getAllCategories();
    }
    
    public ToDoItem getEditItem(int index) {
        //model.getItem(index);
        return null;
    }

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

    public ChangeLanguageAction getLanguage() {
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
     * This method is used by the controller to update the language of all the actions it has control over.
     * @param lang
     */
    public void updateLanguage(Locale arg) {
        //TODO: Functionality to change the language.
        Locale.setDefault(arg);
        conf.setProp("locale",arg.getLanguage());
        lang.refreshTexts();
        setLanguage();
    }
    /** MAJOR TEMPORARY CODE!!!! Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createNavigationIcon(String imageName) {
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
    private void setLanguage() {
        this.edit.updateLanguage(lang);
        this.add.updateLanguage(lang);
        this.delete.updateLanguage(lang);
        this.language.updateLanguage(lang);
        this.cancel.updateLanguage(lang);
        this.about.updateLanguage(lang);
        view.table.setModel(new TableToDoItemModel(model, lang));
        this.view.repaint();
    }
    
    /**
     * This method detects all movements of observed windows and saves the new position to the config,
     * so that we can show them at the same position at next start.
     * @see ComponentAdapter#componentMoved(ComponentEvent)
     */
    @Override
    public void componentMoved(ComponentEvent e) {
		System.out.println("DEBUG: any component moved");
    	//save position of MainWindow
    	if(e.getComponent().getClass().equals(MainView.class)){
    		System.out.println("DEBUG: mainview moved");
    		this.conf.setProp("windowXPos",Integer.toString(e.getComponent().getX()));
    		this.conf.setProp("windowYPos",Integer.toString(e.getComponent().getY()));
    	}
    }
    
    /**
     * This method detects if a observed window is resized and saves the new position to the config,
     * so that we can show them with the same size at next start.
     * @see ComponentAdapter#componentResized(ComponentEvent)
     */
    @Override
    public void componentResized(ComponentEvent e) {
		System.out.println("DEBUG any component got resized");
    	//save position of MainWindow
    	if(e.getComponent().getClass().equals(MainView.class)){
    		System.out.println("DEBUG mainview got resized");
    		this.conf.setProp("windowHeight",Integer.toString(e.getComponent().getHeight()));
    		this.conf.setProp("windowWidth",Integer.toString(e.getComponent().getWidth()));
    	}
    }
}

