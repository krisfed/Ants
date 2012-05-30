package program;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import ui.GameplayScreen;
import ui.MatchScreen;
import ui.StartUpScreen;
import ui.TournamentBracket;
import ui.TournamentScreen;
import world.World;
import ai.StateMachine;

/**
 * Manages the players, games and world which comprise an Ant tournament.
 * @author JOH
 * @version 0.2
 */
public class GameManager {

	private boolean debug = true;
	// The set of uploaded brains - identified by their filename
	private HashMap<String, StateMachine> playerBrains;
	private HashMap<String, Integer> playerScores;
	
	private ArrayList<Object> matches;

	private World world;
	
	private JFrame bracket;
	private TournamentBracket tournamentBracket;
	
	
	/**
	 * Constructor.
	 */
	public GameManager() {
		playerBrains = new HashMap<>();
		playerScores = new HashMap<>();
		setWorld(null);
		if (debug)
		{
			
			//StartUpScreen startUp = new StartUpScreen(this);
			//MatchScreen matchScreen = new MatchScreen(this);
			//playDummyMatch();
			//TournamentScreen tournScreen = new TournamentScreen(this);
			playDummyTourn();
			
		} else {
			TournamentScreen ui = new TournamentScreen(this);
		}
	}
	
	/**
	 * (recursively) creates a hierarchy of pairs 
	 * out of a given array list
	 * @param pairs list to combine into pairs
	 * @return multi-nested array list of pairs
	 */
	private ArrayList<Object> combineIntoPairs(ArrayList<Object> pairs){
		//if already only one pair, return as is
		if (pairs.size() == 2){
			//in case it's first recursion, and input is
			//in the form of ["String1", "String2"],
			//get them wrapped in arrays
			if(pairs.get(0).getClass() == "".getClass()){
				ArrayList<Object> arrayedPair = new ArrayList<>();
				for (int i=0; i<2; i++){
					ArrayList<Object >singularPair = new ArrayList<>();
					singularPair.add(pairs.get(i));
					arrayedPair.add(singularPair);
				}
				return arrayedPair;
			}
			return pairs;
		} else {
			ArrayList<Object> newPairs = new ArrayList<>(); //less pairs
			//while at least 2 elements
			while(pairs.size()>1){
				ArrayList<Object >pair = new ArrayList<>();
				//pick two elements at random to form a new pair, 
				//remove them from the list of available elements
				for (int i = 0; i<2; i++){
					Object pick = pairs.remove((int)(Math.random()*pairs.size()));
					//if a string, enclose in ArrayList:
					if(pick.getClass() == "".getClass()){
						ArrayList<Object >singularPair = new ArrayList<>();
						singularPair.add(pick);
						pair.add(singularPair);
					//else add as is:	
					} else {
						pair.add(pick);
					}
				}	
				//add the new pair
				newPairs.add(pair);
			}
			//if still one element left (odd number of elements),
			//add it as a pair of its own
			if(pairs.size()==1){
				Object lastOne = pairs.get(0);
				//if a string, enclose in ArrayList
				if(lastOne.getClass() == "".getClass()){
					ArrayList<Object >singularPair = new ArrayList<>();
					singularPair.add(lastOne);
					newPairs.add(singularPair);
				//else add as is:
				} else {
					newPairs.add(lastOne);
				}
			}
			//recursive call on a new collection of pairs:
			return combineIntoPairs(newPairs);
		}
	}
	
	/**
	 * Works out the correct matching of brains for a tournament to be held.
	 */
	public void assignMatches()
	{
		ArrayList<Object> players = new ArrayList<>();//players to assign
		players.addAll(playerBrains.keySet()); //add all team names
		
		matches = combineIntoPairs(players);
		System.out.println(matches);
//		for (Object pair : matches){
//			System.out.println(pair);
//		}

		displayBracket();
		System.out.println("==Result: " +playMatches(matches));
	}
	
	/**
	 * Simulation of the match: select the winner at random
	 * @param str1
	 * @param str2
	 * @return
	 */
	private Object pretendMatch(String str1, String str2){
		if(Math.random()>0.5){
			System.out.println("==="+str1+" vs. "+str2+ "; winner:" + str1);
			return str1;
		} else {
			System.out.println("==="+str1+" vs. "+str2+ "; winner:" + str2);
			return str2;
		}
	}
	
	/**
	 * Recursively plays out all the matches
	 * @param matchesToPlay
	 */
	public Object playMatches(ArrayList<Object> matchesToPlay){

		System.out.println("matchesToPlay: " +matchesToPlay);
		int i = matches.indexOf(matchesToPlay);
		//System.out.println("matches.indexOf(matchesToPlay): "+matches.indexOf(matchesToPlay));
		System.out.println("matches: " +matches);
		
		tournamentBracket.update(matches);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		//if only one element in array, return it
		if(matchesToPlay.size()==1){
			System.out.println("It's a loner");
			return matchesToPlay;
		} else {
			//break up the pair into parts
			ArrayList<Object> part1 = (ArrayList<Object>)matchesToPlay.get(0);
			ArrayList<Object> part2 = (ArrayList<Object>)matchesToPlay.get(1);
			
			//if it is the basic pair (i.e. [[player1], [player2]]),
			//play the match between them
			if( part1.get(0).getClass() == "".getClass() &&
					 part2.get(0).getClass() == "".getClass()){
				System.out.println("It's a final pair ready for combat");
				ArrayList<Object> resultArray = new ArrayList<>();
				resultArray.add(pretendMatch((String)part1.get(0), 
						(String)part2.get(0)));
				//replace the original pair with the winner
				if(i != -1){
					matches.set(i, resultArray);
				} else if (matches == matchesToPlay){
				 	matches = resultArray;
				}
				return playMatches(resultArray);
			}else {
				//explore one of the branches
				System.out.println("Need to go deeper");
				ArrayList<Object> resultArray = new ArrayList<>();
				
				//deal with only one branch at a time (hacky?..)
				if(part1.size()>1){
					resultArray.add(playMatches(part1));
					resultArray.add(part2);	
				} else {
					resultArray.add(part1);
					resultArray.add(playMatches(part2));
				}				

				//replace the original array with the reduced one
				if(i != -1){
					matches.set(i, resultArray);
				} else if (matches == matchesToPlay){
					matches = resultArray;
				}
				return playMatches(resultArray);
			}
			
		}
			
			
	}
	
	public void displayBracket(){
		bracket = new JFrame("Tournament Bracket");
		tournamentBracket = new TournamentBracket(matches, playerBrains.size());
		bracket.add(tournamentBracket);
		bracket.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//for now only
		bracket.pack();
		bracket.setLocationRelativeTo(null);
		bracket.setVisible(true);
	}
	
	public void playDummyMatch(){	
		setWorld(World.parseWorld("sample0.world"));
		addBrain("crapBrain.txt", StateMachine.newInstance("crapBrain.txt"));
		addBrain("exampleBrain.txt", StateMachine.newInstance("exampleBrain.txt"));
		playMatch("crapBrain.txt", "exampleBrain.txt");
	}
	
	public void playDummyTourn(){	
		setWorld(World.parseWorld("sample0.world"));
		int numberPlayers = 8;
		for (int i = 1; i <= numberPlayers; i++){
			addBrain("ex"+i, StateMachine.newInstance("exampleBrain.txt"));
		}
		assignMatches();
	}
	
	
	/**
	 * Plays a single match
	 * @param red string-key of the red team brain
	 * @param black string-key of the black team brain
	 */
	public void playMatch(String redName, String blackName){
		System.out.println(playerBrains);
		if((playerBrains.containsKey(redName))&&(playerBrains.containsKey(blackName))){
			world.setRedBrain(redName, this.getBrain(redName));
			world.setBlackBrain(blackName, this.getBrain(blackName));
			
			System.out.println("game set up");
			
			world.beginGame();
			showResults();
			world.closeScreen();
		
			world.swapBrains();
			world.beginGame();
			showResults();
			world.closeScreen();
		
		// if this is a singular match:
		this.resetBrains();
			
		}
		
	}
	
	private void showResults(){
		String winnerMessage;
		if (world.getBlackScore() > world.getRedScore()){
			winnerMessage = world.getBlackName() + " won!\n";
		} else if (world.getBlackScore() < world.getRedScore()){
			winnerMessage = world.getRedName() + " won!\n";
		} else {
			winnerMessage = "It's a draw!\n";
		}
		JOptionPane.showMessageDialog(null, 
			winnerMessage,
			"Pleased to announce the winner!", 
			JOptionPane.PLAIN_MESSAGE);
		
	}
	
	/**
	 * Sets the world's map to an auto-generated map
	 */
	public void setGeneratedWorld(){
		setWorld(World.generateMap());
	}
	
	/**
	 * Adds a new ant brain.
	 * @param name the name of the brain / player or team
	 * @param brain the StateMachine containing the brain
	 * @return true if succeeded, false if a brain with the same name is already loaded.
	 */
	public boolean addBrain(String name, StateMachine brain) {
		if (brain == null)
			return false;
		if (playerBrains.containsKey(name))
			return false;
		playerBrains.put(name, brain);
		playerScores.put(name, 0);
		return true;
	}
	
	/**
	 * Deletes all of the uploaded brains
	 * and their scores.
	 */
	public void resetBrains(){
		playerBrains = new HashMap<>();
		playerScores = new HashMap<>();
	}
	
	/**
	 * Returns a brain from the list.
	 * @param name the name of the brain to return
	 * @return the brain if successful, null otherwise
	 */
	public StateMachine getBrain(String name) {
		if(playerBrains.containsKey(name))
			return playerBrains.get(name);
		return null;
	}
	
	/**
	 * Returns an ant brain's overall score.
	 * @param name the name of the brain to score
	 * @return the score if name valid, -1 otherwise
	 */
	public int getScore(String name) {
		if (playerScores.containsKey(name))
			return playerScores.get(name);
		return -1;
	}
	
	/**
	 * Adds some number to a brain's score.
	 * @param name the name of the brain to affect
	 * @param scoreToAdd the number to add to current score
	 * @return true if succeeded, false otherwise
	 */
	public boolean addToScore(String name, int scoreToAdd) {
		if (!playerScores.containsKey(name))
			return false;
		playerScores.put(name, playerScores.get(name) + scoreToAdd);
		return true;
	}
	
	/**
	 * Returns the total number of brains currently under management.
	 * @return the number of brains
	 */
	public int getTotalPlayers()
	{
		return playerBrains.size(); 	
	}

	/**
	 * Returns the current world.
	 * @return
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Sets the current world.
	 * @param world
	 * @return
	 */
	public boolean setWorld(World world) {
		if (world == null)
			return false;
		this.world = world;
		return true;
	}
}
