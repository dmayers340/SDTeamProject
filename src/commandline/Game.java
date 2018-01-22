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

	private int numberOfPlayers; //we should assume there will always be 4 AI players
	private Deck currentDeck;
	private Round newRound;

	private ArrayList <Player> listOfPlayers;
	private int remainingPlayers;
	private int activePlayerIndex;
	private Player activePlayer;
	private final String logFile = "toptrumps.log";


	/**
	 * constructor method;
	 * every new game shuffles the deck
	 * @param d (current deck)
	 */

	public Game (Deck d)
	{	boolean deckOutputToLog = false;
		logDeck(d,deckOutputToLog);
		d.shuffleDeck();
		deckOutputToLog = true;
		currentDeck = d;
		logDeck(currentDeck, deckOutputToLog); //prints shuffled deck to log file
		
		int p = TopTrumpsCLIApplication.howManyPlayers();
		numberOfPlayers = p+1;
		
		// each game starts with all players active
		remainingPlayers = numberOfPlayers;

		// prints deck 
		System.out.println();
		System.out.println("Current deck printed below:");
		System.out.println(currentDeck.dString());

		// creates players
		System.out.println();
		System.out.println("Creating players... ");
		createPlayers();
		System.out.println("Done!");
		remainingPlayers = numberOfPlayers;

		System.out.println();
		System.out.println("Dealing cards...");
		dealCards();
		System.out.println("Done!");

		/**
		 * rounds continue until there is only 1 active player left
		 * the last active player is the winner 
		 */

		while (remainingPlayers > 1)

		{
			setActivePlayer();

			System.out.println("Starting a new round...");

			newRound = new Round(listOfPlayers, activePlayer, currentDeck);
			logCardsInPlay(); //prints each player's top card to log

			newRound.playRound();

			updatePlayers();
		}

		showWinner();

	}

	public Deck getCurrentDeck()
	{
		return currentDeck;
	}

	/**
	 * removes players with no cards
	 */

	private void updatePlayers()

	{
		int i; 
		
		for (i = 0; i<remainingPlayers; i++)
		{
			if (!listOfPlayers.get(i).hasCards())
			{
				System.out.println(listOfPlayers.get(i).getName() + " is out of cards");
				listOfPlayers.remove(listOfPlayers.get(i));
				remainingPlayers--;
				i = -1;
				
				System.out.println("The players in the array list are:  ");

				for (int j = 0; j<listOfPlayers.size(); j++)
				{

					System.out.println(listOfPlayers.get(j).getName());

				}

				System.out.println();

			}
			
		}
	}



	/**
	 * selects next player
	 */

	private void setActivePlayer()

	{
		// if active player has not been set 
		if (activePlayer == null)	
		{

			activePlayerIndex = pickRandomPlayer();
			activePlayer = listOfPlayers.get(activePlayerIndex);

		}

		// NEEDS EDITING
		// if the current active player won the previous round
		else if (activePlayer.equals(newRound.getWinner()))
			
			// .equals vs == ? 
			
		{
			for (int index = 0; index<listOfPlayers.size(); index++)

			{
				if (listOfPlayers.get(index)==activePlayer) 
					activePlayerIndex = index;
			}

			activePlayer = listOfPlayers.get(activePlayerIndex);

		}

		// if the current active player is on top of the array 
		else if (activePlayerIndex >= (listOfPlayers.size())-1)
		{
			activePlayerIndex = 0;
		}

		else activePlayerIndex++;

		activePlayer = listOfPlayers.get(activePlayerIndex);

	}


	/**
	 * method print winnerGame
	 */

	private void showWinner ()

	{
		System.out.println();
		System.out.println("The winner of the game is " + listOfPlayers.get(0).getName());
		System.out.println();
	}

	/**
	 * human player gets to choose their username
	 * all other players are assigned automatically generated names
	 */	

	public void createPlayers() {

		// numberOfPlayers = currentDeck.getNumPlayers(); 
		listOfPlayers = new ArrayList<Player>(numberOfPlayers);

		int i = 0;

		// creates the human player
		Player h = new Player(TopTrumpsCLIApplication.getUsername());
		h.setHuman();
		listOfPlayers.add(h);

		// create AI players
		for (i = 1; i < numberOfPlayers; i++) 

		{
			Player p = new Player("Player" + "" + (i+1));
			listOfPlayers.add(p);
		}

		//		System.out.print(listOfPlayers.toString());
	}



	public void dealCards() {

		int numCardsEach = currentDeck.getNumberOfCards() / numberOfPlayers;

		int i;
		for (i = 0; i < numberOfPlayers; i++) {

			System.out.println("Dealing once");
			ArrayList<Card> cardsForEachPlayer = new ArrayList<Card>(currentDeck.getDeck().subList(0, numCardsEach));
			String playerName = listOfPlayers.get(i).getName();
			logPlayerCards(cardsForEachPlayer, playerName);
			listOfPlayers.get(i).receiveCards(cardsForEachPlayer);
			currentDeck.getDeck().removeAll(cardsForEachPlayer);

		}

		if (!currentDeck.getDeck().isEmpty()) { //if cards remaining in deck

			listOfPlayers.get(pickRandomPlayer()).receiveExtraCards(currentDeck.getDeck());

		}
		
		
	
		for (Player p: listOfPlayers)
			
		{	
			System.out.println(p.getHand().toString());
		}


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
					}


					printer.println(logSeparator);	
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

	private void logPlayerCards(ArrayList<Card>eachPlayersCards, String pName) {
		
		PrintWriter printer = null;

		try {
			try {
				FileWriter fw = new FileWriter(logFile, true);
				BufferedWriter bw = new BufferedWriter(fw);
				printer = new PrintWriter(bw);

			{ 
				printer.println(pName + "'s cards");
				printer.println("");
				printer.println(eachPlayersCards.toString()); 
				printer.println("");
			}
			
			String logSeparator = "-------------------------------------------------------------"+
			"-------------------------";
			printer.println(logSeparator);
			
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
	
	private void logCardsInPlay() { //prints to log each players top cards in each round
		
		PrintWriter printer = null;

		try {
			try {
				FileWriter fw = new FileWriter(logFile, true);
				BufferedWriter bw = new BufferedWriter(fw);
				printer = new PrintWriter(bw);
				String logSeparator = "-----------------------------------------------------"+
						"-------------------------";
				printer.println("Round " + newRound.getRoundCount() + ". " + "Cards in play:-");
				printer.println(" ");
				{
					for (Player p: listOfPlayers) {
					
					
					printer.print(p.getName() + ":" + " ");
					printer.println(p.getTopCard().toString());

				}
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
	
	

	private int pickRandomPlayer() { //returns random index number

		int randomIndex = (int)Math.floor(Math.random() * numberOfPlayers);
		return randomIndex;
	}

}

