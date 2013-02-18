package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import controller.Config;
import controller.ToDoController;

/**
 * This class sets up the main window of the application
 * @author Emma Rangert
 */

@SuppressWarnings("serial")

public class MainView extends JFrame {
    private static ToDoController controller;
	
	
    public MainView(ToDoController newController) {
        this.controller = newController;
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
	 * Method for adding components to the content pane. 
	 * @param pane the pane to where the components are added
	 */
	private static void addComponentsToPane(Container pane) {
		
		// Labels
		JLabel item = new JLabel("Item");
		JLabel category = new JLabel("Category");
		JLabel priority = new JLabel("Priority");
	    JLabel deadline = new JLabel("Deadline");
		
		// Table
		JTable table = new JTable();
		
		// Scroll pane
	    ScrollPane scrollPane = new ScrollPane();

	    // Panels
	    JPanel northPanel = new JPanel();
	    JPanel southPanel = new JPanel();
	    
	    // Text fields
	    JTextField inputFld = new JTextField();
	    
	    // Buttons
	    JButton addBtn = new JButton(controller.getAddAction());
	    
	    /*
	    addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EditTaskFrame editview = new EditTaskFrame("Edit tasks");
				editview.setSize(400,400);
				editview.setVisible(true);
			}
		});
        */
	    pane.setLayout(new BorderLayout());
	    scrollPane.add(table);
	    
	    // Add to pane
	    pane.add(scrollPane, BorderLayout.CENTER);
		pane.add(northPanel, BorderLayout.NORTH);
	    pane.add(southPanel, BorderLayout.SOUTH);
	    
	    // Set layouts
	    southPanel.setLayout(new BorderLayout());
	    northPanel.setLayout(new GridLayout());
	    
	    inputFld.setHorizontalAlignment(JTextField.LEFT);

	    // Set up south panel
	    southPanel.add(inputFld, BorderLayout.CENTER);
	    southPanel.add(addBtn, BorderLayout.EAST);
        southPanel.setBorder(BorderFactory.createTitledBorder("Add to do"));

	    // Set up north panel
	    northPanel.add(item);
	    northPanel.add(category);
	    northPanel.add(priority);
	    northPanel.add(deadline);
	    northPanel.setBorder(BorderFactory.createTitledBorder("To do's"));
	    
	    table.setBorder(BorderFactory.createTitledBorder(""));	    
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

}
