package controller;
import javax.swing.*;
import java.awt.event.ActionEvent;

import model.LocaliziedTexts;
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
        Object source;
        JPanel panel;
        JTextField tf;
        source = event.getSource();
        if (source instanceof JButton){
            panel = (JPanel)((JButton)source).getParent();

            tf = (JTextField)panel.getComponent(0);
            String title = tf.getText();
            System.out.println("Input text: "+title+".");
            if(title.trim().length() == 0){
                JOptionPane.showMessageDialog(panel, "You have not entered a task title!","Enter title",JOptionPane.WARNING_MESSAGE);
            } else {
                ToDoItem item;
                item = (ToDoItem) parent.addItem(title);
                if(item != null){
                    EditTaskFrame editView = new EditTaskFrame(parent, item ,parent.getCategories());
                    editView.setSize(400,400);
                    editView.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(panel, "This task already exists!","Task already exists",JOptionPane.WARNING_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(((JComponent)source).getParent(), "Ah, ah, ah! You didn't say the magic word!","Magic word request!",JOptionPane.WARNING_MESSAGE);
        }

    }
    public void updateLanguage(LocaliziedTexts lang) {
        putValue(NAME, lang.getText("ui.mainview.menu.edit.add"));
    }
}