package view;

import model.ToDoItem;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-03-05
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */
public class ToDoListRenderer extends JCheckBox implements ListCellRenderer {

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
        setEnabled(list.isEnabled());
        setSelected(((ToDoItem) value).isDone());
        setFont(list.getFont());
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String dueDate = "";
        if (((ToDoItem) value).getDueDate() != null){
            dueDate = dateFormat.format(((ToDoItem) value).getDueDate());
            if(((ToDoItem) value).getDueDate().compareTo(new Date()) < 0)
                setForeground(Color.RED);
        }
        setText(
                ((ToDoItem) value).getTitle() +
                        "         "+
                        "Due:" +
                        dueDate);
        if (isSelected)
            setBackground(Color.red);


        return this;
    }
}
