package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import model.LocalizedTexts;
import view.GraphPanel;

/**
 * handles the menu click to show the {@link GraphPanel}
 * @author simon
 *
 */
@SuppressWarnings("serial")
public class ShowGraphAction extends AbstractAction implements Observer {
	
	//controller
    private ToDoController parent;
    // the frame which displays the graph
    JFrame f = null;
	

    /**
     * Constructor, nothing fancy!
     * @param text The title for the component using this action.
     * @param icon The icon for the component using this action.
     * @param desc The hovertext of the component using this action.
     * @param mnemonic The mnemonic of the component using this action.
     * @param controller This actions controller parent.
     */
    public ShowGraphAction(String text, ImageIcon icon,
                        String desc, Integer mnemonic,
                        ToDoController controller) {
        super(text, icon);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        this.parent = controller;
        
        //add ourself as a observer
        this.parent.getModel().addObserver(this);
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {

    	this.f = new JFrame(this.parent.getLanguage().getText("ui.mainview.windowTitle"));
    	this.f.setPreferredSize(new Dimension(new Integer(this.parent.getConf().getProp("windowWidth")), 
    			new Integer(this.parent.getConf().getProp("windowHeight")))); // having our own size would be nicer...
    	this.f.setContentPane(new GraphPanel(this.parent.getModel()));
    	this.f.pack();
    	this.f.setVisible(true);
		
	}

    /**
     * Language set method. Sets the strings of this object according to the input language localization object.
     * @param lang Language localization class to get correct textstrings.
     */
    public void updateLanguage(LocalizedTexts lang) {
        putValue(NAME, lang.getText("ui.mainview.menu.file.item.showGraph"));
        putValue(SHORT_DESCRIPTION,lang.getText("ui.mainview.menu.file.item.showGraph"));
        putValue(MNEMONIC_KEY,new Integer(lang.getText("ui.mainview.menu.file.item.showGraph.mnemonic").charAt(0)));

    }

	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("graphpane needs to be repainted...");
		if(this.f != null) { //we only need to update something if we have something to show
			this.f.getContentPane().repaint();//slow, but we only can repaint everything...
			System.out.println("got repainted!");
		}
	}

}
