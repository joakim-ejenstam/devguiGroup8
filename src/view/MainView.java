package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import controller.AddAction;
import model.DeletedListModel;
import model.DoneListModel;
import model.OverdueListModel;
import model.LocalizedTexts;
import model.TableToDoItemModel;

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
    
    public JList doneList;// = new JList(choices);
    public JList overdueList;// = new JList(choices2);
    public JList deletedList;// = new JList(choices3);
    
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
        
        viewPending = new JRadioButton(lang.getText("ui.mainview.radiobutton.viewpending"), true);
        viewDone = new JRadioButton(lang.getText("ui.mainview.radiobutton.viewdone"), false);
        viewOverDue = new JRadioButton(lang.getText("ui.mainview.radiobutton.viewoverdue"), false);
        viewDeleted = new JRadioButton(lang.getText("ui.mainview.radiobutton.viewdeleted"), false);
        
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
        JMenuItem setTodo = new JMenuItem(controller.getDoneAction());
		
		// Set up menu bar
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(help);
		file.add(chooseLanguage);
		help.add(about);
		edit.add(addTodo);
		edit.add(editTodo);
		edit.add(deleteTodo);
        edit.add(setTodo);

        popupMenu = new JPopupMenu("Pew pew!");
        JMenuItem popEdit = new JMenuItem(controller.getEditAction());
        JMenuItem popDel = new JMenuItem(controller.getDeleteAction());
        JMenuItem popSet = new JMenuItem(controller.getDoneAction());
        popupMenu.add(popEdit);
        popupMenu.add(popDel);
        popupMenu.add(popSet);

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
		table.setAutoCreateRowSorter(true); //removed the autosorting so we can implement our own sorting algorithm.
        table.getTableHeader().setReorderingAllowed(false);
		table.getModel().addTableModelListener(this);//should catch changes in the table model once we promote them with the fireUpdate() below.
	    
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
        this.doneList.addMouseListener(new TodoMouseListener());
        this.deletedList = new JList(deletedListModel);
        this.doneList.setCellRenderer(new ToDoListRenderer());
        this.doneList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.deletedList.addMouseListener(new TodoMouseListener());
        this.deletedList.setCellRenderer(new ToDoListRenderer());
        this.overdueList = new JList(overdueListModel);
        this.overdueList.addMouseListener(new TodoMouseListener());
        this.overdueList.setCellRenderer(new ToDoListRenderer());
        
        // table
        this.table = createTable();
        this.table.addMouseListener(new TodoMouseListener());
	    
        // Scroll pane
        final JScrollPane scrollPane = new JScrollPane(table);

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

	    // Add radio buttons to button group to make it so only one can be selected at a time 
	    viewItems.add(viewPending);
	    viewItems.add(viewDone);
	    viewItems.add(viewOverDue);
	    viewItems.add(viewDeleted);

	    viewPending.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e) {
	          scrollPane.getViewport().remove(scrollPane.getViewport().getView());
	          scrollPane.getViewport().add(table);
	          System.out.println("view all action");
	          scrollPane.revalidate();
	          scrollPane.repaint();
	        }
	    });
	    
	    viewDone.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e) {
	          scrollPane.getViewport().remove(scrollPane.getViewport().getView());
	          scrollPane.getViewport().add(doneList);
	          System.out.println("view done action");
	          scrollPane.revalidate();
	          scrollPane.repaint();
	        }
	    });
	    
	    viewOverDue.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e) {
	          scrollPane.getViewport().remove(scrollPane.getViewport().getView());
	          scrollPane.getViewport().add(overdueList);
	          System.out.println("view over due action");
	          scrollPane.revalidate();
	          scrollPane.repaint();
	        }
	    });
	    
	    viewDeleted.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e) {
	          scrollPane.getViewport().remove(table);
	          scrollPane.getViewport().add(deletedList);
	          System.out.println("view deleted action");
	          scrollPane.revalidate();
	          scrollPane.repaint();
	        }
	    });
	    
	    // Adding radio buttons and clock to north panels 
	    northLeftPanel.add(viewPending);
	    northLeftPanel.add(viewDone);
	    northLeftPanel.add(viewOverDue);
	    northLeftPanel.add(viewDeleted);
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
        this.doneListModel.getDoneItems();
        this.deletedListModel.getDeletedItems();
        this.overdueListModel.getOverdueItems();
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
        this.viewPending.setText(lang.getText("ui.mainview.radiobutton.viewpending"));
        this.viewDone.setText(lang.getText("ui.mainview.radiobutton.viewdone"));
        this.viewOverDue.setText(lang.getText("ui.mainview.radiobutton.viewoverdue"));
        this.viewDeleted.setText(lang.getText("ui.mainview.radiobutton.viewdeleted"));
        this.setTitle("Greigth To Do Manager");			// no real need to translate this... (lang.getText("ui.mainview.windowTitle"));
        this.validate();
        this.repaint();
    }

    public JTable getTable() {
        return this.table;
    }

    protected class TodoMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2 && !e.isConsumed() && e.getButton() == 1) {
                e.consume();

                int index;
                if (e.getSource() instanceof JTable)
                    index = ((JTable) e.getSource()).convertColumnIndexToModel(((JTable) e.getSource()).getSelectedRow());
                else
                    index = ((JList)e.getSource()).getSelectedIndex();

                EditTaskFrame editView =
                        new EditTaskFrame(
                                controller,
                                controller.getEditItem(index),
                                controller.getCategories(),
                                controller.getLanguage());
                editView.setSize(400,400);
                editView.setVisible(true);
             }
        }

        public void mousePressed(MouseEvent ev) {
            if (ev.isPopupTrigger()) {
                int r = table.rowAtPoint(ev.getPoint());
                if (r >= 0 && r < table.getRowCount()) {
                    table.setRowSelectionInterval(r, r);
                } else {
                    table.clearSelection();
                }
                popupMenu.show(ev.getComponent(), ev.getX(), ev.getY());
            }
        }

        public void mouseReleased(MouseEvent ev) {
            if (ev.isPopupTrigger()) {
                int r = table.rowAtPoint(ev.getPoint());
                if (r >= 0 && r < table.getRowCount()) {
                    table.setRowSelectionInterval(r, r);
                } else {
                    table.clearSelection();
                }
                popupMenu.show(ev.getComponent(), ev.getX(), ev.getY());
            }
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
