import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import model.LocalizedTexts;
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
    	config = Config.getInstance();
		
        ToDoItemModel model = null;
		try {
			model = new XMLFileToDoItemModel();
		} catch (LoadModelException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(1);//without a model our application can't run...
		}
        final LocalizedTexts lang = new LocalizedTexts(config);
		final ToDoController controller = new ToDoController(model,lang);
        final TableToDoItemModel tbModel = new TableToDoItemModel(model,lang);
        controller.setConfig(config);
        
        //let's do some ui finetuning
        //apple needs of course some extra attention...
    	if (System.getProperty("os.name").toLowerCase().startsWith("mac os x")) {
    		//move program menu up to system bar
    		System.setProperty("apple.laf.useScreenMenuBar", "true");
    		//change program name
    		System.setProperty("com.apple.mrj.application.apple.menu.about.name", lang.getText("ui.mainview.windowTitle"));
    	}
    	//set look & feel of system
    	try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (Exception e) {
	    	//we don't have to do anything, as the dault l&f will be used now anyway
	    }
        
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
