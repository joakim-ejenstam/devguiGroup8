import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
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
 * The starter class which glues the model view and controller
 * @author Joakim, Mattias
 */
public class ApplicationStartup {

	private Config config = null; // the configuration values of the application

	/**
	 * The constructor
	 * @param args
	 */
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
	}
	
	
	/**
	 * The main method for the whole app
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		// change program name
		System.setProperty(
				"com.apple.mrj.application.apple.menu.about.name","Greigth ToDo Manager");
				//lang.getText("ui.mainview.windowTitle"));

		new ApplicationStartup(args);
	}
}
