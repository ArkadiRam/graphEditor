import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GraphEditor extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Graph manager";
	private final static String INFO = "Written by Arkadi Zaleuski\n Index number: 250929";
	private final static String Instr = "Clear field by choosing 'New' option in File menu on menu bar or build an example choosing the corresponing opion.\n"
			+ "Add nodes by right clicking on empty field on the screen and choosing the an option for that\n"
			+ "	You can edit or remove node by right clicking on it and choosing needed option. \n"
			+ "Move nodes by dragging them with your mouse.\n You can add edges right clicking on the node and choosig an option 'Add edge'and then left click on another node.\n"
			+ "Edges are also editable the same way the nodes are.\n"
			+ "In order to get the overall structur of the graph choose an option 'Structure' on the menu bar and then choosing option nodes on lines.\n"
			+ "You can also save you progress and load progress from the binary file choosing this option in File menu on menu bar.\n";

	Font newFont = new Font("Serif", Font.BOLD, 24);

	JMenuBar menuBar = new JMenuBar();
	JMenu editMenu = new JMenu("File");
	JMenu aboutMenu = new JMenu("About");
	JMenu structureMenu = new JMenu("Structure");
	JMenuItem newItem = new JMenuItem("New");
	JMenuItem buildItem = new JMenuItem("Build Example");
	JMenuItem aboutItem = new JMenuItem("Author");
	JMenuItem instructItem = new JMenuItem("Instructions");
	JMenuItem edgesItem = new JMenuItem("Edges");
	JMenuItem nodesItem = new JMenuItem("Nodes");
	JMenuItem saveToItem = new JMenuItem("Save to...");
	JMenuItem loadFromItem = new JMenuItem("Open from...");

	static GraphPanel panel = new GraphPanel();

	public GraphEditor() {
		super(TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(640, 480);
		setLocationRelativeTo(null);
		setContentPane(panel);
		panel.setGraph(new Graph());
		createMenu();
		setVisible(true);
		this.setFont(newFont);

	}

	public void createMenu() {
		menuBar.add(editMenu);
		menuBar.add(structureMenu);
		menuBar.add(aboutMenu);

		structureMenu.add(nodesItem);
		structureMenu.add(edgesItem);
		editMenu.add(newItem);
		editMenu.add(buildItem);
		editMenu.add(saveToItem);
		editMenu.add(loadFromItem);
		aboutMenu.add(aboutItem);
		aboutMenu.add(instructItem);

		GraphEditor gEdit = this;

		aboutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(gEdit, INFO, "", javax.swing.JOptionPane.INFORMATION_MESSAGE);
			}

		});
		buildItem.addActionListener((action) -> {
			panel.graph.lines.clear();
			panel.graph.nodes.clear();
			buildExample();
			repaint();
		});

		loadFromItem.addActionListener((action) -> {
			FileChooser fc = new FileChooser("Choose file or place to load from");
			
			String path  = fc.getPath();
					readBin(path);
		});
		saveToItem.addActionListener((action) -> {
			FileChooser fc = new FileChooser("Choose file or place to save");
		
			String path  = fc.getPath();
					printBin(path);
		});

		nodesItem.addActionListener((action) -> {
			int k;
			if((k=panel.graph.getNodes().length)!=0)
			JOptionPane.showMessageDialog(gEdit,"Number of nodes - "+ k+ "\n" + panel.graph.printNodes(), "",
					javax.swing.JOptionPane.INFORMATION_MESSAGE);
			else 
				JOptionPane.showMessageDialog(gEdit, "List of nodes is empty", "",
						javax.swing.JOptionPane.INFORMATION_MESSAGE);
		});

		edgesItem.addActionListener((action) -> {
			int k;
			if((k=panel.graph.getLines().length)!=0)
			JOptionPane.showMessageDialog(gEdit,"Number of edges - "+ k+ "\n" + panel.graph.printLines(), "Edges",
					javax.swing.JOptionPane.INFORMATION_MESSAGE);
			else JOptionPane.showMessageDialog(gEdit,"Number of edges - 0", "Edges",
					javax.swing.JOptionPane.INFORMATION_MESSAGE);
		});

		newItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.graph.lines.clear();
				panel.graph.nodes.clear();
				repaint();
			}
		});
		this.setJMenuBar(menuBar);

		instructItem.addActionListener(action -> {
			JOptionPane.showMessageDialog(this, Instr);
		});
	}

	public void buildExample() {
		Node n1 = new Node(500, 400);
		Node n2 = new Node(30, 100);
		Node n3 = new Node(400, 50);
		Node n4 = new Node(100, 50);
		Node n5 = new Node(50, 200);
		Line l1 = new Line(n1, n3);
		Line l2 = new Line(n1, n2);
		Line l3 = new Line(n4, n5);

		l2.setColor(Color.DARK_GRAY);
		l1.setColor(Color.GREEN);
		n3.setColor(Color.red);
		n2.setColor(Color.pink);
		n1.setName("A");
		n2.setName("B");
		n3.setName("C");

		panel.graph.addNode(n4);
		panel.graph.addNode(n5);
		panel.graph.addLine(l1);
		panel.graph.addLine(l3);
		panel.graph.addLine(l2);
		panel.graph.addNode(n1);
		panel.graph.addNode(n2);
		panel.graph.addNode(n3);

		panel.setGraph(panel.graph);
	}

	public static void main(String[] args) {
		GraphEditor ge = new GraphEditor();
		ge.buildExample();
	}

	/*
	 * public void saveTo(String p, String content) { Path path = Paths.get(p);
	 * byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
	 * 
	 * try { Files.write(path, bytes);
	 * System.out.println("Successfully written data to the file"); } catch
	 * (IOException e) { e.printStackTrace(); } } public void loadFrom() {
	 * FileChooser fc = new FileChooser("Choose bin file"); try {
	 * 
	 * byte[] allBytes = Files.readAllBytes(Paths.get(fc.getPath())); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * }
	 */
	
	public void printBin(String filename) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename) );
			oos.writeInt(panel.graph.getNodes().length);
			for(Node n:panel.graph.nodes)
				oos.writeObject(n);
			oos.writeInt(panel.graph.getLines().length);
			for(Line n:panel.graph.lines)
				oos.writeObject(n);
			
			oos.close();
		} catch (Exception e1) {
			System.out.println("Fail writing objects");
		}
		
		return;
	}
	
	public void readBin(String filename)
	{
		try {
			ArrayList<Node> str = new ArrayList<Node>();
			ObjectInputStream ios = new ObjectInputStream( new FileInputStream(filename));
			int k = ios.readInt();
			
			for(int i=0;i<k;i++)
				panel.graph.addNode((Node)ios.readObject());
			
			k = ios.readInt();
			for(int i=0;i<k;i++)
				panel.graph.addLine((Line)ios.readObject());
			
			ios.close();
		
		} catch (Exception e) {
			System.out.println(e.getClass());
		}
		repaint();
		
		}
}
