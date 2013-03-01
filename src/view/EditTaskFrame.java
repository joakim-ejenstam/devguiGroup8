package view;

import controller.ToDoController;
import model.Category;
import model.LocalizedTexts;
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
    
    public EditTaskFrame(ToDoController controller, ToDoItem item, List<Category> categories, LocalizedTexts language){
        super(language.getText("ui.editview.windowTitle"));
        Container c = getContentPane();
        this.editTaskPanel = new EditTaskPanel(controller, item , categories, language);
        c.add(editTaskPanel, BorderLayout.CENTER);
        this.setVisible(true);
        this.pack();
    }
    
}
