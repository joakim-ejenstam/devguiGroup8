package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

//import model.TableToDoItemModel;

import controller.Config;
import controller.ToDoController;
import model.TableToDoItemModel;

/**
 * This class sets up the main window of the application
 * @author Emma Rangert
 */

@SuppressWarnings("serial")

public class MainView extends JFrame implements Observer {

    private static ToDoController controller;
    private static TableToDoItemModel tableModel;

    public MainView(ToDoController newController, TableToDoItemModel tbModel) {
        MainView.controller = newController;
        MainView.tableModel = tbModel;
        controller.addObserver(this);
    }

    /**
	 * Method for setting up the menu bar and adding it to the main frame.
	 * @param frame the frame where to add the menu bar
	 */
	private static void addMenuBar(JFrame frame) {

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		// Menus
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		JMenu help = new JMenu("Help");
		JMenu chooseLanguage = new JMenu("Change language");

		// Sub menus
		JMenuItem about = new JMenuItem("About");
		JMenuItem addTodo = new JMenuItem("Add todo");
		JMenuItem editTodo = new JMenuItem("Edit todo");
		JMenuItem english = new JMenuItem("English");
		JMenuItem german = new JMenuItem("German");
		JMenuItem swedish = new JMenuItem("Swedish");

		// Set up menu bar
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(help);
		file.add(chooseLanguage);
		help.add(about);
		edit.add(addTodo);
		edit.add(editTodo);
		chooseLanguage.add(english);
		chooseLanguage.add(german);
		chooseLanguage.add(swedish);

		/* For later use when we add more sub menus to file menu:
		file.addSeparator();
		*/
	}

	/**
	 * Method for creating and setting up the table for showing to do items to the user.
	 * @return Returns the table.
	 */
	private static JTable createTable() {

		// TODO: this is temporary added to show the headings in the table
		// should be a TableToDoItemModel and not DefaultTableModel,
		// however I did not yet manage to get the headings working that way
		String[] columnLabels = {"Title","Category","Priority","Due","Done"};

		//DefaultTableModel temp = new DefaultTableModel(columnLabels,0);
	    //JTable table = new JTable(tableModel);
	    JTable table = new JTable(tableModel);

	    return table;
	}

	/**
	 * Method for adding components to the content pane.
	 * @param pane the pane to where the components are added
	 */
	private static void addComponentsToPane(Container pane) {

		pane.setLayout(new BorderLayout());

		// Panels
	    JPanel northPanel = new JPanel();
	    JPanel southPanel = new JPanel();

	    // Scroll pane
	    JScrollPane scrollPane = new JScrollPane(createTable());

	    // Text fields and buttons
	    JTextField inputFld = new JTextField();
	    JButton addBtn = new JButton(controller.getAddAction());

	    // Add to pane
	    pane.add(scrollPane, BorderLayout.CENTER);
		pane.add(northPanel, BorderLayout.NORTH);
	    pane.add(southPanel, BorderLayout.SOUTH);

	    // Set layouts and alignment
	    southPanel.setLayout(new BorderLayout());
	    northPanel.setLayout(new GridLayout());
	    inputFld.setHorizontalAlignment(JTextField.LEFT);

	    // Set up south panel
	    southPanel.add(inputFld, BorderLayout.CENTER);
	    southPanel.add(addBtn, BorderLayout.EAST);
        southPanel.setBorder(BorderFactory.createTitledBorder("Add to do"));

	    scrollPane.setBorder(BorderFactory.createTitledBorder("To do's"));
	    scrollPane.setColumnHeaderView(createTable().getTableHeader());
	}

	/**
	 * Method for creating and showing the user interface.
	 * @param config the instance to be created and shown
	 */
	public void createAndShowGUI(Config config) {

		JFrame frame = new JFrame("The Greight TODO Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(Integer.valueOf(config.getProp("windowXPos")),
				Integer.valueOf(config.getProp("windowYPos")),
				Integer.valueOf(config.getProp("windowWidth")),
				Integer.valueOf(config.getProp("windowHeight")));

		addComponentsToPane(frame.getContentPane());
		addMenuBar(frame);

		frame.setVisible(true);
		frame.pack();
    }

    /**
     * This method is always called if something has changed in the observed object.
     * We will have to update our data now to make sure, that we show everything up to date.
     * @see  java.util.Observer#update(Observable, Object)
     * @param Obersable the observed object
     * @param Object some arg that the observed object <em>might</em> set
     */
    @Override
    public void update(Observable o, Object arg) {
        // TODO Auto-generated method stub

    }

}
