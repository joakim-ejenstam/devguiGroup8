package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;

import view.EditTaskPanel;

import model.LocalizedTexts;

/**
 * @author Mattias
 */
@SuppressWarnings("serial")
public class EnableTimeAction extends AbstractAction {
   // ToDoController parent;
    EditTaskPanel panel;
	
	
    /**
     * Constructor, nothing fancy!
     * @param text The title for the component using this action.
     * @param icon The icon for the component using this action.
     * @param desc The hovertext of the component using this action.
     * @param mnemonic The mnemonic of the component using this action.
     * @param controller This actions controller parent.
     */
    public EnableTimeAction(String text, ImageIcon icon,
                    String desc, Integer mnemonic,
                    ToDoController controller) {
        super(text, icon);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
    }


    public void actionPerformed(ActionEvent event) {
        if (panel != null) {
        	JCheckBox cBox = (JCheckBox)event.getSource();
        	JSpinner timeSpinner = panel.getEnableTime(cBox);
        	if(cBox.isSelected()){
        		timeSpinner.setEnabled(true);
        	}else{
        		timeSpinner.setEnabled(false);
        	}
        }

    }
    
    public void addPanel(EditTaskPanel newPanel) {
        this.panel = newPanel;
    }


    /**
     * Language set method. Sets the strings of this object according to the input language localization object.
     * @param lang Language localization class to get correct textstrings.
     */
    public void updateLanguage(LocalizedTexts lang) {
        putValue(NAME, lang.getText("ui.editview.checkBox.enableTime"));
        putValue(SHORT_DESCRIPTION,lang.getText("ui.mainview.enableAction"));

    }
}
