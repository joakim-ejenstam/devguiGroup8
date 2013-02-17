import controller.Config;
import controller.ToDoController;
import model.XMLFileToDoItemModel;
import view.MainView;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-02-07
 * Time: 09:50
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationStartup {

	private Config config = null; //the configuration values of the application
	
    public ApplicationStartup(String[] args) {
    	try {
    		config = Config.getInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.exit(1); //we can't run without any configuration values.
		}
        XMLFileToDoItemModel model = new XMLFileToDoItemModel();
        final ToDoController controller = new ToDoController(model);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	MainView view = new MainView(controller);
            	view.createAndShowGUI(config);
            }
        });
        this.config.saveApplicationProperties(); //TODO: is this here the right place to do it? It should happen when the application gets closed
	}

    
    
	public static void main(String[] args) {
    	new ApplicationStartup(args);
    }
}
