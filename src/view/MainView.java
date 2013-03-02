package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import controller.AddAction;
import model.LocalizedTexts;
import model.TableToDoItemModel;

import org.java.ayatana.ApplicationMenu;

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
    private LocalizedTexts lang;
    public JTable table;
    private JMenu file;
    private JMenu edit;
    private JMenu help;
    
    final JLabel timeLabel = new JLabel(); 
    
    //View button group for showing certain lists
    private JRadioButton viewDone;
    private JRadioButton viewOverDue;
    private JRadioButton viewAll;

    public MainView(ToDoController newController, TableToDoItemModel tbModel, LocalizedTexts newLang) {
        this.controller = newController;
        //the main view needs a TableModel as it uses a JTable
        //(as this model is tightly bound to the JTable, it's probably okay to 
        //create it here and not get it injected by the controller)
        this.tableModel = tbModel;
        
        this.lang = newLang;
        
        viewAll = new JRadioButton("All", true);//lang.getText("ui.mainview.radiobutton.viewall"), true);
        viewDone = new JRadioButton("Done", false);//lang.getText("ui.mainview.radiobutton.viewdone"), false);
        viewOverDue = new JRadioButton("Overdue", false);//lang.getText("ui.mainview.radiobutton.viewoverdue"), false);
        
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
		file = new JMenu(lang.getText("ui.mainview.menu.file"));
		edit = new JMenu(lang.getText("ui.mainview.menu.edit"));
		help = new JMenu(lang.getText("ui.mainview.menu.help"));
		JMenuItem chooseLanguage = new JMenuItem(controller.getLanguageAction());
		
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
	    JPanel northRightPanel = new JPanel();
	    JPanel northLeftPanel = new JPanel();
	    JPanel southPanel = new JPanel();
		
	    // Scroll pane
        this.table = createTable();
        this.table.addMouseListener(new TodoMouseListener());
	    JScrollPane scrollPane = new JScrollPane(table);
	    
	    // Text fields and buttons
	    JTextField inputFld = new JTextField();
	    JButton addBtn = new JButton(controller.getAddAction());
	    
	    // Add to pane
	    pane.add(scrollPane, BorderLayout.CENTER);
		pane.add(northPanel, BorderLayout.NORTH);
	    pane.add(southPanel, BorderLayout.SOUTH);
	    
	    // Set layouts and alignment
	    southPanel.setLayout(new BorderLayout());
	    northPanel.setLayout(new BorderLayout());//FlowLayout(FlowLayout.LEFT));
	    inputFld.setHorizontalAlignment(JTextField.LEFT);

	    // Set up south panel
	    southPanel.add(inputFld, BorderLayout.CENTER);
	    southPanel.add(addBtn, BorderLayout.EAST);
	    
	    // Set up north panel 
	    northPanel.add(northLeftPanel, BorderLayout.CENTER);
	    northPanel.add(northRightPanel, BorderLayout.EAST);

	    // Adding radio buttons and clock to north panels 
	    northLeftPanel.add(viewAll);
	    northLeftPanel.add(viewDone);
	    northLeftPanel.add(viewOverDue);
	    northRightPanel.add(timeLabel);
	    
	    // creates the clock
	    final DateFormat timeFormat = new SimpleDateFormat("HH:mm");  
	    ActionListener timerListener = new ActionListener()  
	    {  
	        public void actionPerformed(ActionEvent e)  
	        {  
	            Date date = new Date();  
	            String time = timeFormat.format(date);  
	            timeLabel.setText(time);  
	        }  
	    };  
	    Timer timer = new Timer(1000, timerListener);  
	    // to make sure it doesn't wait one second at the start  
	    timer.setInitialDelay(0);  
	    timer.start();  
		
	    
	    scrollPane.setColumnHeaderView(createTable().getTableHeader());
        ((AddAction)addBtn.getAction()).setTextField(inputFld);
	}

	/**
	 * Method for creating and showing the user interface. 
	 * @param config the instance to be created and shown
	 */
	public void createAndShowGUI(Config config) {
		
		JFrame frame = new JFrame(lang.getText("ui.mainview.windowTitle"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(Integer.valueOf(config.getProp("windowWidth")), 
				Integer.valueOf(config.getProp("windowHeight"))));
		frame.setLocation(Integer.valueOf(config.getProp("windowXPos")), Integer.valueOf(config.getProp("windowYPos")));
		
		addComponentsToPane(frame.getContentPane());
		addMenuBar(frame);
		
		//support for ubuntu global menu, using the jayanta-lib
		ApplicationMenu.tryInstall(frame);
		
		frame.pack();
		frame.setVisible(true);
		
		//we add the controller as a listener to observe changes
		frame.addComponentListener(this.controller);
        controller.setTable(table);
        System.out.println("DEBUG frame added component listener");
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
		this.tableModel.fireTableDataChanged(); //informs tableModel-listeners, which in our case is this class as specified above
	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		System.out.println("DEBUG tableChanged()");
		// TODO Auto-generated method stub
		//not sure what we have to do here...
	}

    public void updateLanguage(LocalizedTexts lang) {
        this.file.setText(lang.getText("ui.mainview.menu.file"));
        this.edit.setText(lang.getText("ui.mainview.menu.edit"));
        this.help.setText(lang.getText("ui.mainview.menu.help"));
        this.viewAll.setText(lang.getText("ui.mainview.radiobutton.viewall"));
        this.viewDone.setText(lang.getText("ui.mainview.radiobutton.viewdone"));
        this.viewOverDue.setText(lang.getText("ui.mainview.radiobutton.viewoverdue"));
        this.setTitle(lang.getText("ui.mainview.windowTitle"));
        this.validate();
        this.repaint();
    }

    public JTable getTable() {
        return this.table;
    }
    protected class TodoMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2 && !e.isConsumed()) {
                e.consume();
                System.out.println("Double click");
//handle double click.
            }
            else
              System.out.println("Click");

            System.out.println(e.getButton());


        }

        @Override
        public void mousePressed(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
