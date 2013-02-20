package controller;

import javax.swing.ImageIcon;

public class AboutAction {

	private ToDoController parent;

    public AboutAction(String text, ImageIcon icon,
                    String desc, Integer mnemonic,
                    ToDoController controller) {
        super(text, icon);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        this.parent = controller;
    }
	
}
