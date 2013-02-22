import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import model.LocaliziedTexts;
import model.TableToDoItemModel;
import model.ToDoItemModel;
import model.XMLFileToDoItemModel;
import view.MainView;
import controller.Config;
import controller.ToDoController;
import exceptions.LoadModelException;

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
        final LocaliziedTexts lang = new LocaliziedTexts(config);
		final ToDoController controller = new ToDoController(model,lang);
        final TableToDoItemModel tbModel = new TableToDoItemModel(model,lang);
        controller.setConfig(config);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	MainView view = new MainView(controller, tbModel,lang);
            	view.createAndShowGUI(config);
            }
        });
        
//        //following stuff happens at exiting the application 
//        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//
//            public void run() {
//            	//save state of the application
//            	 try {
//             		config = Config.getInstance();
//             		config.saveApplicationProperties();
//             		System.out.println("DEBUG state got saved to file!");
//         		} catch (InstantiationException e) {
//         			e.printStackTrace();//we can't do anything here anymore, the config will just not be saved.
//         		}
//            }
//        }));
	}

    
    
	public static void main(String[] args) {
    	new ApplicationStartup(args);
    }
}
