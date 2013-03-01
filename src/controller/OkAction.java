package controller;

import model.LocalizedTexts;
import model.ToDoItem;
import view.EditTaskPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-02-19
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class OkAction extends AbstractAction {
    ToDoController parent;
    EditTaskPanel panel;

    /**
     * Constructor, nothing fancy!
     * @param text The title for the component using this action.
     * @param icon The icon for the component using this action.
     * @param desc The hovertext of the component using this action.
     * @param mnemonic The mnemonic of the component using this action.
     * @param controller This actions controller parent.
     */
    public OkAction(String text, ImageIcon icon,
                     String desc, Integer mnemonic,
                     ToDoController controller) {
        super(text, icon);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        this.parent = controller;
    }

    /**
     * This method adds an EditTaskPanel to the action so it can be accessed in the actionPerformed method.
     * @param newPanel  The newly created panel.
     */
    public void addPanel(EditTaskPanel newPanel) {
        this.panel = newPanel;
    }

    /**
     * Overrided method. fetches item from panel and sends to the controller to update the model, then close the edittaskframe.
     * @param event
     */
    public void actionPerformed(ActionEvent event) {
        if (panel != null) {
            ToDoItem item = panel.getTodoItem();
            parent.updateEditItem(item);
        }
        Component component = (Component)event.getSource();
        JFrame frame = (JFrame) SwingUtilities.getRoot(component);
        frame.setVisible(false);
        frame.dispose();
    }

    /**
     * Language set method. Sets the strings of this object according to the input language localization object.
     * @param lang Language localization class to get correct textstrings.
     */
    public void updateLanguage(LocalizedTexts lang) {
        putValue(NAME, lang.getText("ui.editview.button.ok"));
        putValue(SHORT_DESCRIPTION,lang.getText("ui.mainview.okAction"));
    }
}
