package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import model.DeletedListModel;
import model.DoneListModel;
import model.LocalizedTexts;
import model.OverdueListModel;
import model.TableToDoItemModel;

import org.java.ayatana.ApplicationMenu;

import controller.AddAction;
import controller.Config;
import controller.ToDoController;
import controller.TodoMouseListener;

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
    
    private JFrame frame;
    private JMenu file;
    private JMenu edit;
    private JMenu help;
    private JTabbedPane testPane; 
    
    final JLabel timeLabel = new JLabel(); 
    
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
        JMenuItem showGraph = new JMenuItem(controller.getShowGraphAction());

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
		table.setAutoCreateRowSorter(true);
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


        this.testPane = new JTabbedPane();
        this.testPane.addTab(lang.getText("ui.mainview.radiobutton.viewpending"), null, scrollPane);
        this.testPane.addTab(lang.getText("ui.mainview.radiobutton.viewdone"), null, donePane);
        this.testPane.addTab(lang.getText("ui.mainview.radiobutton.viewoverdue"),null,overDuePane);
        this.testPane.addTab(lang.getText("ui.mainview.radiobutton.viewdeleted"),null,deletePane);
        this.testPane.setMnemonicAt(0, KeyEvent.VK_1);
        this.testPane.setMnemonicAt(1, KeyEvent.VK_2);
        this.testPane.setMnemonicAt(2, KeyEvent.VK_3);
        this.testPane.setMnemonicAt(3, KeyEvent.VK_4);

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
	    final DateFormat timeFormat = new SimpleDateFormat("HH:mm");
	    final DateFormat timeFormat2 = new SimpleDateFormat("HH mm");
	    ActionListener timerListener = new ActionListener()  
	    {  
	        public void actionPerformed(ActionEvent e)  
	        {  
	            Date date = new Date();
	            String time;
	            if((date.getTime()/1000%2) == 0) {
	            	time = timeFormat.format(date); //even seconds
	            } else {
	            	time = timeFormat2.format(date); //odd seconds
	            }
	            timeLabel.setText(time); 
	            timeLabel.setFont(new Font("Arial",Font.PLAIN, 15));
	            timeLabel.setToolTipText(new SimpleDateFormat("EEE., yyyy-MM-dd HH:mm").format(date));
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
		
		this.frame = new JFrame(lang.getText("ui.mainview.windowTitle"));
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setPreferredSize(new Dimension(Integer.valueOf(config.getProp("windowWidth")), 
				Integer.valueOf(config.getProp("windowHeight"))));
		this.frame.setLocation(Integer.valueOf(config.getProp("windowXPos")), Integer.valueOf(config.getProp("windowYPos")));
		
		addComponentsToPane(this.frame.getContentPane());
		addMenuBar(this.frame);
		
		//support for ubuntu global menu, using the jayanta-lib
		ApplicationMenu.tryInstall(this.frame);
		
		this.frame.pack();
		this.frame.setVisible(true);
		
		//we add the controller as a listener to observe changes
		this.frame.addComponentListener(this.controller);
        controller.setTable(table);
    }

	/**
	 * This method is always called if something has changed in the observed object.
	 * We will have to update our data now to make sure, that we show everything up to date.
	 * @see  java.util.Observer#update(Observable, Object)
	 * @param o the observed object
	 * @param arg some arg that the observed object <em>might</em> set
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
        this.testPane.setTitleAt(0,lang.getText("ui.mainview.radiobutton.viewpending"));
        this.testPane.setTitleAt(1,lang.getText("ui.mainview.radiobutton.viewdone"));
        this.testPane.setTitleAt(2,lang.getText("ui.mainview.radiobutton.viewoverdue"));
        this.testPane.setTitleAt(3,lang.getText("ui.mainview.radiobutton.viewdeleted"));
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
