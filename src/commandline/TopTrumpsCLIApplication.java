package commandline;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {

	/**
	 * class constants
	 */
	private static final int maxAttributes = 6;

	/**
	 * instance variables
	 */
	private static String FILE_NAME = "StarCitizenDeck.txt"; 
	private static int numberOfGames = 0;
	private static Deck newDeck;

	/**
	 * This main method is called by TopTrumps.java when the user specifies that they want to run in
	 * command line mode. The contents of args[0] is whether we should write game logs to a file.
	 * @param args
	 */
	public static void main(String[] args) {

		boolean writeGameLogsToFile = false; // Should we write game logs to file?
		if (args[0].equalsIgnoreCase("true")) writeGameLogsToFile=true; // Command line selection

		// State
		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application

		// Loop until the user wants to exit the game
		while (!userWantsToQuit) 

		{
			optionMenu();

			// ----------------------------------------------------
			// Add your game logic here based on the requirements
			// ----------------------------------------------------

			// userWantsToQuit=true; // use this when the user wants to exit the game

		}

	}

	/**
	 * Not a final version, wrote this for testing purposes
	 * 
	 * Asks for user input
	 * S - view past stats
	 * G - start a new game
	 * Q(uit) - exit
	 */
	// play game or view stats?
	private static void optionMenu() 
	{
		System.out.println();
		System.out.println("Options: ");
		System.out.println("G - play a new game ");
		System.out.println("S - view past game statistics ");
		System.out.println("Q - exit the application ");
		System.out.println();

		String choice = getInput();

		// if letter S was entered - nothing happens
		if (choice.charAt(0) == 'S')
		{
			System.out.println("No statistics to display ");
			System.out.println();
		}


		// if letter G was entered - creates a new game object
		// passes the current deck (newDeck) as a variable
		else if (choice.charAt(0) == 'G')
		{
			String readDeck =" ";
			if (numberOfGames < 0)
			{

				// not sure if needed... 
				System.out.println("Read from a new deck? (Y/N)"); 
				readDeck = getInput();

			}

			// reads from new deck at first round or if asked
			if (readDeck.charAt(0) == 'Y' || numberOfGames == 0)
			{	
				System.out.println();
				readIn();
			}

			// play time! 
			Game newGame = new Game(newDeck);
			numberOfGames++;
			newGame.createPlayers();
		}

		// if Q or QUIT was entered
		// not sure how to use the boolean :D 
		else if (choice.charAt(0)=='Q')
		{
			System.out.println ("You exited the program");
			System.out.println();
			System.exit(0);
		}

		// any other  input
		else 
		{
			System.out.println("Please enter valid input");
			System.out.println();
			return;
		}

	}


	/**
	 * reads command line input
	 * @return String
	 */
	private static String getInput()
	{
		Scanner in = new Scanner (System.in);
		String input = " ";
		
		input = in.next();
		return input;
	}


	/**
	 * reads from the .txt file
	 * adds categories to the deck
	 * adds all cards to the deck
	 */
	private static void readIn()
	{
		newDeck = new Deck();
		FileReader reader;
		try 
		{
			String [] split = new String [maxAttributes];
			reader = new FileReader(FILE_NAME);

			Scanner in = new Scanner (reader);
			String line = in.nextLine();

			// splits the first line (categories)
			split = line.split(" ");

			// sets category names
			for (int i = 0; i < maxAttributes; i++)	
			{
				newDeck.addCategory(split[i]); 
			}

			// reads remaining lines
			// adds cards to the deck
			while (in.hasNextLine())
			{
				line = in.nextLine();
				split = line.split(" ");

				newDeck.addCard(split);
			}

		} 

		catch (FileNotFoundException e) 
		{
			System.out.println("File not Found");
			System.out.println("Check if file name is correct");
			System.out.println();
		}

	} 
	
	private static void howManyPlayers() {
		
		System.out.print("How many opponents would you like? Maximum is 4.");
		int numberOfPlayers = Integer.parseInt(getInput());
		newDeck.setNumPlayers(numberOfPlayers);
		
	}
	
	
	
}