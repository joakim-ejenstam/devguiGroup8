package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-02-19
 * Time: 17:07
 * To change this template use File | Settings | File Templates.
 */
public class CancelAction extends AbstractAction {
    private ToDoController parent;

    public CancelAction(String text, ImageIcon icon,
                    String desc, Integer mnemonic,
                    ToDoController controller) {
        super(text, icon);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        this.parent = controller;
    }

    public void actionPerformed(ActionEvent event) {
        JButton source = (JButton)event.getSource();
        source.getParent();
    }
}
