package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import program.GameManager;
import world.World;
import ai.StateMachine;

/**
 * Screen to set up a tournament
 * @author kris
 *
 */
public class TournamentScreen extends JFrame{
	private GameManager gm;
	private final JFileChooser fc;
	
	Font bigFont;
	
	//buttons
	JButton browseWorldButton;
	JButton generateWorldButton;
	JButton browseBrainButton;
	JButton addBrainButton;
	JButton beginButton;
	JButton backButton;
	
	//text fields
	JTextField brainName;
	JTextField brainFile;
	JTextField worldFile;
	
	//table
	JTable table;
	JScrollPane tableScroll;
	
	/**
	 * Constructor
	 */
	public TournamentScreen(GameManager gm){
		super("Tournament Mode");
		this.gm = gm;
		fc = new JFileChooser();
		bigFont = new Font("Arial", Font.BOLD, 20);
		
		//make buttons
		backButton = new JButton(new AbstractAction("Back"){
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		beginButton = new JButton(new AbstractAction("Begin"){
			public void actionPerformed(ActionEvent e) {
				startTournament();
			}
		});
		beginButton.setEnabled(false);
		browseWorldButton = new JButton(new AbstractAction("Browse"){
			public void actionPerformed(ActionEvent e) {
				loadMap();
			}
		});
		generateWorldButton = new JButton(new AbstractAction("Generate"){
			public void actionPerformed(ActionEvent e) {
				generateMap();
			}
		});
		browseBrainButton = new JButton(new AbstractAction("Browse"){
			public void actionPerformed(ActionEvent e) {
				browseBrain();
			}
		});
		addBrainButton = new JButton(new AbstractAction("Add"){
			public void actionPerformed(ActionEvent e) {
				addBrain();
			}
		});
		
		//make text fields
		brainName = new JTextField(20);
		brainFile = new JTextField(20);
		worldFile = new JTextField(20);
		
		//make table
		String[] colHeadings = {"Name","Brain"};
		int numRows = 14;
		DefaultTableModel model = new DefaultTableModel(numRows, colHeadings.length) ;
		model.setColumnIdentifiers(colHeadings);
		table = new JTable(model);
		tableScroll = new JScrollPane(table);
		tableScroll.setPreferredSize(new Dimension(300, 18*numRows));
		
		
		//place components of GUI
		addComponents();
		
	    //  final initialization
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
	    this.pack();
	    this.setLocationRelativeTo(null);	
	    this.setVisible(true);  
		
	}

	protected void addBrain() {
		// TODO Auto-generated method stub
		
	}

	protected void browseBrain() {
		// TODO Auto-generated method stub
		
	}

	protected void generateMap() {
		// TODO Auto-generated method stub
		
	}

	protected void loadMap() {
		// TODO Auto-generated method stub
		
	}

	protected void startTournament() {
		// TODO Auto-generated method stub
		
	}

	private void addComponents() {
		Container pane = this.getContentPane();
		
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel mainPanel = new JPanel(gbl);
		gbc.insets = new Insets(10,10,10,10);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		JLabel worldLabel = new JLabel("World", JLabel.CENTER);
		worldLabel.setFont(bigFont);
		mainPanel.add(worldLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		mainPanel.add(new JLabel("File", JLabel.LEFT), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		mainPanel.add(worldFile, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		mainPanel.add(browseWorldButton, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 1;
		mainPanel.add(generateWorldButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		JLabel brainLabel = new JLabel("Ant Brain", JLabel.CENTER);
		brainLabel.setFont(bigFont);
		mainPanel.add(brainLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		mainPanel.add(new JLabel("Name", JLabel.LEFT), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		mainPanel.add(brainName, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		mainPanel.add(new JLabel("File", JLabel.LEFT), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		mainPanel.add(brainFile, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 4;
		mainPanel.add(browseBrainButton, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(addBrainButton, gbc);
		
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		gbc.gridheight = 6;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(tableScroll, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 8;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(beginButton, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 9;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(backButton, gbc);
		

		
		
		pane.add(mainPanel);
	}
	
}

