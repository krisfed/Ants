package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import enums.E_Color;

import ai.StateMachine;

import program.GameManager;
import world.World;

/**
 * Screen to set up a single match
 * @author kris
 *
 */
public class MatchScreen extends JFrame {
	
	private GameManager gm;
	private final JFileChooser fc;
	
	
	//buttons:
	JButton browseRedButton;
	JButton browseBlackButton; 
	JButton browseWorldButton;
	JButton generateWorldButton;
	JButton backButton;
	JButton fightButton;
	
	//text fields
	JTextField blackName;
	JTextField redName;
	JTextField blackFile;
	JTextField redFile;
	JTextField worldFile;
	
	/**
	 * Constructor
	 */
	public MatchScreen(GameManager gm){
		super("Standard Match");
		this.gm = gm;
		fc = new JFileChooser();
		
		
		//make Buttons
		backButton = new JButton(new AbstractAction("Back"){
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		fightButton = new JButton(new AbstractAction("Fight!"){
			public void actionPerformed(ActionEvent e) {
				startMatch();
			}
		});
		fightButton.setEnabled(false);
		browseWorldButton = new JButton(new AbstractAction("Browse"){
			public void actionPerformed(ActionEvent e) {
				loadMap();
			}
		});
		browseRedButton = new JButton(new AbstractAction("Browse"){
			public void actionPerformed(ActionEvent e) {
				chooseBrain(E_Color.RED);
			}
		});
		browseBlackButton = new JButton(new AbstractAction("Browse"){
			public void actionPerformed(ActionEvent e) {
				chooseBrain(E_Color.BLACK);
			}
		});
		generateWorldButton = new JButton(new AbstractAction("Generate"){
			public void actionPerformed(ActionEvent e) {
				generateMap();
			}
		});
		
		//make text fields
		blackName = new JTextField(20);
		redName = new JTextField(20);
		blackFile = new JTextField(20);
		redFile = new JTextField(20);
		worldFile = new JTextField(20);
		
		worldFile.addFocusListener(new WorldFocusListener());
		redFile.addFocusListener(new BrainFocusListener(E_Color.RED));
		blackFile.addFocusListener(new BrainFocusListener(E_Color.BLACK));		
				
		
		//place components of GUI
		addComponents();
		
	    //  final initialization
		//this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
	    this.pack();
	    this.setLocationRelativeTo(null);	
	    this.setVisible(true);  
		
	}

	/**
	 * Opens a dialog window to allow user to choose the brain file,
	 * checks its validity
	 * 
	 * @param color the color of brain being chosen
	 */
	private void chooseBrain(E_Color color) {
		int r = fc.showOpenDialog(this);
		if (r == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();		
			
			checkBrain(file.getAbsolutePath(), color);
		}
		
		//	Check if we can start the match
		if ("".compareTo(blackFile.getText()) != 0 && 
				"".compareTo(redFile.getText()) != 0 && 
				gm.getWorld() != null) {
			fightButton.setEnabled(true);
		}
	
	}
	

	/**
	 * Checks if the file at a specified path can be
	 * a valid ant brain, if not clears the corresponding text field
	 * @param absPath path to the file
	 * @param color the color of the brain
	 */
	private void checkBrain(String absPath, E_Color color){
		if (StateMachine.newInstance(absPath) == null){
			JOptionPane.showMessageDialog(this, "Error!\nThis Ant Brain file contains syntax errors.\n"+
					"Please check the contents of this file or try uploading a different one.", "Error", JOptionPane.ERROR_MESSAGE);
			switch(color){
			case RED:
				redFile.setText("");
				break;
			case BLACK:
				blackFile.setText("");
			}
		} else {
			switch(color){
			case RED:
				redFile.setText(absPath);
				break;
			case BLACK:
				blackFile.setText(absPath);
			}
		}
	}

	/**
	 * Opens a dialog window to allow user to choose the map file;
	 * checks if it is a valid map
	 */
	private void loadMap() {
		int r = fc.showOpenDialog(this);
		if (r == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile(); 
			checkAndSetMap(file.getAbsolutePath());
		}

		//	Check if we can start the match
		if ("".compareTo(blackFile.getText()) != 0 && 
				"".compareTo(redFile.getText()) != 0 && 
				gm.getWorld() != null) {
			fightButton.setEnabled(true);
		}
		
	}

	/**
	 * Checks the file at a given path if it is a valid map;
	 * if valid, sets it as a world's map 
	 * @param absPath path to the map file
	 */
	private void checkAndSetMap(String absPath){
		if (gm.setWorld(World.parseWorld(absPath)))
		{
			worldFile.setText(absPath);
		} else {
			worldFile.setText("");
			JOptionPane.showMessageDialog(this, "Error!\nThis Map text file is invalid.\n"+
		"Please check the contents of this file or try uploading a different one.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Sets the world's map to auto-generated map
	 */
	private void generateMap(){
		gm.setGeneratedWorld();
		worldFile.setText("(auto-generated map)");
		
		//	Check if we can start the match
		if ("".compareTo(blackFile.getText()) != 0 && 
				"".compareTo(redFile.getText()) != 0 && 
				gm.getWorld() != null) {
			fightButton.setEnabled(true);
		}
	}
	
	
	
	/**
	 * Sets the red and black brains and starts the match
	 */
	private void startMatch() {
		
		
		//determine team names
		String redTeamName;
		String blackTeamName;
		
		System.out.println(redName.getText());
		if(redName.getText().compareTo("") == 0){
			redTeamName = new File(redFile.getText()).getName();
		} else {
			redTeamName = redName.getText();
		}
		System.out.println("redTeamName: "+redTeamName);
		
		System.out.println(blackName.getText() );
		if(blackName.getText().compareTo("") == 0){
			blackTeamName = new File(blackFile.getText()).getName();
		} else {
			blackTeamName = blackName.getText();
		}
		System.out.println("blackTeamName: "+blackTeamName);
		
		boolean success = gm.addBrain(redTeamName, StateMachine.newInstance(redFile.getText())) &&
				gm.addBrain(blackTeamName, StateMachine.newInstance(blackFile.getText()));
		
				
		if (!success){
			//shouldn't happen
			JOptionPane.showMessageDialog(this, "Error!\nThis Ant Brain file is invalid.\n"+
					"Please check the contents of this file or try uploading a different one.", "Error", 
					JOptionPane.ERROR_MESSAGE);
		}
//		
//		String redTeamName = "red";
//		String blackTeamName ="black";
//		
//		gm.addBrain(redTeamName, StateMachine.newInstance(redFile.getText()));
//		gm.addBrain(blackTeamName, StateMachine.newInstance(blackFile.getText()));
		

		System.out.println("brains are set");
		
		
		gm.playMatch(redTeamName, blackTeamName);
		
		this.setVisible(false);
		this.dispose();
		


		
	}

	/**
	 * Place components of the GUI on the frame 
	 * with a grid bag layout
	 */
	private void addComponents() {
		Container pane = this.getContentPane();
		
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel mainPanel = new JPanel(gbl);
		gbc.insets = new Insets(10,10,10,10);
		
		gbc.gridx=1;
		gbc.gridy=0;
		mainPanel.add(new JLabel("Red Team", JLabel.CENTER), gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		mainPanel.add(new JLabel("Name", JLabel.LEFT), gbc);
		
		gbc.gridx=1;
		gbc.gridy=1;
		mainPanel.add(redName, gbc);
		
		gbc.gridx=0;
		gbc.gridy=2;
		mainPanel.add(new JLabel("File", JLabel.LEFT), gbc);

		gbc.gridx=1;
		gbc.gridy=2;
		mainPanel.add(redFile, gbc);
		
		
		gbc.gridx=2;
		gbc.gridy=2;
		mainPanel.add(browseRedButton, gbc);
		
		gbc.gridx=1;
		gbc.gridy=3;
		mainPanel.add(new JLabel("Black Team", JLabel.CENTER), gbc);
		
		gbc.gridx=0;
		gbc.gridy=4;
		mainPanel.add(new JLabel("Name", JLabel.LEFT), gbc);
		
		gbc.gridx=1;
		gbc.gridy=4;
		mainPanel.add(blackName, gbc);
		
		gbc.gridx=0;
		gbc.gridy=5;
		mainPanel.add(new JLabel("File", JLabel.LEFT), gbc);
		
		gbc.gridx=1;
		gbc.gridy=5;
		mainPanel.add(blackFile, gbc);
		
		gbc.gridx=2;
		gbc.gridy=5;
		mainPanel.add(browseBlackButton, gbc);
		
		gbc.gridx=1;
		gbc.gridy=6;
		mainPanel.add(new JLabel("World", JLabel.CENTER), gbc);
		
		gbc.gridx=0;
		gbc.gridy=7;
		mainPanel.add(new JLabel("File", JLabel.LEFT), gbc);
		
		gbc.gridx=1;
		gbc.gridy=7;
		mainPanel.add(worldFile, gbc);
		
		gbc.gridx=2;
		gbc.gridy=7;
		mainPanel.add(browseWorldButton, gbc);
		
		gbc.gridx=3;
		gbc.gridy=7;
		mainPanel.add(generateWorldButton, gbc);
		
		gbc.gridx=1;
		gbc.gridy=8;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(backButton, gbc);
		
		gbc.gridx=2;
		gbc.gridy=8;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(fightButton, gbc);
		
		
		pane.add(mainPanel);
		
	}
	
	//         ----- nested classes ------
	
	/**
	 * Focus listener for world text field
	 */
	class WorldFocusListener extends FocusAdapter{
		
		String prevText = "";
				
		/**
		 * Checks the path specified in the world text field
		 */
		public void focusLost(FocusEvent e){
			if (("".compareTo(worldFile.getText()) != 0) && 
					(prevText.compareTo(worldFile.getText()) != 0) &&
					("(auto-generated map)".compareTo(worldFile.getText()) != 0)){
				checkAndSetMap(worldFile.getText());
			}
			prevText = worldFile.getText();
			
		}
	}
	
	/**
	 * Focus listener for brain text fields
	 */
	class BrainFocusListener extends FocusAdapter{
		
		String prevText = "";
		E_Color color;
		JTextField brainFile;
		
		/**
		 * Constructor
		 * @param color brain text field of which color
		 */
		public BrainFocusListener(E_Color color){
			super();
			this.color = color;
			switch(color){
			case RED:
				this.brainFile = redFile;
				break;
			case BLACK:
				this.brainFile = blackFile;
				break;
			}
		
			
		}
				
		/**
		 * Checks the path specified in the brain text field
		 */
		public void focusLost(FocusEvent e){
			//if text field has changed and is not empty
			if (("".compareTo(brainFile.getText()) != 0) && 
					(prevText.compareTo(brainFile.getText()) != 0)){
				checkBrain(brainFile.getText(), color);			
			}
			prevText = brainFile.getText();
			
		}
	}

}
