package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import controller.AddAction;
import controller.TodoMouseListener;
import model.*;

import org.java.ayatana.ApplicationMenu;

import controller.Config;
import controller.ToDoController;

/**
 * This class sets up the main window of the application
 * @author Emma Rangert
 * 
 */

@SuppressWarnings("serial")
public class MainView extends JFrame implements Observer, TableModelListener{

	private ToDoController controller;
    private TableToDoItemModel tableModel;
    private OverdueListModel overdueListModel;
    private DoneListModel doneListModel;
    private DeletedListModel deletedListModel;
    private LocalizedTexts lang;
    public JTable table;
    
    private JMenu file;
    private JMenu edit;
    private JMenu help;
    private JPopupMenu popupMenu;
    
    private ButtonGroup viewItems = new ButtonGroup();
    final JLabel timeLabel = new JLabel(); 
    
    //View button group for showing certain lists
    private JRadioButton viewDone;
    private JRadioButton viewOverDue;
    private JRadioButton viewPending;
    private JRadioButton viewDeleted;

    public JList doneList;
    public JList overdueList;
    public JList deletedList;
    
    /**
     * Constructor
     * @param newController the controller 
     * @param tbModel the underlaying table model 
     * @param overdueModel the over due item list model
     * @param doneModel the done item list model
     * @param deleteModel the deleted item list model
     * @param newLang the language resource
     */
    public MainView(ToDoController newController, TableToDoItemModel tbModel, OverdueListModel overdueModel, DoneListModel doneModel, DeletedListModel deleteModel, LocalizedTexts newLang) {
        this.controller = newController;
        //the main view needs a TableModel as it uses a JTable
        //(as this model is tightly bound to the JTable, it's probably okay to 
        //create it here and not get it injected by the controller)
        this.tableModel = tbModel;
        this.overdueListModel = overdueModel;
        this.doneListModel = doneModel;
        this.deletedListModel = deleteModel;
        this.lang = newLang;
        //add this view as a listener to the changes of the model
        controller.addObserver(this);
    }

    /**
	 * Method for setting up the menu bar and adding it to the main frame.
	 * @param frame the frame where to add the menu bar
	 */
	@SuppressWarnings("static-access")
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
        JMenuItem setTodo = new JMenuItem(controller.getDoneAction());
        JMenuItem showGraph = new JMenuItem("Show Graph");
        showGraph.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new GraphPanel(new StupidToDoItemModel());
            }
        });
        showGraph.setIcon(controller.createNavigationIcon("/Chart1"));

		// Set up menu bar
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(help);
		file.add(chooseLanguage);
        file.add(showGraph);
		help.add(about);
		edit.add(addTodo);
		edit.add(editTodo);
		edit.add(deleteTodo);
        edit.add(setTodo);
	}
	
	/**
	 * Method for creating and setting up the table for showing to do items to the user.
	 * @return Returns the table.
	 */
	private JTable createTable() {
		JTable table = new JTable(tableModel);
		table.setAutoCreateRowSorter(true); //removed the autosorting so we can implement our own sorting algorithm.
        table.getTableHeader().setReorderingAllowed(false);
		table.getModel().addTableModelListener(this);//should catch changes in the table model once we promote them with the fireUpdate() below.
	    
		table.getTableHeader().setFont(new Font( "Arial" , Font.PLAIN, 13));
		
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
		
	    // list
        this.doneList = new JList(doneListModel);
        this.doneList.addMouseListener(new TodoMouseListener(controller));
        this.doneList.setCellRenderer(new ToDoListRenderer());

        this.deletedList = new JList(deletedListModel);
        this.deletedList.addMouseListener(new TodoMouseListener(controller));
        this.deletedList.setCellRenderer(new ToDoListRenderer());

        this.overdueList = new JList(overdueListModel);
        this.overdueList.addMouseListener(new TodoMouseListener(controller));
        this.overdueList.setCellRenderer(new ToDoListRenderer());
        
        // table
        this.table = createTable();
        this.table.addMouseListener(new TodoMouseListener(controller));
        this.table.getColumnModel().getColumn(3).setCellRenderer(new ToDoTableRenderer());
        	    
        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        JScrollPane donePane = new JScrollPane(doneList);
        JScrollPane deletePane = new JScrollPane(deletedList);
        JScrollPane overDuePane = new JScrollPane(overdueList);

	    // Text fields and buttons
	    JTextField inputFld = new JTextField();
	    JButton addBtn = new JButton(controller.getAddAction());

        JTabbedPane testPane = new JTabbedPane();
        testPane.addTab(lang.getText("ui.mainview.radiobutton.viewpending"), null, scrollPane);
        testPane.addTab(lang.getText("ui.mainview.radiobutton.viewdone"), null, donePane);
        testPane.addTab(lang.getText("ui.mainview.radiobutton.viewoverdue"),null,overDuePane);
        testPane.addTab(lang.getText("ui.mainview.radiobutton.viewdeleted"),null,deletePane);
        testPane.addTab("Graph",null,new GraphPanel(new StupidToDoItemModel()));
        testPane.setMnemonicAt(0, KeyEvent.VK_1);
        testPane.setMnemonicAt(1, KeyEvent.VK_2);
        testPane.setMnemonicAt(2, KeyEvent.VK_3);
        testPane.setMnemonicAt(3, KeyEvent.VK_4);
        testPane.setMnemonicAt(4, KeyEvent.VK_5);

        // Add to pane
	    pane.add(testPane, BorderLayout.CENTER);
		pane.add(northPanel, BorderLayout.NORTH);
	    pane.add(southPanel, BorderLayout.SOUTH);

        testPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        testPane.setTabPlacement(JTabbedPane.TOP);
	    // Set layouts and alignment
	    southPanel.setLayout(new BorderLayout());
	    northPanel.setLayout(new BorderLayout());
	    inputFld.setHorizontalAlignment(JTextField.LEFT);

	    // Set up south panel
	    southPanel.add(inputFld, BorderLayout.CENTER);
	    southPanel.add(addBtn, BorderLayout.EAST);
	    
	    // Set up north panel 
	    northPanel.add(northLeftPanel, BorderLayout.CENTER);
	    northPanel.add(northRightPanel, BorderLayout.EAST);

	    // Adding clock to north panels 
	    northRightPanel.add(timeLabel);

	    
	    // creates the clock
	    final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	    ActionListener timerListener = new ActionListener()  
	    {  
	        public void actionPerformed(ActionEvent e)  
	        {  
	            Date date = new Date();  
	            String time = timeFormat.format(date);  
	            timeLabel.setText(time); 
	            timeLabel.setFont(new Font("Arial",Font.PLAIN, 15));
	        }  
	    };  
	    Timer timer = new Timer(1000, timerListener);  
	    // to make sure it doesn't wait one second at the start  
	    timer.setInitialDelay(0);  
	    timer.start();  
		
	    
	    scrollPane.setColumnHeaderView(createTable().getTableHeader());
        ((AddAction)addBtn.getAction()).setTextField(inputFld);
        
        inputFld.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    JTextField textField = (JTextField) e.getSource();
                    String text = textField.getText();
                    controller.addItem(text);
                    textField.setText("");
                }
                //String text = textField.getText();
                //textField.setText(text.toUpperCase());

            }
        });
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
		this.tableModel.fireTableDataChanged(); //informs tableModel-listeners, which in our case is this class as specified above
        this.doneListModel.getDoneItems();
        this.deletedListModel.getDeletedItems();
        this.overdueListModel.getOverdueItems();
	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		// TODO Auto-generated method stub
		//not sure what we have to do here...
	}

	/**
	 * Method for updating the language of the user interface
	 * @param lang the language to update to. 
	 */
    public void updateLanguage(LocalizedTexts lang) {
        this.file.setText(lang.getText("ui.mainview.menu.file"));
        this.edit.setText(lang.getText("ui.mainview.menu.edit"));
        this.help.setText(lang.getText("ui.mainview.menu.help"));
        this.viewPending.setText(lang.getText("ui.mainview.radiobutton.viewpending"));
        this.viewDone.setText(lang.getText("ui.mainview.radiobutton.viewdone"));
        this.viewOverDue.setText(lang.getText("ui.mainview.radiobutton.viewoverdue"));
        this.viewDeleted.setText(lang.getText("ui.mainview.radiobutton.viewdeleted"));
        this.setTitle(lang.getText("ui.mainview.windowTitle"));
        this.validate();
        this.repaint();
    }

    /**
     * Method for returning the table. 
     * @return the table
     */
    public JTable getTable() {
        return this.table;
    }
}
