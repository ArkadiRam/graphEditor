import java.awt.Graphics;
import java.util.ArrayList;
/** Class represents a graph consisting of Nodes and lines(edges).*/
public class Graph {
	/*
	 * List that contains all the nodes of this graph
	 */
	public ArrayList<Node> nodes;
	/*
	 * List that contains all the lines of this graph
	 */
	public ArrayList<Line> lines;

	/**
	 * Creates an instance of graph
	 */
			
	public Graph() {
		this.nodes = new ArrayList<Node>();
		this.lines = new ArrayList<Line>();

	}
	/**Adds node to a graph,
	 * 
	 * @param n node
	 */
	public void addNode(Node n) {
		nodes.add(n);
	}
	/** Adds line to a graph.
	 * 
	 * @param l Line
	 */
	public void addLine(Line l) {
		lines.add(l);
	}
	/**Removes given node from the list of nodes
	 * 
	 * @param n node
	 */
	public void removeNode(Node n) {
		nodes.remove(n);
	}
	/**Removes given line from the list of lines
	 * 
	 * @param l line
	 */
	public void removeLine(Line l) {
		lines.remove(l);
	}
	/**Removes any left over line after removing a node.
	 * 
	 */
	public void removeLines() {
		ArrayList<Line> tempLines = new ArrayList<Line>();
		for (Line l : lines) {
			if (nodes.contains(l.start) == false || nodes.contains(l.end) == false)
				tempLines.add(l);
		}
		for (Line l : tempLines) {
			lines.remove(l);
		}
	}
/**
 * 
 */
	public void draw(Graphics g) {
		for (Line l : lines) {
			l.draw(g);
		}
		for (Node n : nodes) {
			n.draw(g);
		}

	}
	/**
	 * Returns an array of nodes of this graph.
	 * @return array of nodes of this graph.
	 */
	public Node[] getNodes() {
		Node[] array = new Node[0];
		return nodes.toArray(array);
	}
	/**
	 * Returns an array of lines.
	 * @return  array of lines of this graph.
	 */
	public Line[] getLines() {
		Line[] array = new Line[0];
		return lines.toArray(array);
	}
/**
 * Return a string of lines, which were converted to strings.
 * @return string with info about lines. 
 */
	public String printLines() {
		StringBuilder sb = new StringBuilder();
		for(Line l: lines) {
		sb.append(l);
		sb.append("\n");
		}
		return sb.toString();
	}
	/**
	 * Return a string of nodes, which were converted to strings.
	 * @return string with info about nodes. 
	 */
	public String printNodes() {
		StringBuilder sb = new StringBuilder();
		for(Node n: nodes) {
		sb.append(n);
		sb.append(", ");
		}
		return sb.toString();
	}

}
