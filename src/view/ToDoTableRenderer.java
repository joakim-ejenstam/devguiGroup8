package view;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import controller.ToDoController;

@SuppressWarnings("serial")
public class ToDoTableRenderer extends DefaultTableCellRenderer {
	
	ToDoController controller;
	JLabel lbl = new JLabel();
	ImageIcon icon = new ImageIcon();
	
	/**
	 * Method for rendering an image in the priority column
	 */
	@SuppressWarnings("static-access")
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		
		if ( (Integer) value == 1) {
			icon = controller.createNavigationIcon("/Flag-Green");
		}
		else if ((Integer) value == 2) {
			icon = controller.createNavigationIcon("/Flag-Yellow");
		}
		else {
			icon = controller.createNavigationIcon("/Flag-Red");
		}
		
		lbl.setText("");
		lbl.setIcon(icon);
		return lbl;
	}
}