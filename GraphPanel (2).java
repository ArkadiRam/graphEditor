import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1963221305622255634L;
	private int mouseX = 0;
	private int mouseY = 0;
	private boolean mouseButtonLeft = false;
	private boolean mouseButtonRight = false;
	protected int mouseCursor = Cursor.DEFAULT_CURSOR;

	Graph graph;
	
	protected Node selectedNode = null;
	protected Node nodeUnderCursor = null;
	protected Line lineUnderCursor = null;

	public GraphPanel() {
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
	}

	private Node findNode(MouseEvent event) {
		return findNode(event.getX(), event.getY());
	}

	private Node findNode(int x, int y) {
		for (Node n : graph.getNodes()) {
			if (n.isMouseOver(x, y) != null) {
				return n;
			}
		}
		return null;
	}
	private Line findLine(int x,int y) {
		for(Line l :graph.getLines()) {
			if(l.isMouseOver(x, y)!=null)
			return l;
		}
		return null;
	}

	public void setGraph(Graph g) {
		this.graph = g;
	}

	public Graph getGraph() {
		return this.graph;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (graph == null)
			return;

		graph.draw(g);

	}

	private void setMouseCursor(MouseEvent e) {
		nodeUnderCursor = findNode(e);
		lineUnderCursor = findLine(e.getX(),e.getY());
		if(nodeUnderCursor != null || lineUnderCursor!=null ) {
			mouseCursor = Cursor.HAND_CURSOR;
		} else if (mouseButtonLeft) {
			mouseCursor = Cursor.MOVE_CURSOR;
		} else if(selectedNode != null)	 {
			mouseCursor = Cursor.WAIT_CURSOR;
		}
		else mouseCursor = Cursor.DEFAULT_CURSOR;
		setCursor(Cursor.getPredefinedCursor(mouseCursor));
		mouseX = e.getX();
		mouseY = e.getY();
		}
	
	private void moveNodes(int mx,int my) {
		for(Node n: graph.getNodes()) {
			n.moveNode(mx, my);
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		 selectedNode = null;

		if(mouseButtonLeft) {
		if(nodeUnderCursor!=null) {
			nodeUnderCursor.moveNode(e.getX()-mouseX, e.getY() - mouseY);
		}
		else moveNodes(e.getX()-mouseX, e.getY() - mouseY);
		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		setMouseCursor(e);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		setMouseCursor(e);
		if(e.getButton() == 1) {
		if(nodeUnderCursor!=null && selectedNode !=null ) {
			graph.addLine(new Line(nodeUnderCursor, selectedNode));
			selectedNode = null;
			repaint();
			}
		else selectedNode = null;
	}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			mouseButtonLeft = true;
		}
		if (e.getButton() == 3) {
			mouseButtonRight = true;
		}
		setMouseCursor(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == 1) {
			mouseButtonLeft = false;
		}
		if (e.getButton() == 3) {
			mouseButtonRight = false;
			setMouseCursor(e);
			if(nodeUnderCursor!=null)
				createPopupMenu(e,nodeUnderCursor);
			else if(lineUnderCursor != null) 
				createPopupMenu(e,lineUnderCursor);
			else createPopup(e);
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	protected void createPopupMenu(MouseEvent event, Node node) {
		JMenuItem menuItem;

		JPopupMenu popup = new JPopupMenu();
		menuItem = new JMenuItem("Change color");
		menuItem.addActionListener((a) -> {
			Color newColor = JColorChooser.showDialog(this, "Choose Background Color", node.getColor());
			if (newColor != null) {
				node.setColor(newColor);
			}
			repaint();
		});
		popup.add(menuItem);
		
		menuItem  = new JMenuItem("ChangeName");
		menuItem.addActionListener( (action) -> {
			String s = 
				JOptionPane.showInputDialog(this,"Enter data","Enter new name for node",javax.swing.JOptionPane.INFORMATION_MESSAGE );
			if(s!=null) {
				node.setName(s);
			}
			repaint();
			});
		popup.add(menuItem);
		
		
		menuItem = new JMenuItem("Remove Node");
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				graph.removeNode(node);
				graph.removeLines();
				repaint();
			}
			
			
			
		});
		popup.add(menuItem);
		menuItem = new JMenuItem("Add edge");
		menuItem.addActionListener((action)->{
			selectedNode = node;
		});
		
		popup.add(menuItem);
		popup.show(event.getComponent(), event.getX(), event.getY());
		
	}
		
	public void createPopup(MouseEvent e) {
		JPopupMenu popup = new JPopupMenu();
		JMenuItem newNode = new JMenuItem("Create node");
		newNode.addActionListener((action)->{
		
			graph.addNode(new Node(e.getX(), e.getY()));
			this.repaint();
			
		});
		popup.add(newNode);

		popup.show(e.getComponent(), e.getX(), e.getY());
		
		
	}
	public void createPopupMenu(MouseEvent e, Line l ) {
		JPopupMenu menu =  new JPopupMenu();
		JMenuItem item = new JMenuItem("remove line");
		item.addActionListener((action)->{
			graph.removeLine(l);
			repaint();
			
		});
		menu.add(item);
		
		item = new JMenuItem("Change color");
		item.addActionListener((action)->{
			Color c = JColorChooser.showDialog(this, "Choose Background Color", l.getColor()); ; 
			if(c!=null)
			l.setColor(c);
			repaint();
		});
		menu.add(item);
		
		menu.show(e.getComponent(), e.getX(), e.getY());
		
		
	}

}
