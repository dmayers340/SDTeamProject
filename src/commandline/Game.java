package commandline;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Game {

	/**
	 *  instance variables
	 */

	public int numberOfPlayers; //we should assume there will always be 4 AI players
	
	

	private int remainingPlayers; // players still in game
	private Deck currentDeck;
	private Round newRound;
	private Player activePlayer;
	public String username;
	private Player gameWinner;

	private static ArrayList <Player> listOfPlayers;

	private final String logFile = "toptrumps.log";

	/**
	 * constructor method;
	 * every new game shuffles the deck
	 * @param d (current deck)
	 */

	public Game (Deck d)
	{	
		// this is all for testing
		System.out.println("running game class ; should print from deck");
		System.out.print(d.dString());
		System.out.println();

		boolean deckOutputToLog = false;
		logDeck(d,deckOutputToLog);

		d.shuffleDeck();
		deckOutputToLog = true;
		currentDeck = d;
		logDeck(currentDeck, deckOutputToLog); //prints shuffled deck to log file

		int p = TopTrumpsCLIApplication.howManyPlayers(); // user picks number of players
		numberOfPlayers = p+1; // AI players + human player
		remainingPlayers = p+1; // starts with all players still in game

		createPlayers();
		dealCards();
		
		logDealtCards(); // right after they have been dealt

		/**
		 * rounds continue until there is only 1 player left
		 * the last remaining player is the winner 
		 */

		while (remainingPlayers > 1)

		{
			setActivePlayer(); // set deciding player 
			newRound = new Round(listOfPlayers, activePlayer);

			if (newRound.getRoundCount() > 1) {
				logDealtCards(); 
			}
			
			 
			newRound.playRound();
			

			roundLog();

			System.out.println("----------------------------------------------------------------"+
					"-------------------------");
			

			updatePlayers(); // updates number of remaining players
		}
		
		newRound.getWinner();
		showWinner();

	}

	public int getNumberOfPlayers() {
		numberOfPlayers=TopTrumpsCLIApplication.howManyPlayers();
		return numberOfPlayers;
	}


//	public void setNumberOfPlayers(int numberOfPlayers) {
//		this.numberOfPlayers = numberOfPlayers;
//	}
//	
//	public Deck getCurrentDeck()
//	{
//		return currentDeck;
//	}


	/**
	 * updates number of remaining players
	 */

	private void updatePlayers ()

	{
		for (int i=0; i<listOfPlayers.size(); i++)

		{
			Player p = listOfPlayers.get(i);

			if (p.isInGame() && p.getHand().size()<1)

			{
				System.out.println(p.getName() + " HAS NO CARDS LEFT");
				p.setStatus(false);
				remainingPlayers--;
			}
		}
	}



	/**
	 * selects next active player
	 */

	private void setActivePlayer()

	{
		if (newRound==null) // if new game

		{
			activePlayer = listOfPlayers.get(pickRandomPlayer());
		}

		else if (newRound.getWinner() == null) // if draw

		{
			return;  
		}

		else 

		{
			activePlayer = newRound.getWinner();
		}

	}


	/**
	 * method print winnerGame
	 */

	private void showWinner ()

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

		else 
		{
			gameWinner = newRound.getWinner();
			logGameWinner();
		}

		System.out.println("The winner of the game is " + gameWinner.getName());

		if (gameWinner == listOfPlayers.get(0))
		{
			System.out.print ("\n" + 
					"�X�T�T�T�[���������������������������X�[�������X�[�������X�[\n" + 
					"�U�X�T�[�U�������������������������X�a�^�[�����U�U�����X�a�^�[\n" + 
					"�U�U���^�p�T�T�j�T�[�X�T�T�j�T�j�T�m�[�X�p�[�X�g�U�X�T�m�[�X�p�j�T�T�j�T�[�X�T�T�[\n" + 
					"�U�U���X�g�X�[�U�X�[�g�X�[�U�X�g�X�[�U�U�U�U�U�U�U�U�X�[�U�U�d�g�X�[�U�X�[�g�T�T�g\n" + 
					"�U�^�T�a�U�^�a�U�U�U�U�^�a�U�U�U�X�[�U�^�g�^�a�U�^�g�X�[�U�^�g�U�^�a�U�U�U�d�T�T�U\n" + 
					"�^�T�T�T�m�T�T�m�a�^�m�T�[�d�a�^�a�^�m�T�m�T�T�m�T�m�a�^�m�T�m�m�T�T�m�a�^�m�T�T�a\n" + 
					"���������������������X�T�a�U\n" + 
					"���������������������^�T�T�a");
			
			
		}

	}

	/**
	 * human player gets to choose their username
	 * all other players are assigned automatically generated names
	 */	

	public void createPlayers() {

		// numberOfPlayers = currentDeck.getNumPlayers(); 
		listOfPlayers = new ArrayList<Player>();

		int i = 0;

		System.out.println("Please enter your username: ");

		Scanner in = new Scanner (System.in);
		username = String.format("%-10s", in.next());

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



	public void dealCards() {

		int numCardsEach = currentDeck.getNumberOfCards() / numberOfPlayers;

		int i;
		for (i = 0; i < numberOfPlayers; i++) {

			ArrayList<Card> cardsForEachPlayer = new ArrayList<Card>(currentDeck.getDeck().subList(0, numCardsEach));
			String playerName = listOfPlayers.get(i).getName();
			listOfPlayers.get(i).receiveCards(cardsForEachPlayer);
			currentDeck.getDeck().removeAll(cardsForEachPlayer);

		}

		if (!currentDeck.getDeck().isEmpty()) { //if cards remaining in deck

			listOfPlayers.get(pickRandomPlayer()).receiveExtraCards(currentDeck.getDeck());

		}

		System.out.println("Dealing cards...");
		System.out.println();


	}



	private void logDeck(Deck d, boolean deckOutput)	{ //for printing to output log

		PrintWriter printer = null;

		try {
			try {
				FileWriter fw = new FileWriter(logFile, true);
				BufferedWriter bw = new BufferedWriter(fw);
				printer = new PrintWriter(bw);
				String deck = "";
				String deckDescriptor = "";
				String logSeparator = "----------------------------------------------------------------"+
						"-------------------------";
				{

					if (deckOutput == true) {
						deck = currentDeck.dString();
						deckDescriptor = "Shuffled deck\n";

					}

					else {
						fw = new FileWriter(logFile, false); //overwrite log contents if new game
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
	
	

	private void logDealtCards() {

		PrintWriter printer = null;

		try {
			try {
				FileWriter fw = new FileWriter(logFile, true);
				BufferedWriter bw = new BufferedWriter(fw);
				printer = new PrintWriter(bw);

				String logSeparator = "-------------------------------------------------------------"+
						"-------------------------";
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
	

		private void roundLog() {

			PrintWriter printer = null;

			try {
				try {
					FileWriter fw = new FileWriter(logFile, true);
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

			private void logGameWinner() {

				PrintWriter printer = null;

				try {
					try {
						FileWriter fw = new FileWriter(logFile, true);
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

	private int pickRandomPlayer() { //returns random index number

		int randomIndex = (int)Math.floor(Math.random() * numberOfPlayers);
		return randomIndex;
	}

}

