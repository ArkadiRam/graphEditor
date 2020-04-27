import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
/**
 * Class that represents an edge in graph's @see Graph visual representation. 
 * @author Arkadzi Zaleuski 250929
 * @version January 3 2020
 *
 */
public class Line implements Serializable {
	
	private static final long serialVersionUID = -5338860810856727572L;
	/**
	 * One of two nodes an edge connects with.
	 */
	Node start;
	/**
	 * One of two nodes an edge connects with.
	 */
	Node end;
	/**
	 * A color of an edge 
	 */
	private Color color = Color.BLACK;
	/**
	 * Creates an instance of an edge.
	 * @param s One of two nodes an edge connects with.
	 * @param e One of two nodes an edge connects with.
	 */
	public Line(Node s, Node e) {
		start = s;
		end = e;
	}
	/**
	 * Creates an instance of an edge.
	 * @param s One of two nodes an edge connects with.
	 * @param x coordinate on x axis, where the second node is supposedly is.
	 * @param y coordinate on y axis, where the second node is supposedly is.
	 */
	public Line(Node s, int x, int y) {
		start = s;
		end = new Node(x, y);
	}
	/**
	 * Changes the second node of this edge.
	 * @param x x coordinate of new node.
	 * @param y y  coordinate of new node.
	 */
	public void changeEnd(int x, int y) {
		end = new Node(x, y);
	}
	/**
	 * Sets color of and edge.
	 * @param c color of an edge.
	 */
	public void setColor(Color c) {
		this.color = c;
	}
/**
 *Returns color of this edge. 
 * @return color of this edge
 */
	public Color getColor() {
		return color;
	}
	/**
	 * 
	 * @param g
	 */
	void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
	}
	/**
	 * Sets second node the edge is connected to.
	 * @param n second node the edge is connected to.
	 */
	public void setEnd(Node n) {
		end = n;
	}
	/**
	 * Sets first node the edge is connected to.
	 * @param n first node the edge is connected to.
	 */
	public void setStart(Node n) {
		start = n;
	}
	/**Returns this line if given coordinates of the mouse are over approximate area of line.
	 * Used to determine if specific mouse action for line should be invoked.
	 * 
	 * @param x position of mouse on x axis.
	 * @param y position of mouse on y axis.
	 * @return {@code null} in case mouse is further then predetermined distance from the line, {@code this} in case mouse is closer then predetermined distance from the line
	 */
	public Line isMouseOver(float x, float y) {
		if (end.getX() > start.getX())
			if (x > end.getX() || x < start.getX())
				return null;
		if (end.getX() < start.getX())
			if (x < end.getX() || x > start.getX())
				return null;
		float equasion = (y - start.getY()) / (end.getY() - start.getY())
				- (x - start.getX()) / (end.getX() - start.getX());

		if (Math.abs(equasion) / (Math.sqrt(Math.pow(1 / (float) (end.getY() - start.getY()), 2)
				+ Math.pow(1 / (float) (end.getX() - start.getX()), 2))) <= 4) {
			return this;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return start + " -> " + end;
		}
}
