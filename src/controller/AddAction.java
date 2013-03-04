package controller;
import javax.swing.*;
import java.awt.event.ActionEvent;

import model.LocalizedTexts;
import model.ToDoItem;
import view.*;



@SuppressWarnings("serial")
public class AddAction extends AbstractAction {
    private ToDoController parent;
    private JTextField tf;
    private LocalizedTexts lang;

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
                     ToDoController controller, LocalizedTexts newLang) {
		super(text, icon);
		putValue(SHORT_DESCRIPTION, desc);
		putValue(MNEMONIC_KEY, mnemonic);
        this.parent = controller;
        this.lang = newLang;
	}

    /**
     * Setter for the textfield attribute. Needed for the actionPerformed later on.
     * @param textField Main input field.
     */
    public void setTextField(JTextField textField){
        this.tf = textField;
    }

    /*This method is huge and ugly! Needs to be cleansed later */
    /**
     * Override method for the actionEvent. When the user sends the event, we fetch the textfield, check the text and
     * create a new todoitem or send a warning to the user that an error has occurred. The method also fires a EditTaskFrame.
     * @param event the ActionEvent when the user clicks on any component containing this action.
     */
    public void actionPerformed(ActionEvent event) {
    	    JPanel panel = (JPanel)tf.getParent();
            String title = tf.getText();
            tf.setText(null); //empties the quick-add-textField.
            System.out.println("Input text: "+title);
            if(title.trim().length() == 0){     // The user have not put in any text in the textfield.
                JOptionPane.showMessageDialog(
                        panel,
                        lang.getText("ui.addaction.optionpane.entertitle"),
                        lang.getText("ui.addaction.optionpanetitle.entertitle"),
                        JOptionPane.WARNING_MESSAGE);
            } else {
                ToDoItem item;
                item = (ToDoItem) parent.addItem(title);
                if(item != null){      // This clause will put up the edit item frame and panel.
                    EditTaskFrame editView =
                            new EditTaskFrame(parent, item ,parent.getCategories(), parent.getLanguage());
                    editView.setSize(400,400);
                    editView.setVisible(true);
                } else {        // This dialogue will be shown if the user wants to create an item that already exists.
                    JOptionPane.showMessageDialog(
                            panel,
                            lang.getText("ui.addaction.optionpane.re_entertitle"),
                            lang.getText("ui.addaction.optionpane.exitstitle"),
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            //JOptionPane.showMessageDialog(((JComponent)source).getParent(), "Ah, ah, ah! You didn't say the magic word!","Magic word request!",JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Language set method. Sets the strings of this object according to the input language localization object.
     * @param lang Language localization class to get correct textstrings.
     */
    public void updateLanguage(LocalizedTexts lang) {
        putValue(NAME, lang.getText("ui.mainview.menu.edit.add"));
        putValue(SHORT_DESCRIPTION,lang.getText("ui.mainview.addAction"));

    }
}