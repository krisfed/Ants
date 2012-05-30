package ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class BracketScreen extends JFrame {

	private TournamentBracket tournamentBracket;
	private String player1;
	private String player2;
	private ArrayList<Object> matches;
	private Font font = new Font("Tahoma", Font.BOLD, 20);
	
	public BracketScreen(ArrayList<Object> matches, int nPlayers, String player1, String player2){
		super("Tournament Bracket");
		this.player1 = player1;
		this.player2 = player2;
		tournamentBracket = new TournamentBracket(matches, nPlayers);
		
		//arrange components
		Container pane = this.getContentPane();
		JPanel mainPanel = new JPanel(new GridLayout(2,1));
		// P1 VS P2 part
		JLabel playersLabel = new JLabel(player1 + " VS. " + player2, JLabel.CENTER);
		playersLabel.setFont(font);
		mainPanel.add(playersLabel);
		
		// Bracket part
		JScrollPane bracketScroll = new JScrollPane(tournamentBracket, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//bracketScroll.setPreferredSize(new Dimension(500, 500));
		mainPanel.add(bracketScroll);
		pane.add(mainPanel);
		
		
		this.setResizable(false);
	    this.pack();
	    this.setLocationRelativeTo(null);	
	    this.setVisible(true); 
	}


}

