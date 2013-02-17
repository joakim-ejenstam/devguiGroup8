package controller;
import javax.swing.*;
import java.awt.event.ActionEvent;
import view.*;



public class AddAction extends AbstractAction {
	public AddAction(String text, ImageIcon icon,
					 String desc, Integer mnemonic) {
		super(text, icon);
		putValue(SHORT_DESCRIPTION, desc);
		putValue(MNEMONIC_KEY, mnemonic);
	}


    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        /* Todo, access textfield from button panel
        *  */
        EditTaskFrame editFrame = new EditTaskFrame();


    }
}