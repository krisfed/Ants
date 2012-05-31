package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * A window to displays the bracket screen to show the state of 
 * the tournament.
 * @author kris
 */
public class BracketScreen extends JDialog {

	private TournamentBracket tournamentBracket;
	private String player1;
	private String player2;
	private ArrayList<Object> matches;
	private Font font = new Font("Tahoma", Font.BOLD, 30);
	
	public BracketScreen(ArrayList<Object> matches, int nPlayers, int round, String player1, String player2){
		//super(null, "Tournament Bracket");
		super();
		this.setModal(true); //to wait until the user presses begin
		//System.out.println("BracketScreen constructor");
		//System.out.println("isEventDispatchThread()" + SwingUtilities.isEventDispatchThread());
		this.player1 = player1;
		this.player2 = player2;
		tournamentBracket = new TournamentBracket(matches, nPlayers);
		
		//arrange components
		Container pane = this.getContentPane();
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		
		//text part
		JPanel textPanel = new JPanel(new GridLayout(2,1));
		JLabel roundLabel = new JLabel("Round " + round, JLabel.CENTER);
		roundLabel.setFont(font);
		textPanel.add(roundLabel);
		// P1 VS P2 part
		JLabel playersLabel = new JLabel(player1 + " VS " + player2, JLabel.CENTER);
		playersLabel.setFont(font);
		textPanel.add(playersLabel);
		mainPanel.add(textPanel, BorderLayout.NORTH);
		
		// Bracket part
		JScrollPane bracketScroll = new JScrollPane(tournamentBracket, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		bracketScroll.setPreferredSize(new Dimension(800, 500));
		mainPanel.add(bracketScroll, BorderLayout.CENTER);
		
		// Begin button
		JButton beginButton = new JButton(new AbstractAction("Begin"){
			public void actionPerformed(ActionEvent e) {
				dispose(); //just need to close the window
			}
			
		});

		beginButton.setPreferredSize(new Dimension(150, 40));
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(beginButton);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
				
		pane.add(mainPanel);
		
		//this.setResizable(false);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//for now only
	    this.pack();
	    this.setLocationRelativeTo(null);	
	    this.setVisible(true); 
	}


}

