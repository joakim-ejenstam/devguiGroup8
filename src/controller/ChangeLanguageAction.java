package controller;

import model.LocaliziedTexts;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-02-19
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
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
        Locale se = new Locale("sv", "SE");
        Locale en = new Locale("en", "UK");
        Locale de = new Locale("de", "DE");

        if (source instanceof JMenuItem) {
            JRootPane rp = ((JMenuItem) source).getRootPane();
            Object[] possibilities = {en.getDisplayLanguage(),de.getDisplayLanguage(),se.getDisplayLanguage()};
            String l = (String)JOptionPane.showInputDialog(
                    rp,
                    "Choose your preferred language!",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    en.getDisplayLanguage());
            if (l != null) {
                if (l == en.getDisplayLanguage()) {
                    parent.updateLanguage(en);
                } else if(l == de.getDisplayLanguage()) {
                    parent.updateLanguage(de);
                } else {
                    parent.updateLanguage(se);
                }
            }
        }
    }

    public void updateLanguage(LocaliziedTexts lang) {
        putValue(NAME, lang.getText("ui.mainview.menu.file.changeLanguage"));
        putValue(SHORT_DESCRIPTION,lang.getText("ui.mainview.languageAction"));
    }
}
