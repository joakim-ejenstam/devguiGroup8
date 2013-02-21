import model.LocaliziedTexts;
import model.TableToDoItemModel;
import model.ToDoItemModel;
import model.XMLFileToDoItemModel;
import view.MainView;
import controller.Config;
import controller.ToDoController;
import exceptions.LoadModelException;

import java.util.Locale;

import javax.swing.UIManager;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-02-07
 * Time: 09:50
 */
public class ApplicationStartup {

	private Config config = null; //the configuration values of the application
	
    public ApplicationStartup(String[] args) {
    	//set look & feel of system
    	try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (Exception e) {
	    	//we don't have to do anything, as the dault l&f will be used now anyway
	    }
        try {
    		config = Config.getInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.exit(1); //we can't run without any configuration values.
		}
    	
        ToDoItemModel model = null;
		try {
			model = new XMLFileToDoItemModel();
		} catch (LoadModelException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(1);//without a model our application can't run...
		}
        LocaliziedTexts lang = new LocaliziedTexts(config);
		final ToDoController controller = new ToDoController(model,lang);
        final TableToDoItemModel tbModel = new TableToDoItemModel(model,lang);
        controller.setConfig(config);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	MainView view = new MainView(controller, tbModel);
            	view.createAndShowGUI(config);
            }
        });
        this.config.saveApplicationProperties(); //TODO: is this here the right place to do it? It should happen when the application gets closed
	}

    
    
	public static void main(String[] args) {
    	new ApplicationStartup(args);
    }
}
