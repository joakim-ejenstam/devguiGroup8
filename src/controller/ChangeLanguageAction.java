package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-02-19
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */
public class ChangeLanguageAction extends AbstractAction {
    private ToDoController parent;

    public ChangeLanguageAction(String text, ImageIcon icon,
                                String desc, Integer mnemonic,
                                ToDoController controller) {
        super(text, icon);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        this.parent = controller;
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source instanceof JMenuItem) {
            JRootPane rp = ((JMenuItem) source).getRootPane();

            Object[] possibilities = {"English", "German", "Swedish"};
            String s = (String)JOptionPane.showInputDialog(
                    rp,
                    "Choose your preferred language!",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    "English");
            if (s != null) {
                parent.updateLanguage(s);
                JOptionPane.showMessageDialog(rp, "You selected " + s + "!");
            }
        }
    }
}
