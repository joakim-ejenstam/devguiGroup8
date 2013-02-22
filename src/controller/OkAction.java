package controller;

import model.LocaliziedTexts;
import model.ToDoItem;
import view.EditTaskPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-02-19
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */
public class OkAction extends AbstractAction {
    ToDoController parent;
    EditTaskPanel panel;

    public OkAction(String text, ImageIcon icon,
                     String desc, Integer mnemonic,
                     ToDoController controller) {
        super(text, icon);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        this.parent = controller;
    }
    public void addPanel(EditTaskPanel newPanel) {
        this.panel = newPanel;
    }

    public void actionPerformed(ActionEvent event) {
        System.out.println("Jag gillar ponnysar...");
        if (panel != null) {
            ToDoItem item = panel.getTodoItem();
            parent.updateEditItem(item);
        }
    }
    public void updateLanguage(LocaliziedTexts lang) {
        putValue(NAME, lang.getText("ui.editview.button.ok"));
        putValue(SHORT_DESCRIPTION,lang.getText("ui.mainview.okAction"));
    }
}
