package controller;
import javax.swing.*;
import java.awt.event.ActionEvent;

import model.ToDoItem;
import view.*;



public class AddAction extends AbstractAction {
    private ToDoController parent;

	public AddAction(String text, ImageIcon icon,
					 String desc, Integer mnemonic,
                     ToDoController controller) {
		super(text, icon);
		putValue(SHORT_DESCRIPTION, desc);
		putValue(MNEMONIC_KEY, mnemonic);
        this.parent = controller;
	}


    public void actionPerformed(ActionEvent event) {
        JButton source;
        JPanel panel;
        JTextField tf;
        source = (JButton)event.getSource();
        panel = (JPanel)source.getParent();

        tf = (JTextField)panel.getComponent(0);
        String title = tf.getText();

        ToDoItem item;
        item = (ToDoItem) parent.addItem(title);

        EditTaskFrame editView = new EditTaskFrame("Edit tasks");
        editView.setSize(400,400);
        editView.setVisible(true);
    }
}