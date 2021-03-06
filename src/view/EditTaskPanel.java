package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.Category;
import model.LocalizedTexts;
import model.ToDoItem;

import com.toedter.calendar.JDateChooser;

import controller.ToDoController;
//import javax.swing.BorderFactory;

/**
 * The add/edit task panel.
 * @author Mattias
 */
@SuppressWarnings("serial")
public class EditTaskPanel extends JPanel {
    
	private ToDoController controller;
	private ToDoItem item;
	private String[] categories;
	private LocalizedTexts lang;
	
    //Labels
    private JLabel titleLabel;//            = new JLabel(lang.getText("ui.editview.title"));
    private JLabel dateLabel;//             = new JLabel(lang.getText("ui.editview.dueDate"));
    private JLabel descriptionLabel;//      = new JLabel(lang.getText("ui.editview.description"));
    private JLabel categoryLabel;//         = new JLabel(lang.getText("ui.editview.category"));
    private JLabel priorityLabel;//         = new JLabel(lang.getText("ui.editview.priotity"));
    
    
    //TextFields
    private JTextField titleField       	= new JTextField(10);

    
    //Text area
    private JTextArea descriptionArea    = new JTextArea(0,0);
    private JScrollPane	scrollArea 		 = new JScrollPane(descriptionArea);
    
    //Category JComboBox
    private JComboBox categoryBox;
    
    //Priority JRadioButtons
    private ButtonGroup priorityGroup    = new ButtonGroup();
    private JRadioButton lowPriority;//     = new JRadioButton(lang.getText("ui.editview.low"), true);
    private JRadioButton mediumPriority;//  = new JRadioButton(lang.getText("ui.editview.medium"), false);
    private JRadioButton highPriority;//    = new JRadioButton(lang.getText("ui.editview.high"), false);

    //Extra Panels
    private JPanel topTitlePanel		 = new JPanel();
    private JPanel topDatePanel			 = new JPanel();
    private JPanel categoryPanel		 = new JPanel();
    private JPanel descCatPanel			 = new JPanel();
    private JPanel bottomPanel           = new JPanel();
    private JPanel bottomLabelPanel		 = new JPanel();
    private JPanel priorityPanel	 	 = new JPanel();
    private JPanel subBottomPanel		 = new JPanel();
    private JPanel bottomCenterPanel	 = new JPanel();
    private JPanel topPanel              = new JPanel();
    private JPanel subTopPanel			 = new JPanel();
    private JPanel buttonPanel			 = new JPanel();

    //JDates
    private JDateChooser dueDateCal		= new JDateChooser();
    
    /**
     * The constructor for the class.
     * @param controller
     * @param item
     * @param categories
     */
	EditTaskPanel(ToDoController controller, ToDoItem item, List<Category> categories, LocalizedTexts lang){
    	this.item = item;
    	this.controller = controller;
    	this.categories = new String[categories.size()+1];
    	this.categories[0] = "None";
    	this.lang = lang;
    	
    	titleLabel            = new JLabel(lang.getText("ui.editview.label.title"));
    	dateLabel             = new JLabel(lang.getText("ui.editview.label.dueDate"));
    	descriptionLabel      = new JLabel(lang.getText("ui.editview.label.description"));
    	categoryLabel         = new JLabel(lang.getText("ui.editview.label.category"));
    	priorityLabel         = new JLabel(lang.getText("ui.editview.label.priority"));
    	
    	lowPriority     = new JRadioButton(lang.getText("ui.editview.priority.low"), (this.item.getPriority()<2)?true:false);//1 or 0 (new Item, no priority)
    	mediumPriority  = new JRadioButton(lang.getText("ui.editview.priority.medium"), (this.item.getPriority()==2)?true:false);
    	highPriority    = new JRadioButton(lang.getText("ui.editview.priority.high"), (this.item.getPriority()==3)?true:false);
    	
    	System.out.println(categories.size());
    	for(int i = 1; i<categories.size()+1; i++){
    		this.categories[i] = categories.get(i-1).getLabel();
    	}
    	this.categoryBox = new JComboBox(this.categories);
        this.categoryBox.setEditable(true);
    	this.categoryBox.setSelectedIndex(0);
        controller.getOkAction().addPanel(this);
        
    	//Setup the different layoutManagers
        setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());
        subTopPanel.setLayout(new BoxLayout(subTopPanel, BoxLayout.PAGE_AXIS));
        topTitlePanel.setLayout(new BoxLayout(topTitlePanel, BoxLayout.LINE_AXIS));
        topDatePanel.setLayout(new BoxLayout(topDatePanel,BoxLayout.LINE_AXIS));
        descCatPanel.setLayout(new BoxLayout(descCatPanel, BoxLayout.LINE_AXIS));
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.LINE_AXIS));
        bottomPanel.setLayout(new BorderLayout());
        bottomLabelPanel.setLayout(new BoxLayout(bottomLabelPanel, BoxLayout.PAGE_AXIS));
        bottomCenterPanel.setLayout(new BoxLayout(bottomCenterPanel, BoxLayout.PAGE_AXIS));
        subBottomPanel.setLayout(new BorderLayout());
        priorityPanel.setLayout(new BoxLayout(priorityPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        
        //Configuration for topTitlePanel
        topTitlePanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        topTitlePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        titleLabel.setPreferredSize(titleLabel.getPreferredSize());
        topTitlePanel.add(titleLabel);
        topTitlePanel.add(titleField);
        
        
        //Configuration for datePanel
        topDatePanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        topDatePanel.add(Box.createRigidArea(new Dimension(10,0)));
        dateLabel.setPreferredSize(dateLabel.getPreferredSize());
        topDatePanel.add(dateLabel);
        topDatePanel.add(dueDateCal);

        
        //Configuration for category panel
        categoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        categoryPanel.add(categoryLabel);
        categoryPanel.add(Box.createRigidArea(new Dimension(4,0)));
        categoryPanel.add(categoryBox);
        categoryPanel.add(Box.createRigidArea(new Dimension(30,0)));
        
        descCatPanel.add(Box.createRigidArea(new Dimension(10,0)));
        descCatPanel.add(descriptionLabel);
        descCatPanel.add(Box.createHorizontalGlue());
        descCatPanel.add(categoryPanel);

        
        //Configuration for the top panel
        subTopPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        subTopPanel.add(topTitlePanel);
        subTopPanel.add(Box.createRigidArea(new Dimension(0,2)));
        subTopPanel.add(topDatePanel);
        subTopPanel.add(Box.createRigidArea(new Dimension(0,2)));
        subTopPanel.add(descCatPanel);
        subTopPanel.add(Box.createRigidArea(new Dimension(0,5)));

        
        //Configuration for bottomLabels
        bottomLabelPanel.add(Box.createRigidArea(new Dimension(0,15)));
        bottomLabelPanel.add(Box.createRigidArea(new Dimension(20,0)));
        bottomLabelPanel.add(priorityLabel);      
        
        
        //Configuration for priorityPanel
        priorityGroup.add(lowPriority);
        priorityGroup.add(mediumPriority);
        priorityGroup.add(highPriority);
        priorityPanel.add(lowPriority);
        priorityPanel.add(Box.createRigidArea(new Dimension(5,0)));
        priorityPanel.add(mediumPriority);
        priorityPanel.add(Box.createRigidArea(new Dimension(5,0)));
        priorityPanel.add(highPriority);

        
        //Configuration for buttonPanel
        buttonPanel.add(new JButton(controller.getOkAction()));
        buttonPanel.add(Box.createRigidArea(new Dimension(30,0)));
        buttonPanel.add(new JButton(controller.getCancelAction()));
       
        
        //Configuration for bottomCenterPanel
        bottomCenterPanel.add(Box.createRigidArea(new Dimension(0,10)));
        bottomCenterPanel.add(priorityPanel);
        bottomCenterPanel.add(buttonPanel);
        
        
        //Add everything together in the topPanel and bottomPanel
        topPanel.add(subTopPanel,BorderLayout.PAGE_START);
        topPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        descriptionArea.setLineWrap(true);
        topPanel.add(scrollArea, BorderLayout.CENTER);
        subBottomPanel.add(bottomLabelPanel, BorderLayout.LINE_START);
        subBottomPanel.add(bottomCenterPanel,BorderLayout.CENTER);
        bottomPanel.add(subBottomPanel, BorderLayout.PAGE_END);
        
        ////////Configuration for the main panel, EditTaskPanel
        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.PAGE_END);

        fillFields(item);
    }
    /**
     * Fills the fields in the current frame from a ToDo item
     * @param item the ToDo item to be used for the frame
     */
    private void fillFields(ToDoItem item)  {
        if(item.getTitle() != null)
            this.titleField.setText(item.getTitle());
        if(item.getCategory() != null)
            this.categoryBox.setSelectedItem(item.getCategory().getLabel());
        if(item.getDescription() != null)
            this.descriptionArea.setText(item.getDescription());
        if(item.getCreationDate() != null)
            this.dueDateCal.setDate(item.getDueDate());
    }
    
    /**
     * Fetches the panels ToDoItem
     * @return a ToDoItem
     */
    public ToDoItem getTodoItem() {
        item.setDescription(descriptionArea.getText());
        if(lowPriority.isSelected())
            item.setPriority(1);
        if(mediumPriority.isSelected())
            item.setPriority(2);
        if(highPriority.isSelected())
            item.setPriority(3);
        String cat = (String)categoryBox.getSelectedItem();
        if (cat != "None")
            item.setCategory(new Category(cat));
        else {
            item.setCategory(null);
        }
        item.setDueDate(dueDateCal.getDate());
        return item;
    }
    
    
}
