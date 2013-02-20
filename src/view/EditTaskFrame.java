package view;

import controller.ToDoController;
import model.ToDoItem;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

/**
 * The window for the add/edit task panel.
 * @author Mattias
 */
@SuppressWarnings("serial")
public class EditTaskFrame extends JFrame {
    
    private EditTaskPanel editTaskPanel;
    
    public EditTaskFrame(ToDoController controller, ToDoItem item){
        super("Edit Tasks");
        Container c = getContentPane();
        this.editTaskPanel = new EditTaskPanel(controller, item);
        c.add(editTaskPanel, BorderLayout.CENTER);
        this.setVisible(true);
        this.pack();
    }
    
}
