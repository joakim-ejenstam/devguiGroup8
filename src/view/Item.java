/**
 * 
 */
package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JPanel;

import model.ToDoItem;

/**
 * This class represents a {@link ToDoItem} which can be drawn on a {@link Graphics} like a {@link JPanel}.
 * Not all methods are implemented semantically, thus some methods might always return null or false independent on their parameters. 
 * @author simon
 *
 */
public class Item implements Drawable, Clickable {
	
	private Dimension2D dimension;
	private Point2D point;
	private ToDoItem data;
	private String toPaint;

	/**
	 * 
	 */
	public Item(ToDoItem item) {
		this.data = item;
		//define String we want to paint
		this.toPaint = this.data.getTitle();
		//calculate space we need for the object
		
		//this.dimension = new Dimension(width, height);
		
	}

	@Override
	public boolean contains(Point2D p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Rectangle2D r) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean intersects(Rectangle2D r) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersects(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void drawMe(Graphics g, List<Drawable> alreadyPaintedItems) {
		//TODO this should be done in the Layoutmanager, which I would have to create too!!
//		//calculate X position (date)
//    	int xPos = (int)((data.getDueDate().getTime()-this.minDueDate.getTime())/(1000 * 60 * 60 * 24)*this.timeScale+this.PADDING/5);
//    	//calculate Y position (priority)
//    	int yPos = this.height-(int)((data.getPriority()-this.minPriority)*this.priorityScale-this.PADDING/1.2);//0 is upper corner
//    	//check if position is already taken
//    	
//		this.point = new Point(xPos, yPos);
		
	}

}
