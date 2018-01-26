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
	private int remainingPlayers; // players still in game
	private Deck currentDeck;
	private Round newRound;
	private Player activePlayer;
	private String username;
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

		boolean deckOutputToLog = false;
		logDeck(d,deckOutputToLog);

		d.shuffleDeck();
		deckOutputToLog = true;
		currentDeck = d;
		logDeck(currentDeck, deckOutputToLog); //prints shuffled deck to log file

		int p = TopTrumpsCLIApplication.howManyPlayers(); // user picks number of players
		numberOfPlayers = p+1; // AI players + human player
		remainingPlayers = p+1; // starts with all players still in game

		/**
		 * for testing 
		System.out.println();
		System.out.println("Current deck printed below:");
		System.out.println(currentDeck.dString());
		 */

		createPlayers();
		dealCards();

		/**
		 * rounds continue until there is only 1 player left
		 * the last remaining player is the winner 
		 */

		while (remainingPlayers > 1)

		{
			setActivePlayer(); // set deciding player 
			newRound = new Round(listOfPlayers, activePlayer);

			logCardsInPlay(); 
			//prints each player's top card to log
			newRound.playRound();

			String log = newRound.getRoundLog(); 
			System.out.println(log);

			System.out.println("================================================");

			updatePlayers(); // updates number of remaining players
		}

		newRound.getWinner();
		showWinner();

	}


	public Deck getCurrentDeck()
	{
		return currentDeck;
	}


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
		}

		System.out.println("The winner of the game is " + gameWinner.getName());

		if (gameWinner == listOfPlayers.get(0))
		{
			System.out.print ("\n" + 
					"╔═══╗─────────────╔╗───╔╗───╔╗\n" + 
					"║╔═╗║────────────╔╝╚╗──║║──╔╝╚╗\n" + 
					"║║─╚╬══╦═╗╔══╦═╦═╩╗╔╬╗╔╣║╔═╩╗╔╬╦══╦═╗╔══╗\n" + 
					"║║─╔╣╔╗║╔╗╣╔╗║╔╣╔╗║║║║║║║║╔╗║║╠╣╔╗║╔╗╣══╣\n" + 
					"║╚═╝║╚╝║║║║╚╝║║║╔╗║╚╣╚╝║╚╣╔╗║╚╣║╚╝║║║╠══║\n" + 
					"╚═══╩══╩╝╚╩═╗╠╝╚╝╚╩═╩══╩═╩╝╚╩═╩╩══╩╝╚╩══╝\n" + 
					"──────────╔═╝║\n" + 
					"──────────╚══╝");
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
		username = in.next();

		// creates the human player
		Player h = new Player(username);
		h.setHuman();
		listOfPlayers.add(h);

		// create AI players
		for (i = 1; i < numberOfPlayers; i++) 

		{
			Player p = new Player("Player" + "" + i);
			listOfPlayers.add(p);
		}

	}



	public void dealCards() {

		int numCardsEach = currentDeck.getNumberOfCards() / numberOfPlayers;

		int i;
		for (i = 0; i < numberOfPlayers; i++) {

			ArrayList<Card> cardsForEachPlayer = new ArrayList<Card>(currentDeck.getDeck().subList(0, numCardsEach));
			String playerName = listOfPlayers.get(i).getName();
			logPlayerCards(cardsForEachPlayer, playerName);
			listOfPlayers.get(i).receiveCards(cardsForEachPlayer);
			currentDeck.getDeck().removeAll(cardsForEachPlayer);

		}

		if (!currentDeck.getDeck().isEmpty()) { //if cards remaining in deck

			listOfPlayers.get(pickRandomPlayer()).receiveExtraCards(currentDeck.getDeck());

		}
		
		System.out.println("Dealing cards...");
		System.out.println();

		for (Player p: listOfPlayers)

		{	
			System.out.println(p.handToString());
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

	private void logCommunalPile(String cP) {

		PrintWriter printer = null;

		try {
			try {
				FileWriter fw = new FileWriter(logFile, true);
				BufferedWriter bw = new BufferedWriter(fw);
				printer = new PrintWriter(bw);

				{ 
					printer.println("Communal pile");
					printer.println("");
					printer.println(cP); 
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
						printer.println(p.getHand());

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

