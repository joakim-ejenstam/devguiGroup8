/**
 * 
 */
package view;

import java.awt.Graphics;
import java.awt.Shape;
import java.util.List;

/**
 * @author simon
 *
 */
public interface Drawable extends Shape {
	
	/**
	 * Draws the Element on the {@link Graphics} g.
	 * The position depends on the already painted elements, as this elements should not overlap each other.
	 * @param g the {@link Graphics} to print on
	 * @param alreadyPaintedItems a {@link List} of all already painted items
	 */
	void drawMe(Graphics g, List<Drawable> alreadyPaintedItems);

}
