package controller;

import model.LocalizedTexts;

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

    /**
     * Constructor, nothing fancy!
     * @param text The title for the component using this action.
     * @param icon The icon for the component using this action.
     * @param desc The hovertext of the component using this action.
     * @param mnemonic The mnemonic of the component using this action.
     * @param controller This actions controller parent.
     */
    public ChangeLanguageAction(String text, ImageIcon icon,
                                String desc, Integer mnemonic,
                                ToDoController controller) {
        super(text, icon);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        this.parent = controller;
    }

    /**
     * Override method to change the language of the application. The user selects from a combobox in the dialogue
     * and the matching locale is sent to the controller.
     * @param event
     */
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        Locale se = new Locale("sv", "SE");
        Locale en = new Locale("en", "UK");
        Locale de = new Locale("de", "DE");

        if (source instanceof JMenuItem) {
            JRootPane rp = ((JMenuItem) source).getRootPane();
            Object[] possibilities = {"English","Deutsch","Svenska"};
            String l = (String)JOptionPane.showInputDialog(
                    rp,
                    "Choose your preferred language!",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    en.getDisplayLanguage());
            if (l != null) {
                if (l == "English") {
                    parent.updateLanguage(en);
                } else if(l == "Deutsch") {
                    parent.updateLanguage(de);
                } else {
                    parent.updateLanguage(se);
                }
            }
        }
    }

    /**
     * Language set method. Sets the strings of this object according to the input language localization object.
     * @param lang Language localization class to get correct textstrings.
     */
    public void updateLanguage(LocalizedTexts lang) {
        putValue(NAME, lang.getText("ui.mainview.menu.file.changeLanguage"));
        putValue(SHORT_DESCRIPTION,lang.getText("ui.mainview.languageAction"));
    }
}
