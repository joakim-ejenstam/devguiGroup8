package controller;

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

    public EditAction(String text, ImageIcon icon,
                     String desc, Integer mnemonic) {
        super(text, icon);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        /*Todo: Get item from model, display edit frame
        * with fields filled from todoitem.*/
    }
}
