package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import model.TableToDoItemModel;
import controller.Config;
import controller.ToDoController;

/**
 * This class sets up the main window of the application
 * @author Emma Rangert
 */

@SuppressWarnings("serial")
public class MainView extends JFrame implements Observer, TableModelListener {
	
    private ToDoController controller;
    private TableToDoItemModel tableModel;
	
    public MainView(ToDoController newController, TableToDoItemModel tbModel) {
        this.controller = newController;
        //the main view needs a TableModel as it uses a JTable
        //(as this model is tightly bound to the JTable, it's probably okay to 
        //create it here and not get it injected by the controller)
        this.tableModel = tbModel;
        
        //add this view as a listener to the changes of the model
        controller.addObserver(this);
        System.out.println("DEBUG added view as observer to model");
    }

    /**
	 * Method for setting up the menu bar and adding it to the main frame.
	 * @param frame the frame where to add the menu bar
	 */
	private void addMenuBar(JFrame frame) {
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		// Menus
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		JMenu help = new JMenu("Help");
		JMenuItem chooseLanguage = new JMenuItem(controller.getLanguage());

		// Sub menus
		JMenuItem about = new JMenuItem(controller.getAboutAction());
		JMenuItem addTodo = new JMenuItem(controller.getAddAction());
		JMenuItem editTodo = new JMenuItem(controller.getEditAction());
		JMenuItem deleteTodo = new JMenuItem(controller.getDeleteAction());
		
		// Set up menu bar
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(help);
		file.add(chooseLanguage);
		help.add(about);
		edit.add(addTodo);
		edit.add(editTodo);
		edit.add(deleteTodo);

		/* For later use when we add more sub menus to file menu:
		file.addSeparator();
		*/
	}
	
	/**
	 * Method for creating and setting up the table for showing to do items to the user.
	 * @return Returns the table.
	 */
	private JTable createTable() {
		System.out.println("DEBUG: Col: "+tableModel.getColumnCount()
				+" Row: "+tableModel.getRowCount());
		JTable table = new JTable(tableModel);
		table.setAutoCreateRowSorter(true);
		table.getModel().addTableModelListener(this);//should catch changes in the table model once we promote them with the fireUpdate() below.
	    //System.out.println(tableModel.getColumnName(0));
	    
	    return table;
	}
	
	/**
	 * Method for adding components to the content pane. 
	 * @param pane the pane to where the components are added
	 */
	private void addComponentsToPane(Container pane) {
			    
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
	 * @param Observable the observed object
	 * @param Object some arg that the observed object <em>might</em> set
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("DEBUG Update from model");
		// TODO Auto-generated method stub
		this.tableModel.fireTableDataChanged(); //informs tableModel-listeners, which in our case is this class as specified above
	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		System.out.println("DEBUG tableChanged()");
		// TODO Auto-generated method stub
		//not sure what we have to do here...
	}
}
