package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import program.GameManager;

/**
 * The start up screen of the game.
 * @author kris
 *
 */
public class StartUpScreen extends JFrame {

	//private static BufferedImage titleImage;
	private static ImageIcon titleImage;
	private GameManager gm;
	
	/**
	 * Constructor
	 */
	public StartUpScreen(GameManager gm){
		super("Ants");
		this.gm = gm;
		
		// load title image
		try {
			//titleImage = ImageIO.read(new File("Images/ants_title.gif"));
			titleImage = new ImageIcon("Images/dummy_title.gif");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldn't load image.");
		}
		
		Container pane = this.getContentPane();
		
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(titleImage.getIconWidth(), titleImage.getIconHeight()+70));
		panel.add(new JLabel(titleImage), BorderLayout.NORTH);
		//buttons
		JButton matchButton = new JButton(new AbstractAction("Standard Match"){
			public void actionPerformed(ActionEvent e){
				startMatch();
			}
		});
		panel.add(matchButton, BorderLayout.WEST);
		JButton tournamentButton = new JButton(new AbstractAction("Tournament Mode"){
			public void actionPerformed(ActionEvent e) {
				startTournament();
			}
			
		});
		panel.add(tournamentButton, BorderLayout.EAST);
		JButton creditsButton = new JButton(new AbstractAction("Credits"){
			public void actionPerformed(ActionEvent e){
				viewCredits();
			}
		});
		panel.add(creditsButton, BorderLayout.SOUTH);
		
		
		pane.add(panel);
		
	    //  final initialization
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
	    this.pack();
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);  
		
	}

	private void startTournament() {
		System.out.println("startTournament");
		// TODO Auto-generated method stub
		
	}

	private void startMatch() {
		System.out.println("startMatch");
		MatchScreen matchScreen = new MatchScreen(gm);
		
	}

	private void viewCredits() {
		System.out.println("credits");
		CreditsScreen creditsScreen = new CreditsScreen();
		
	}
	

	
}
