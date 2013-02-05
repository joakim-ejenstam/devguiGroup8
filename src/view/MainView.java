import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 * 
 * @author Emma Rangert
 *
 */

public class MainView {
	public static void addComponentsToPane(Container pane) {
        
		JList list = new JList(new DefaultListModel());//listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.setVisibleRowCount(10);
		JScrollPane scrollPane = new JScrollPane(list);
		pane.add(scrollPane,BorderLayout.PAGE_START);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
	    pane.add(mainPanel);
		
	    
	    JPanel textFieldPanel = new JPanel(new FlowLayout());
	    JTextField textField = new JTextField();
	    textField.setPreferredSize(new Dimension(300,20));//pane.getWidth(),20)); //Hard coded for now...
	    textFieldPanel.add(textField);
	    
	    JPanel buttonFieldPanel = new JPanel(new FlowLayout());
	    JButton addButton = new JButton("+");
	    addButton.setPreferredSize(new Dimension(20,20));
	    buttonFieldPanel.add(addButton);
	    
	    
	    /*
	    JTextField textField = new JTextField(new FlowLayout().LEFT);
	    textField.setPreferredSize(new Dimension(300,20));
	    mainPanel.add(textField);
	    
	    JButton addButton = new JButton("+");
	    addButton.setPreferredSize(new Dimension(20,20));
	    mainPanel.add(addButton);
	    */
	    //mainPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));

	    mainPanel.add(textFieldPanel,BorderLayout.SOUTH);
	    mainPanel.add(buttonFieldPanel, BorderLayout.LINE_END);
    }

	private static void createAndShowGUI() {
        
        JFrame frame = new JFrame("The Greight TODO Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 500);
        addComponentsToPane(frame.getContentPane());
        
        JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu file = new JMenu("File");
		menuBar.add(file);
		JMenu edit = new JMenu("Edit");
		menuBar.add(edit);
		JMenu help = new JMenu("Help");
		menuBar.add(help);
        
		frame.pack();
        frame.setVisible(true);
    }
	
	public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
