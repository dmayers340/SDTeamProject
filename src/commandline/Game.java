package commandline;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Game 

{
	/**
	 *  instance variables
	 */
	public int numberOfPlayers; // number of players in game

	private int remainingPlayers; // players still in game
	private Deck currentDeck;
	private Round newRound;
	private Player activePlayer; // active player makes the category choice
	public String username;
	private Player gameWinner;
	private DatabaseConnection db;

	private String logSeparator = "-------------------------------------------------------------"+
			"-------------------------";

	private static ArrayList <Player> listOfPlayers;
	private final String LOG_FILE = "toptrumps.log";

	/**
	 * Constructor method. 
	 * Creates a new Game object and shuffles the deck, then writes the deck contents to a log file.
	 * Called by the TopTrumpsCLIApplication.java class (the online version does not use the Game.java class).
	 * @param Deck d = current deck 
	 */
	
	public Game (Deck d, DatabaseConnection db)
	{	
		// this is all for testing
		this.db = db;
		boolean deckOutputToLog = false;
		logDeck(d,deckOutputToLog);

		d.shuffleDeck();
		deckOutputToLog = true;
		currentDeck = d;
		logDeck(currentDeck, deckOutputToLog); //prints shuffled deck to log file
	}
	

	/**
	 * Starts a new game (creates players and deals cards). 
	 * Then executes high-level game logic until the winner is decided.
	 * Called from the TopTrumpsCLIApplication.java class.
	 */
	
	public void playGame()

	{
		remainingPlayers = numberOfPlayers; // starts with all players still in game

		createPlayers();
		dealCards();
		logDealtCards(); // right after they have been dealt

		/**
		 * rounds continue until there is only 1 player left
		 * the last remaining player is the winner 
		 */

		while (remainingPlayers > 1)

		{
			chooseActivePlayer(); // set deciding player 
			newRound = new Round(listOfPlayers, activePlayer);

			if (Round.getRoundCount() > 1) {
				logDealtCards(); 
			}

			newRound.playRound();
			roundLog();
			System.out.println(logSeparator);
			updatePlayers(); // updates number of remaining players
		}

		newRound.getWinner();
		displayWinner();
		db.updateDBRounds();
		//		Round.getRoundCount());
		//		db.updateDBRounds(Round.getRoundCount(), newRound.getDrawCount(), Round.getPlayerRoundWins());
		// db.updateDBGame(numberOfPlayers, gameWinner.getName());
		
	}
	
	
	/**
	 * Called from the playGame() method.
	 * Generates a number of Player objects and stores them in an ArrayList.
	 * The first created Player is always the human player.
	 * Human player has to choose their username, the others are assigned default usernames.
	 */	
	
	private void createPlayers() {

		listOfPlayers = new ArrayList<Player>();

		int i = 0;

		// creates the human player
		Player h = new Player(username);
		h.setHuman();
		listOfPlayers.add(h);

		// create AI players
		for (i = 1; i < numberOfPlayers; i++) 
		{
			Player p = new Player("AI_Player" + i);
			listOfPlayers.add(p);
		}

	}
	

	/**
	 * Called from the playGame() method.
	 * Removes cards from the current deck and adds them to the hands of players.
	 * At the end the current deck is left empty.
	 */
	
	private void dealCards() {

		int numCardsEach = currentDeck.getNumberOfCards() / numberOfPlayers; // how many cards each player should get

		int i;
		for (i = 0; i < numberOfPlayers; i++) {

			ArrayList<Card> cardsForEachPlayer = new ArrayList<Card>(currentDeck.getDeck().subList(0, numCardsEach));  
			listOfPlayers.get(i).receiveCards(cardsForEachPlayer); // gives cards
			currentDeck.getDeck().removeAll(cardsForEachPlayer); // removes from current deck

		}

		if (!currentDeck.getDeck().isEmpty()) { //if cards remaining in deck

			listOfPlayers.get(pickRandomPlayer()).receiveExtraCards(currentDeck.getDeck());

		}

		System.out.println("Dealing cards...");
		System.out.println();

	}
	

	/**
	 * The method is called after each round.
	 * If the player has no cards, sets the Player.java isInGame instance variable to false 
	 * and updates the number of remaining players.
	 */
	
	private void updatePlayers ()

	{
		for (int i=0; i<listOfPlayers.size(); i++)

		{
			Player p = listOfPlayers.get(i);

			if (p.isInGame() && p.getHand().size()<1)

			{
				System.out.println(p.getName() + " HAS NO CARDS LEFT");
				p.setStatus(false); // sets the value of isInGame to negative
				remainingPlayers--;
			}
		}
	}


	/**
	 * Determines the next active player.
	 * If no rounds have been played, selects a random player.
	 * If previous round was a draw, the active player stays the same.
	 * Otherwise, the winner of the previous round becomes the active player.
	 */
	
	private void chooseActivePlayer()

	{
		if (newRound==null) // if new game

		{
			activePlayer = listOfPlayers.get(pickRandomPlayer());
		}

		else if (newRound.getWinner() == null) // if draw

		{
			return;  
		}

		else // otherwise

		{
			activePlayer = newRound.getWinner();
		}

	}
	

	/**
	 * Generates a random number that is then used as index when choosing the first active player. 
	 * The number cannot be greater than the total number of players.
	 * @return random integer
	 */
	
	private int pickRandomPlayer() { 

		int randomIndex = (int)Math.floor(Math.random() * numberOfPlayers);
		return randomIndex;
	}


	/**
	 *  Called at the end of a game, displays the final winner of the game. 
	 *  If human player won the game, prints out a "congratulations" message.
	 */
	
	private void displayWinner ()

	{
		// if only one player is left with cards after a draw
		// they automatically become the winner
		if (newRound.isDraw())		
		{
			for (int i=0; i<listOfPlayers.size(); i++)
			{
				if (listOfPlayers.get(i).isInGame())
					gameWinner = listOfPlayers.get(i); 
			}
		}

		else // the last "standing" player becomes the winner
		{
			gameWinner = newRound.getWinner();
			logGameWinner();
		}

		System.out.println("The winner of the game is " + gameWinner.getName());

		if (gameWinner == listOfPlayers.get(0)) // human player always has index 0
		{
			System.out.print ("\n CONGRATULATIONS! \n You just won the game!");
		}

	}



	/**
	 * 
	 * 
	 * @param d = current deck 
	 * @param deckOutput  
	 */

	private void logDeck(Deck d, boolean deckOutput)	{ //for printing to output log

		PrintWriter printer = null;

		try {
			try {
				FileWriter fw = new FileWriter(LOG_FILE, true);
				BufferedWriter bw = new BufferedWriter(fw);
				printer = new PrintWriter(bw);
				String deck = "";
				String deckDescriptor = "";

				{

					if (deckOutput == true) {
						deck = currentDeck.dString();
						deckDescriptor = "Shuffled deck\n";

					}

					else {
						fw = new FileWriter(LOG_FILE, false); //overwrite log contents if new game
						deck = d.dString();
						deckDescriptor = "Deck as read from file\n";
						printer.println(logSeparator);	
					}



					printer.println(deckDescriptor);
					printer.println(deck);
					printer.println(logSeparator);
				}


			}
			finally {

				if (printer != null) {
					printer.close();
				}
			} 	
		}
		catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "File not found",
					"Error", JOptionPane.ERROR_MESSAGE);

		}				

	}

	/**
	 * 
	 */
	
	private void logDealtCards() {

		PrintWriter printer = null;

		try {
			try {
				FileWriter fw = new FileWriter(LOG_FILE, true);
				BufferedWriter bw = new BufferedWriter(fw);
				printer = new PrintWriter(bw);

				String playerCards = "";

				{ 

					for (Player p: listOfPlayers)

					{
						playerCards = playerCards + p.handToString() + logSeparator;
					}

				}

				System.out.println(playerCards);
				printer.println(playerCards);

			}

			finally {

				if (printer != null) {
					printer.close();
				}
			} 	
		}
		catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "File not found",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * 
	 */
	
	private void roundLog() {

		PrintWriter printer = null;

		try {
			try {
				FileWriter fw = new FileWriter(LOG_FILE, true);
				BufferedWriter bw = new BufferedWriter(fw);
				printer = new PrintWriter(bw);

				{ 
					printer.println();
					printer.println(newRound.getRoundLog());
				}
			}

			finally {

				if (printer != null) {
					printer.close();
				}
			} 	
		}
		catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "File not found",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}


	/**
	 * 
	 */
	
	private void logGameWinner() {

		PrintWriter printer = null;

		try {
			try {
				FileWriter fw = new FileWriter(LOG_FILE, true);
				BufferedWriter bw = new BufferedWriter(fw);
				printer = new PrintWriter(bw);

				{ 
					printer.println(gameWinner.getName() + " WON THE GAME!");
				}
			}

			finally {

				if (printer != null) {
					printer.close();
				}
			} 	
		}
		catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "File not found",
					"Error", JOptionPane.ERROR_MESSAGE);
		}




	}


	/**
	setter methods below
	accessed from the ToptrumpsCLIApplication.java class
	 */

	public void setNumberOfPlayers(int n) 
	{
		numberOfPlayers=n;
	}

	public void setUsername(String u)

	{
		username = u;	
	}

}

