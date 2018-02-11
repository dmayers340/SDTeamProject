package commandline;

import java.util.Scanner;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {

	/**
	 * instance variables
	 */
	private static DatabaseConnection db = new DatabaseConnection();

	private static Player winner;
	private static Game newGame;
	private static int category;

	private static final String SEPARATOR = "------------------------------------------------------------------------------------------------";


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

			// if letter S was entered - nothing happens
			if (choice.charAt(0) == 'S')
			{
				System.out.println(getStats());
			}

			// starts a new game
			else if (choice.charAt(0) == 'G')
			{  
				DatabaseConnection db = new DatabaseConnection();
				newGame = new Game(db);

				newGame.writeToLog(writeGameLogsToFile);
				newGame.setNumberOfPlayers(getNumberOfAIPlayers()+1);

				// user enters username
				System.out.println("Please pick a username: ");	
				newGame.setUsername(getInput());
				newGame.initialiseGame();

				while (newGame.getStatus() == false)
				{
					runRound(); 
				}

				displayGameWinner();
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

		}

		System.exit(0);
	}


	/**
	 * 
	 */
	private static void runRound()
	{
		newGame.chooseActivePlayer();
		Player activePlayer = newGame.getActivePlayer();

		// print to console
		System.out.println();
		System.out.println("STARTING ROUND NUMBER " + newGame.getRoundCount());
		System.out.println();

		// prints user's top card
		if (newGame.getPlayer(0).isInGame() == true)
		{
			System.out.println("Your card details are printed below:");
			System.out.println();
			System.out.println(newGame.getPlayer(0).getTopCard().cString());
			System.out.println(newGame.getPlayer(0).getTopCard().toString());
			System.out.println();
		}

		// if user out of cards
		else 	
		{
			System.out.println("You are currently out of cards");
			System.out.println();
		}

		// if it's users turn to choose category
		if (activePlayer.isHuman()==true)
		{
			// print to console
			System.out.println("It's your turn to choose the category! "
					+ "Please enter a category.");

			// ask for input
			int c = activePlayer.chooseCategory();
			newGame.setCurrentCategory(c);
		}

		// if it's AI's turn to choose category
		else
		{
			int c = activePlayer.findBestCategory();
			newGame.setCurrentCategory(c);

			// print to console
			System.out.println("The active player is " + activePlayer.getName());
			System.out.println(activePlayer.getName() + " is choosing the category...");
			System.out.println("The category for round " + newGame.getRoundCount() + 
					" is " + activePlayer.getTopCard().getCategories().get(c));
		}

		newGame.startRound();

		if (newGame.getPlayer(0).isInGame() == false)

		{
			System.out.println("You don't have any cards left!");
		}

		winner = newGame.getWinner();

		System.out.println();
		
		System.out.println(winner.getName() + " won this round!");
		System.out.println();
		
		System.out.println("The winning card was:");
		System.out.println();
		
		System.out.println(winner.getTopCard().cString());
		System.out.println(winner.getTopCard().toString());	
		System.out.println();
		
		System.out.println(SEPARATOR);

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
		stats.append("The hooman has won " + db.getHumanWin() + " times\n");
		stats.append("The average number of draws is " + db.getNumberOfDraws() + "\n");
		stats.append("The largest number of rounds played in a single game is " + db.getMaxRounds() + "\n");

		String statistics = stats.toString();
		return statistics;
	}


	/**
	 *  Called at the end of a game, displays the final winner of the game. 
	 *  If human player won the game, prints out a "congratulations" message.
	 */

	private static void displayGameWinner ()
	{

		System.out.println("The winner of the game is " + winner.getName());

		if (winner.isHuman()==true) // human player always has index 0
		{
			System.out.print ("\n CONGRATULATIONS! \n You just won the game!");
		}

	}

}
