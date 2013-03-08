package controller;

import view.EditTaskFrame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created with IntelliJ IDEA.
 * User: Joakim
 * Date: 2013-03-07
 * Time: 21:30
 * To change this template use File | Settings | File Templates.
 */
public class TodoMouseListener implements MouseListener {

    private ToDoController controller;
    private JPopupMenu popupMenu;

    public TodoMouseListener(ToDoController cont) {

        this.controller = cont;

        popupMenu = new JPopupMenu();
        JMenuItem popEdit = new JMenuItem(controller.getEditAction());
        JMenuItem popDel = new JMenuItem(controller.getDeleteAction());
        JMenuItem popSet = new JMenuItem(controller.getDoneAction());
        popupMenu.add(popEdit);
        popupMenu.add(popDel);
        popupMenu.add(popSet);
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && !e.isConsumed() && e.getButton() == 1) {
            e.consume();
            int index;
            if (e.getSource() instanceof JTable)
                index = ((JTable) e.getSource()).convertRowIndexToModel(((JTable) e.getSource()).getSelectedRow());
            else
                index = ((JList)e.getSource()).getSelectedIndex();

            EditTaskFrame editView =
                    new EditTaskFrame(
                            controller,
                            controller.getEditItem(index),
                            controller.getCategories(),
                            controller.getLanguage());
            editView.setSize(400, 400);
            editView.setVisible(true);
        }
        else if(e.getClickCount() != 2 && e.getSource() instanceof JList) {
            JList list = (JList)e.getSource();
            int index = list.getSelectedIndex();
            controller.setSelected(index);
        }
    }

    public void mousePressed(MouseEvent ev) {
        Object source = ev.getSource();
        if (source instanceof JTable){
            if (ev.isPopupTrigger()) {
                int r = ((JTable)source).rowAtPoint(ev.getPoint());
                if (r >= 0 && r < ((JTable)source).getRowCount()) {
                    ((JTable)source).setRowSelectionInterval(r, r);
                } else {
                    ((JTable)source).clearSelection();
                }
                popupMenu.show(ev.getComponent(), ev.getX(), ev.getY());
            }
        } else {
            if (ev.isPopupTrigger()) {
                int r = ((JList)source).locationToIndex(ev.getPoint());
                if (r >= 0 && r < ((JList)source).getModel().getSize()) {
                    ((JList)source).setSelectedIndex(r);
                } else {
                    ((JList)source).clearSelection();
                }
                popupMenu.show(ev.getComponent(), ev.getX(), ev.getY());
            }
        }
    }

    public void mouseReleased(MouseEvent ev) {
        Object source = ev.getSource();
        if (source instanceof JTable){
            if (ev.isPopupTrigger()) {
                int r = ((JTable)source).rowAtPoint(ev.getPoint());
                if (r >= 0 && r < ((JTable)source).getRowCount()) {
                    ((JTable)source).setRowSelectionInterval(r, r);
                } else {
                    ((JTable)source).clearSelection();
                }
                popupMenu.show(ev.getComponent(), ev.getX(), ev.getY());
            }
        } else {
            if (ev.isPopupTrigger()) {
                int r = ((JList)source).locationToIndex(ev.getPoint());
                if (r >= 0 && r < ((JList)source).getModel().getSize()) {
                    ((JList)source).setSelectedIndex(r);
                } else {
                    ((JList)source).clearSelection();
                }
                popupMenu.show(ev.getComponent(), ev.getX(), ev.getY());
            }
        }
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
