/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.StupidToDoItemModel;
import model.ToDoItem;
import model.ToDoItemModel;
import controller.Config;

/**
 * This class shows the {@link ToDoItem}s in some sort of graphical view,
 * using a two-dimensional view on them with two axis regarding time and priority
 * @author simon
 *
 */
@SuppressWarnings("serial")
public class GraphPanel extends JPanel {
	
	private ToDoItemModel model;
	private int height;			//height of pane, used for priority-relevant positioning
	private int width;			//width of the pane, used for date-relevant positioning
	private Date minDueDate;	//will be the left corner
	private Date maxDueDate;	//will be the right corner
	private int timeSpan;		//x-axis in DAYS
	private long timeScale;		//How much pixels is one day?
	private int minPriority;	//will be bottom corner
	private int maxPriority;	//will be top corner
	private int prioritySpan;	//y-axis
	private long priorityScale;	//How much pixels is one priority?
	private final int PADDING = 50;	//some space to the border of the pane
	private Set<String> takenPositions;	//collection with all positions already taken (a new item has then to be shifted)

	/**
	 * 
	 */
	public GraphPanel(ToDoItemModel model) {
		this.model = model;
	}

	/**
	 * @param isDoubleBuffered
	 */
	public GraphPanel(ToDoItemModel model, boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		this.model = model;
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //get size for our panel
		try {
			Dimension dim = this.getRootPane().getContentPane().getSize();
			this.width = new Double(dim.getWidth()).intValue()-this.PADDING*5;
			this.height = new Double(dim.getHeight()).intValue()-this.PADDING;
		} catch (NullPointerException e) {
			//apparently we don't have a parent yet.
			//But as we need some numbers for the size, so let's take some default stuff from the window 
			//(the difference shouldn't be to big anyway):
			this.width = Integer.valueOf(Config.getInstance().getProp("windowWidth"))-this.PADDING*5;
			this.height =  Integer.valueOf(Config.getInstance().getProp("windowHeight"))-this.PADDING;
			System.out.println("No parent there to get the size of, taking default values ("+this.width+"x"+this.height+").");
		}
		//get relevant information from the model
		this.minPriority	= this.model.getMinPriority();
		this.maxPriority	= this.model.getMaxPriority();
		this.prioritySpan	= this.maxPriority-this.minPriority;
		this.priorityScale	= this.height/this.prioritySpan;
		this.minDueDate		= this.model.getMinDueDate();
		this.maxDueDate		= this.model.getMaxDueDate();
		this.timeSpan		= (int)((this.maxDueDate.getTime()-this.minDueDate.getTime())/(1000 * 60 * 60 * 24));//difference in *days*
		this.timeScale		= this.width/this.timeSpan;
		System.out.println("---DEBUG BEGIN---");
		System.out.println("--height: "+new Integer(this.height).longValue()+", width: "+new Integer(this.width).longValue());
		System.out.println("--Priority: min: "+this.minPriority+" max: "+this.maxPriority+" span: "+this.prioritySpan+" scale: "+this.priorityScale);
		System.out.println("--Time: min: "+this.minDueDate+" max: "+this.maxDueDate+" span: "+this.timeSpan+" scale: "+this.timeScale);
		System.out.println("---DEBUG END  ---");
        
        int xPos = 0; //position of item
        int yPos = 0; //position of item
		this.takenPositions = new HashSet<String>(this.model.getNumberOfToDoItems()/2);

        for (ToDoItem currItem : model.getAllToDoItems()) {
        	//calculate X position (date)
        	xPos = (int)((currItem.getDueDate().getTime()-this.minDueDate.getTime())/(1000 * 60 * 60 * 24)*this.timeScale+this.PADDING/5);
        	//calculate Y position (priority)
        	yPos = this.height-(int)((currItem.getPriority()-this.minPriority)*this.priorityScale-this.PADDING/1.2);//0 is upper corner
        	//check if position is already taken
        	if(this.takenPositions.contains(xPos+"x"+yPos)) {//TODO: check not only for exact position, but also for X pixels to the right, as the string might overlap
        		System.out.println(xPos+"x"+yPos+" is already taken ("+currItem.getTitle()+")");
        		yPos -= this.PADDING/4;//shift the new item a little to the top
        	}
        	//insert position in collection
        	this.takenPositions.add(xPos+"x"+yPos);
        	//draw it
			g.drawString(currItem.getTitle()+" ("+currItem.getPriority()+"; "+new SimpleDateFormat("yyyy-MM-dd").format(currItem.getDueDate())+")", xPos, yPos);
		}
        
        //paint a line for today
        g.setColor(Color.RED);
        int pixelToday = (int)((new Date().getTime()-this.minDueDate.getTime())/(1000 * 60 * 60 * 24)*this.timeScale+this.PADDING/5);
        g.drawLine(pixelToday, 0, pixelToday, this.height+this.PADDING);
        
    }
	
	/**
	 * just temporary for testing!!!
	 * @param arg
	 */
	public static void main(String[] arg) {
		JFrame f = new JFrame();
		ToDoItemModel model = new StupidToDoItemModel();
		f.setContentPane(new GraphPanel(model));
		f.setPreferredSize(new Dimension(Integer.valueOf(Config.getInstance().getProp("windowWidth")),Integer.valueOf(Config.getInstance().getProp("windowHeight"))));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}
}
