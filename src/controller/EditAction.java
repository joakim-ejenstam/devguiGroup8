package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import model.LocaliziedTexts;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-02-17
 * Time: 16:58
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class EditAction extends AbstractAction {
    private ToDoController parent;

    /**
     * Constructor, nothing fancy!
     * @param text The title for the component using this action.
     * @param icon The icon for the component using this action.
     * @param desc The hovertext of the component using this action.
     * @param mnemonic The mnemonic of the component using this action.
     * @param controller This actions controller parent.
     */
    public EditAction(String text, ImageIcon icon,
                     String desc, Integer mnemonic,
                     ToDoController controller) {
        super(text, icon);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        this.parent = controller;
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        /*Todo: Get item from model, display edit frame
        * with fields filled from todoitem.
        EditTaskFrame editView = new EditTaskFrame();
        editView.setSize(400,400);
        editView.setVisible(true);*/
     }

    /**
     * Language set method. Sets the strings of this object according to the input language localization object.
     * @param lang Language localization class to get correct textstrings.
     */
    public void updateLanguage(LocaliziedTexts lang) {
        putValue(NAME, lang.getText("ui.mainview.menu.edit.edit"));
        putValue(SHORT_DESCRIPTION,lang.getText("ui.mainview.editAction"));
    }
}
