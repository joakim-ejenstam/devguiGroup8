package controller;

import model.LocalizedTexts;

import javax.swing.*;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AboutAction extends AbstractAction{

	private ToDoController parent;
	private LocalizedTexts lang;

    /**
     * Constructor, nothing fancy!
     * @param text The title for the component using this action.
     * @param icon The icon for the component using this action.
     * @param desc The hovertext of the component using this action.
     * @param mnemonic The mnemonic of the component using this action.
     * @param controller This actions controller parent.
     */
    public AboutAction(String text, ImageIcon icon,
                    String desc, Integer mnemonic,
                    ToDoController controller) {
        super(text, icon);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        this.parent = controller;
    }

    /**
     * Here we display the useful about information to the user.
     * @param event Event that is triggered by the user.
     */
    public void actionPerformed(ActionEvent event) {
        JOptionPane.showMessageDialog(
                ((JMenuItem) event.getSource()).getParent(),
                "Greight ToDo Manager v1.0 \n" +
                        "\n Joakim Ejenstam " +
                        "\n Simon Harhues " +
                        "\n Emma Rangert " +
                        "\n Mattias \u00F6st",null, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Language set method. Sets the strings of this object according to the input language localization object.
     * @param lang Language localization class to get correct textstrings.
     */
    public void updateLanguage(LocalizedTexts lang) {
        putValue(NAME, lang.getText("ui.mainview.menu.help.about"));
        putValue(SHORT_DESCRIPTION,lang.getText("ui.mainview.aboutAction"));
        putValue(MNEMONIC_KEY,new Integer(lang.getText("ui.mainview.menu.help.about.mnemonic").charAt(0)));
    }
	
}
