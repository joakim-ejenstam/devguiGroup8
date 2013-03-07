import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;
import model.DoneListModel;
import model.OverdueListModel;
import model.DeletedListModel;
import model.LocalizedTexts;
import model.TableToDoItemModel;
import model.ToDoItem;
import model.ToDoItemModel;
import model.XMLFileToDoItemModel;
import view.CustomTheme;
import view.MainView;
import controller.Config;
import controller.ToDoController;
import exceptions.LoadModelException;

/**
 * @author Joakim, Mattias
 */
public class ApplicationStartup {

	private Config config = null; // the configuration values of the application

	// Color theme
	private static final String THEME = "data" + File.separator
			+ "theme.properties";

	public ApplicationStartup(String[] args) {
		config = Config.getInstance();

		ToDoItemModel model = null;
		try {
			model = new XMLFileToDoItemModel();
		} catch (LoadModelException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(1);// without a model our application can't run...
		}
		final LocalizedTexts lang = new LocalizedTexts(config);
		final ToDoController controller = new ToDoController(model, lang);
		final TableToDoItemModel tbModel = new TableToDoItemModel(model, lang);
		final OverdueListModel overdueModel = new OverdueListModel(model);
		final DoneListModel doneModel = new DoneListModel(model);
		final DeletedListModel deleteModel = new DeletedListModel(model);
		controller.setConfig(config);

		// let's do some ui finetuning
		// apple needs of course some extra attention...
		if (System.getProperty("os.name").toLowerCase().startsWith("mac os x")) {
			// move program menu up to system bar
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			// change program name
			System.setProperty(
					"com.apple.mrj.application.apple.menu.about.name",
					lang.getText("ui.mainview.windowTitle"));

			try {
				// Apple's menu is not compatible with the nimbus theme with
				// custom colors
				// so we set to system default instead
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
			} catch (Exception ex) {
				// default l&f will be used
			}
		} else {
			/*
			 * // else set custom look and feel for the other OS's try {
			 * Properties colorProp = loadColorProperties();
			 * UIManager.put("Table.showGrid", true);
			 * 
			 * // Add custom colors from the property
			 * UIManager.put("nimbusBase",
			 * Color.decode(colorProp.getProperty("ui.primeColor1")));
			 * UIManager.put("nimbusBlueGrey",
			 * Color.decode(colorProp.getProperty("ui.primeColor2")));
			 * UIManager.put("control",
			 * Color.decode(colorProp.getProperty("ui.primeColor3")));
			 * 
			 * for (LookAndFeelInfo info : UIManager
			 * .getInstalledLookAndFeels()) { if
			 * ("Nimbus".equals(info.getName())) {
			 * UIManager.setLookAndFeel(info.getClassName()); break; } } } catch
			 * (Exception e) { // default theme will be used. } }
			 */
			// NEW CODE CHECK IF U GET THE WEIRD ERROR
			try {
				MetalLookAndFeel mlf = new MetalLookAndFeel();
				MetalLookAndFeel.setCurrentTheme(new CustomTheme());
				UIManager.setLookAndFeel(mlf);
			} catch (UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainView view = new MainView(controller, tbModel, overdueModel,
						doneModel, deleteModel, lang);
				view.createAndShowGUI(config);
			}
		});

		StringBuilder message = new StringBuilder(
				lang.getText("ui.mainview.optionpane.remindertext"));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date today = null;
		int counter = 0;
		try {
			today = df.parse(df.format(new Date()));

			for (ToDoItem item : model.getAllToDoItems()) {
				if (item.getDueDate() != null) {
					if (item.getDueDate().compareTo(today) == 0
							|| item.getDueDate().compareTo(today) == -1) {
						counter++;
						message.append("- " + item.getTitle() + ", ");
						message.append(df.format(item.getDueDate()) + "\n\n");
					}
				}

			}
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Error parsing date");
		}
		if (counter > 0) {
			JOptionPane.showMessageDialog(null, message,
					lang.getText("ui.mainview.optionpane.remindertitle"),
					JOptionPane.INFORMATION_MESSAGE);
		}

		// //following stuff happens at exiting the application
		// Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		//
		// public void run() {
		// //save state of the application
		// try {
		// config = Config.getInstance();
		// config.saveApplicationProperties();
		// System.out.println("DEBUG state got saved to file!");
		// } catch (InstantiationException e) {
		// e.printStackTrace();//we can't do anything here anymore, the config
		// will just not be saved.
		// }
		// }
		// }));
	}

	
	/**
	 * Loads the color configuration
	 */
	/*
	private Properties loadColorProperties() {
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
	*/
	public static void main(String[] args) {
		new ApplicationStartup(args);
	}
}
