package view;

import controller.ToDoController;
import model.Category;
import model.ToDoItem;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.List;

import javax.swing.JFrame;

/**
 * The window for the add/edit task panel.
 * @author Mattias
 */
@SuppressWarnings("serial")
public class EditTaskFrame extends JFrame {
    
    private EditTaskPanel editTaskPanel;
    
    public EditTaskFrame(ToDoController controller, ToDoItem item, List<Category> categories){
        super("Edit Tasks");
        Container c = getContentPane();
        this.editTaskPanel = new EditTaskPanel(controller, item , categories);
        c.add(editTaskPanel, BorderLayout.CENTER);
        this.setVisible(true);
        this.pack();
    }
    
}
