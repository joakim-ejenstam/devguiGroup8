package controller;
import javax.swing.*;
import java.awt.event.ActionEvent;

import model.LocaliziedTexts;
import model.ToDoItem;
import view.*;



@SuppressWarnings("serial")
public class AddAction extends AbstractAction {
    private ToDoController parent;

    /**
     * Constructor, nothing fancy!
     * @param text The title for the component using this action.
     * @param icon The icon for the component using this action.
     * @param desc The hovertext of the component using this action.
     * @param mnemonic The mnemonic of the component using this action.
     * @param controller This actions controller parent.
     */
	public AddAction(String text, ImageIcon icon,
					 String desc, Integer mnemonic,
                     ToDoController controller) {
		super(text, icon);
		putValue(SHORT_DESCRIPTION, desc);
		putValue(MNEMONIC_KEY, mnemonic);
        this.parent = controller;
	}


    /**
     * Override method for the actionEvent. When the user sends the event, we fetch the textfield, check the text and
     * create a new todoitem or send a warning to the user that an error has occurred. The method also fires a EditTaskFrame.
     * @param event the ActionEvent when the user clicks on any component containing this action.
     */
    public void actionPerformed(ActionEvent event) {
    	Object source;
        JPanel panel;
        JTextField tf;
        source = event.getSource();
        if (source instanceof JButton){
            panel = (JPanel)((JButton)source).getParent();

            tf = (JTextField)panel.getComponent(0);
            String title = tf.getText();
            tf.setText(null); //empties the quick-add-textField.
            System.out.println("Input text: "+title+".");
            if(title.trim().length() == 0){     // The user have not put in any text in the textfield.
                JOptionPane.showMessageDialog(panel, "You have not entered a task title!","Enter title",JOptionPane.WARNING_MESSAGE);
            } else {
                ToDoItem item;
                item = (ToDoItem) parent.addItem(title);
                if(item != null){      // This clause will put up the edit item frame and panel.
                    EditTaskFrame editView = new EditTaskFrame(parent, item ,parent.getCategories(), parent.getLanguage());
                    editView.setSize(400,400);
                    editView.setVisible(true);
                } else {        // This dialogue will be shown if the user wants to create an item that already exists.
                    JOptionPane.showMessageDialog(panel, "This task already exists!","Task already exists",JOptionPane.WARNING_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(((JComponent)source).getParent(), "Ah, ah, ah! You didn't say the magic word!","Magic word request!",JOptionPane.WARNING_MESSAGE);
        }

    }

    /**
     * Language set method. Sets the strings of this object according to the input language localization object.
     * @param lang Language localization class to get correct textstrings.
     */
    public void updateLanguage(LocaliziedTexts lang) {
        putValue(NAME, lang.getText("ui.mainview.menu.edit.add"));
        putValue(SHORT_DESCRIPTION,lang.getText("ui.mainview.addAction"));

    }
}