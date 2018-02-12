package commandline;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * This class represents an single Top Trumps game. 
 * It contains all the methods necessary to run a game, 
 * stores information about a single game, and updates
 * it as the game progresses.
 */

public class Game 
{
	/**
	 *  instance variables
	 */
	public int numberOfPlayers; // number of players in game
	private int remainingPlayers; // players still in game
	public String username; // chosen username
	private Deck currentDeck;
	private Round newRound;
	private Player activePlayer; // active player makes the category choice
	private Player currentWinner;
	private int currentCategory;
	private static ArrayList <Player> listOfPlayers;
	private static int gameNumber;
	
	private int drawCount; // number of draws in game
	private int roundCount = 0; // always starts from 0
	
	private boolean writeToLog = false;
	private static boolean isFinished;	
//	private static DatabaseConnection db;
	
	// class constants below
	private static final String FILE_NAME = "StarCitizenDeck.txt"; // name of deck file
	private static final String newLine = (System.getProperty("line.separator"));
	private static final String logSeparator = newLine + 
			"------------------------------------------------------------------------------------------------" + newLine;
	private static final String LOG_FILE = "toptrumps.log";
	
	/**
	 * Constructor method. 
	 * Creates a new Game object and connects to the database.
	 * Called by the TopTrumpsCLIApplication.java class (the online version does not use the Game.java class).
	 * @param Deck d = current deck 
	 */

	public Game ()
	{	
	//	this.db = db;
	//	gameNumber = db.getNumberOfGames()+1;
		
		roundCount = 1;
		drawCount = 0; 
		isFinished = false;
	}

	/**
	 * Initialises a new game (shuffles deck, creates players and deals cards). 
	 * Called from the TopTrumpsCLIApplication.java class.
	 */

	public void initialiseGame()

	{
		readIn();
	
		boolean logExists = false;
		if (writeToLog = true)
		{
			logDeck(currentDeck,logExists);
		}

		currentDeck.shuffleDeck();

		if (writeToLog = true)
		{
			logExists = true;
			logDeck(currentDeck, logExists); //prints shuffled deck to log file
		}

		remainingPlayers = numberOfPlayers; // starts with all players still in game

		createPlayers();
		dealCards();
		// choose the 1st active player

	} 
	
	public void writeToLog(boolean w)
	{
		writeToLog = true;
	}

	
	/**
	 * rounds continue until there is only 1 player left
	 * the last remaining player is the winner 
	 */
	public void startRound() 
	{	
		newRound = new Round(roundCount, writeToLog);
		newRound.setPlayers(listOfPlayers);
		newRound.setActivePlayer(activePlayer);
		newRound.setCategroy(currentCategory);
		newRound.playRound();

		finishRound();  
	}


	/**
	 * 
	 */
	
	private void finishRound() 

	{
		updatePlayers(); // updates number of remaining players

		if (remainingPlayers <= 1)

		{
			isFinished = true; 
			
			if (writeToLog == true)
			{
				logGameWinner();
			}
			
		//	db.updateDB(getGameData(), getRoundData());
		}

		if (newRound.isDraw() == true)
		{
			drawCount++;
		}

		if (newRound.isDraw() == false)
		{
			currentWinner = newRound.getWinner();
			currentWinner.addWin();
		}
		
		roundLog();
		logCards();
		roundCount++;
	}


	/**
	 * Reads deck contents from a file.
	 * Sets categories for the new deck (contained in the first line of the file).
	 * Add cards to the deck (each card is a new line).
	 * Uses FILE_NAME, which is stored as a class constant.
	 */
	private void readIn()
	{
		currentDeck = new Deck();

		FileReader reader;
		try 
		{
			reader = new FileReader(FILE_NAME);

			Scanner in = new Scanner (reader);
			String line = in.nextLine();

			// sets categories 
			currentDeck.setCategories(line);

			// adds cards to the deck
			while (in.hasNextLine())
			{
				line = in.nextLine();	
				currentDeck.addCard(line);
			}

		} 

		// in case there is no file
		catch (FileNotFoundException e) 
		{
			System.out.println("File not Found");
			System.out.println("Check if file name is correct");
			System.out.println();
		}

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
		Player h = new Player();
		h.setPlayerName(username);
		h.setHuman();
		listOfPlayers.add(h);

		// create AI players
		for (i = 1; i < numberOfPlayers; i++) 
		{
			Player p = new Player();
			p.setPlayerName("Player" + i);
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

			listOfPlayers.get(pickRandomPlayer()).receiveCards(currentDeck.getDeck());
			

		}


		if (writeToLog == true)
		{
			logCards(); // right after they have been dealt
		}

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

	public void chooseActivePlayer()

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
	 * 
	 * 
	 * @param d = current deck 
	 * @param logExists  
	 */

	private void logDeck(Deck d, boolean logExists)	{ //for printing to output log

		PrintWriter printer = null;

		try {
			try {
				FileWriter fw = new FileWriter(LOG_FILE, true);
				BufferedWriter bw = new BufferedWriter(fw);
				printer = new PrintWriter(bw);
				String deck = "";
				String deckDescriptor = "";

				{

					if (logExists == true) {
						deck = currentDeck.dString();
						deckDescriptor = "Shuffled deck " + newLine;

					}

					else {
						fw = new FileWriter(LOG_FILE, false); //overwrite log contents if new game
						deck = d.dString();
						deckDescriptor = "Deck as read from file" + newLine;	
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

	private void logCards() {

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
						playerCards = playerCards + p.handToString();
					}

				}

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
					printer.print(newRound.getRoundLog());
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
					printer.println(newLine + currentWinner.getName() + " WON THE GAME!");
					
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
	
	public Player getHumanPlayer ()
	{
		return listOfPlayers.get(0);
  }	

	public void setCurrentCategory(int c)

	{
		currentCategory = c;
	}

	/**
	 * getter methods below
	 */

	// return whether game  is finished
	public boolean isFinished()
	{
		return isFinished;

	}

	// return the winner of the game
	public Player getWinner()
	{
		// if only one player is left with cards after a draw
		// they automatically become the game winner
		if (newRound.isDraw())		
		{
			for (int i=0; i<listOfPlayers.size(); i++)
			{
				if (listOfPlayers.get(i).isInGame())
					currentWinner = listOfPlayers.get(i); 
			}
		}

		else // the last "standing" player becomes the winner
		{
			currentWinner = newRound.getWinner();
		}
		
		return currentWinner;
	}

	private String getGameData()
	{
		StringBuilder gData = new StringBuilder("");
		gData.append("'" + gameNumber + "', ");
		gData.append("'" + numberOfPlayers + "',");
		gData.append("'" + currentWinner.getName() + "'");

		String gameData = gData.toString();
		return gameData;
	}

	private String getRoundData()
	{
		StringBuilder rData = new StringBuilder("");
		rData.append("'" + gameNumber + "', ");
		rData.append("'" + roundCount + "', ");
		rData.append("'" + drawCount + "', ");

		rData.append(getWinsPerPlayer(0) + ", ");
		rData.append(getWinsPerPlayer(1) + ", ");
		rData.append(getWinsPerPlayer(2) + ", ");
		rData.append(getWinsPerPlayer(3) + ", ");
		rData.append(getWinsPerPlayer(4));

		String roundData = rData.toString();
		return roundData;
	}
	

	private String getWinsPerPlayer(int i)
	{
		if (listOfPlayers.size()<=i)
		{
			return ("NULL");
		}

		else
		{
			return ("'" + String.valueOf(listOfPlayers.get(i).getRoundWins() + "'"));
		}
	}

	// return current active player
	public Player getActivePlayer()
	{
		return activePlayer;
	}

	public Player getPlayer(int i)
	{
		return listOfPlayers.get(i);
	}

	public boolean isDraw ()
	{
		return newRound.isDraw();
	}
	
	public int getRoundCount ()
	{
		return roundCount;
	}

	public boolean wasADraw()
	{
		return newRound.isDraw();
	}

}

