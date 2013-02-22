package controller;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.LocaliziedTexts;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-02-19
 * Time: 17:07
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class CancelAction extends AbstractAction {
    public CancelAction(String text, ImageIcon icon,
                    String desc, Integer mnemonic,
                    ToDoController controller) {
        super(text, icon);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
    }

    /**
     * Here we just close the EditView and don't do anything else
     * @author simon
     * @param event the ActionEvent when the user clicks on the "cancel"-button in the EditView
     */
    public void actionPerformed(ActionEvent event) {
    	Component component = (Component)event.getSource();
    	 JFrame frame = (JFrame) SwingUtilities.getRoot(component);
    	 frame.setVisible(false);
    	 frame.dispose();
    }

    public void updateLanguage(LocaliziedTexts lang) {
        putValue(NAME, lang.getText("ui.editview.button.cancel"));
        putValue(SHORT_DESCRIPTION,lang.getText("ui.mainview.cancelAction"));

    }
}
