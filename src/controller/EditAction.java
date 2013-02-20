package controller;

import model.LocaliziedTexts;
import view.EditTaskFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-02-17
 * Time: 16:58
 * To change this template use File | Settings | File Templates.
 */
public class EditAction extends AbstractAction {
    private ToDoController parent;

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

    public void updateLanguage(LocaliziedTexts lang) {
        putValue(NAME, lang.getText("ui.mainview.menu.edit.edit"));
    }
}
