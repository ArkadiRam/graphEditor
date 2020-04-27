//Arkadzi Zaleuski 250929 1.3.2020
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;
/**
 * Class that represents a node for building a graph @see Graph
 * 
 *  @author Arkadzi Zaleuski
 * @version January 3 2020
*/
public class Node implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	/** X coordinate of node inside window. */
	private int x;
	/** Y coordinate of node inside window.*/
	private int y;
	/** Name of the node.*/
	private String name ;
	/** Radius of the circle that visually represents node. */
	private int radius;
	/** The color of this circle.*/
	private Color color;
/** 
 * Constructor of graph's Node creates an instance of this class(a node).
 * 
 * @param x X coordinate on screen.
 * @param y Y coordinate on screen.
 * */
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		this.radius = 20;
		name = "?";
		color = Color.WHITE;
	}

	/** Sets a name for this node.
	 * @param s Name of the node that is yet to be set.
	 */
	public void setName(String s) {
		this.name = s;
	}
/**
 * Returns the name of this Node
 * @return the name of the node.
 */
	public String getName() {
		return this.name;
	}

	/**Sets a X coordinate of node.
	 * 
	 * @param x x coordinate.
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**Sets a Y coordinate of node.
	 * 
	 * @param y y coordinate.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**Sets radius of circle, that represents node on screen.
	 * 
	 * @param r radius of circle.
	 */
	public void setR(int r) {
		this.radius = r;
	}
	/**Returns an X coordinate.
	 * 
	 * @return  an X coordinate of this node.	
	 */
	public int getX() {
		return this.x;
	}
	/**Returns an Y coordinate.
	 * 
	 * @return  an Y coordinate of this node.	
	 */
	public int getY() {
		return this.y;
	}
	/**Returns a radius of circle, that represents node.
	 * 
	 * @return  an X coordinate of this node.	
	 */
	public int getRad() {
		return radius;
	}
	/**Sets color of circle, that represents node.
	 * 
	 * @param color color of this circle .
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**Returns a color of this node.
	 * 
	 * @return  a color of this node.
	 */
	public Color getColor() {
		return this.color;
	}
	/**
	 * 
	 * @param g 
	 */
	void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
		g.setColor(Color.BLACK);

		g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
		try {
			g.setFont(new Font("Serif", Font.BOLD, 24));
			g.drawString(name, x - 3 - name.length() * 4, y + 8);
		} catch (Exception e) {

		}
	}

	@Override
	/**
	 * Converts an object of Class Node to string.
	 * @return string
	 */
	public String toString() {
		return "(" + getX() + " ," + getY() + " ," + getColor() + ") " + getName();
	}
/**Returns the node if given coordinates of mouse are over current instance of node.
 *  Used to determine if specific mouse action for node should be invoked.
 * @param mx position of mouse on x axis.
 * @param my position of mouse on y axis.
 * @return {@code null} in case mouse is not over a node, {@code this} in case mouse is over the node.
 */
	public Node isMouseOver(int mx, int my) {
		if (Math.pow((this.getX() - mx), 2) + Math.pow((this.getY() - my), 2) < Math.pow(this.getRad(), 2)) {
			return this;
		}
		return null;
	}

	/**Moves a node towards left bottom corner of the screen.
	 * 
	 * @param mx distance the node has to get moved on x axis.
	 * @param my distance the node has to get moved on Y axis.
	 */
	public void moveNode(int mx, int my) {
		this.x += mx;
		this.y += my;
	}

}
