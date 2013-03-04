import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
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
	
	//Color theme
	private static final String THEME = "data"+File.separator+"theme.properties";
	
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
    		Properties colorProp = loadColorProperties();
    		System.out.println("heej");
    		UIManager.put("Table.showGrid", true);
    		
    		//TEMPORARY TEST
    		if (System.getProperty("os.name").toLowerCase().startsWith("mac os x")){
    			UIManager.put("apple.laf.useScreenMenuBar", "true");
    		}

    		UIManager.put("nimbusBase", Color.decode(colorProp.getProperty("ui.primeColor1")));
    		UIManager.put("nimbusBlueGrey",Color.decode(colorProp.getProperty("ui.primeColor2")));
    		UIManager.put("control",Color.decode(colorProp.getProperty("ui.primeColor3")));
    		
    	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
    	        if ("Nimbus".equals(info.getName())) {
    	            UIManager.setLookAndFeel(info.getClassName());
    	            break;
    	        }
    	    }
    	} catch (Exception e) {
    	    // If Nimbus is not available, you can set the GUI to another look and feel.
        	try {
    	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	       //MetalLookAndFeel.setCurrentTheme(new CustomTheme());
    	    } catch (Exception ex) {
    	    	//we don't have to do anything, as the dault l&f will be used now anyway
    	    }
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

	/**
	 * Loads the color configuration
	 */
	private Properties loadColorProperties(){
		Properties colorTheme = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream(THEME);
			colorTheme.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return colorTheme;
	}
    
	public static void main(String[] args) {
    	new ApplicationStartup(args);
    }
}
