package controller;

import model.LocaliziedTexts;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-02-17
 * Time: 20:23
 * To change this template use File | Settings | File Templates.
 */
public class DeleteAction extends AbstractAction {
    private ToDoController parent;

    public DeleteAction(String text, ImageIcon icon,
                      String desc, Integer mnemonic,
                      ToDoController controller) {
        super(text, icon);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        this.parent = controller;
    }

    public void actionPerformed(ActionEvent event){
        /*TODO: Remove ToDoItem from model.*/
    }

    public void updateLanguage(LocaliziedTexts lang) {
        putValue(NAME, lang.getText("ui.mainview.menu.edit.delete"));
    }

}
