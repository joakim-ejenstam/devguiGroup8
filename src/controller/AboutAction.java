package controller;

import model.LocaliziedTexts;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AboutAction extends AbstractAction{

	private ToDoController parent;

    public AboutAction(String text, ImageIcon icon,
                    String desc, Integer mnemonic,
                    ToDoController controller) {
        super(text, icon);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        this.parent = controller;
    }

    public void actionPerformed(ActionEvent event) {
        JOptionPane.showMessageDialog(((JMenuItem) event.getSource()).getParent(), "PEW PEW!!!");
    }

    public void updateLanguage(LocaliziedTexts lang) {
        putValue(NAME, lang.getText("ui.mainview.menu.help.about"));
        putValue(SHORT_DESCRIPTION,lang.getText("ui.mainview.aboutAction"));
    }
	
}
