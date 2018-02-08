package commandline;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {

	/**
	 * instance variables
	 */
	private static String FILE_NAME = "StarCitizenDeck.txt"; // name of deck file
	private static int numberOfGames = 0; // counter
	private static Deck currentDeck; // current deck
	private static DatabaseConnection db = new DatabaseConnection();

	
	/**
	 * This main method is called by TopTrumps.java when the user specifies that they want to run in
	 * command line mode. The contents of args[0] is whether we should write game logs to a file.
	 * Contains the command line interface for choosing what the user wants to do next.
	 * 
	 * S - view statistics
	 * G - start a new game
	 * Q - exit
	 * 
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
			System.out.println();
			System.out.println("Options: ");
			System.out.println("G - play a new game ");
			System.out.println("S - view past game statistics ");
			System.out.println("Q - exit the application ");
			System.out.println();
			
			String choice = getInput();

			// if letter S was entered 
			if (choice.charAt(0) == 'S')
			{
				System.out.println(getStats());
			}

			// reads the deck from a .txt file and starts a new game
			else if (choice.charAt(0) == 'G')
			{
				readIn();  
				DatabaseConnection db = new DatabaseConnection();
				Game newGame = new Game(currentDeck, db);
				newGame.setNumberOfPlayers(getNumberOfAIPlayers()+1);
				
				// user enters username
				System.out.println("Please pick a username: ");	
				newGame.setUsername(getInput());
				
				newGame.playGame();
				numberOfGames++;
			}
			
			// if Q or QUIT was entered
			else if (choice.charAt(0)=='Q')
			{
				userWantsToQuit = true;
			}

			// any other  input
			else 
			{
				System.out.println("Please enter valid input");
				System.out.println();
			}
			
			setNumberOfGames(getNumberOfGames() + 1);

		}

		System.exit(0);
	}

	
	/**
	 * Reads command line input. 
	 * @return user input as a String
	 */
	private static String getInput()
	{
		Scanner in = new Scanner (System.in);
		String input = " ";

		input = in.next();
		return input;
	}


	/**
	 * Reads deck contents from a file.
	 * Sets categories for the new deck (contained in the first line of the file).
	 * Add cards to the deck (each card is a new line).
	 * Uses FILE_NAME, which is stored as a class constant.
	 */
	private static void readIn()
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
	 * This method asks the user to enter the number of AI players.
	 * Reads command line input by calling this.getInput() method.
	 * The user must enter an integer that is between 1 and 4,
	 * otherwise the method will throw an error message.
	 * 
	 * @return the selected number of AI players
	 */
	public static int getNumberOfAIPlayers() { 

		System.out.println("How many opponents would you like? Maximum is 4.");
		String response; 
		int players = 0; 

		// attempts to get valid input from the user
		while (players==0)
		{
			try 
			{
				response = getInput();
				players = Integer.parseInt(response);

				if (players > 4 || players <0) 
				{
					System.err.println("Number of opponents must be between 1 and 4!");
					players = 0;
				}
			}

			catch (NumberFormatException notANumber)

			{
				System.err.println("Please enter a valid number");
			}
		}

		return players;
	}
	
	/**
	 * This method gets the persistent game statistics from the database
	 * by calling the relevant methods in the DatabaseConnection.java class.
	 * 
	 * As per assignment specification, this includes:
	 * - Number of games player overall
	 * - How many times the computer has won
	 * - How many times the human has won
	 * - The average number of draws
	 * - The largest number of rounds played in a single game
	 * 
	 * 	 @return the above as a nicely formatted String. 
	 */
	private static String getStats()
	{
		StringBuilder stats = new StringBuilder("");
		
		// ASCII art :) 
		stats.append(" \n");
		stats.append("   _____ _______    _______ _____  _____ _______ _____ _____  _____ \n");
		stats.append(" / ____|__   __|/\\|__   __|_   _|/ ____|__   __|_   _/ ____|/ ____|\n");
		stats.append(" | (___    | |  /  \\  | |    | | | (___    | |    | || |    | (___  \n");
		stats.append("  \\___ \\   | | / /\\ \\ | |    | |  \\___ \\   | |    | || |     \\___ \\ \n");
		stats.append("  ____) |  | |/ ____ \\| |   _| |_ ____) |  | |   _| || |____ ____) |\n");
		stats.append(" |_____/   |_/_/    \\_|_|  |_____|_____/   |_|  |_____\\_____|_____/ \n"); 
		stats.append(" \n");
		
		stats.append("Number of games played overall is " + db.getNumberOfGames() + "\n");
		stats.append("The computer has won " + db.getComputerWin() + " times\n");
		stats.append("The humen has won " + db.getHumanWin() + " times\n");
		stats.append("The average number of draws is " + db.getNumberOfDraws() + "\n");
		stats.append("The largest number of rounds played in a single game is " + db.getMaxRounds() + "\n");
		
		String statistics = stats.toString();
		return statistics;
	}

	/**
	 * 
	 * @param numGames - 
	 */
	public static void setNumberOfGames(int numGames)
	{
		numberOfGames = numGames;
	}
	
	/**
	 * 
	 * @return the number of games played
	 */
	public static int getNumberOfGames() {
		return numberOfGames;
	}


}
