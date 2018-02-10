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
				newGame = new Game(db, writeGameLogsToFile);
				newGame.setOnline(false);
				newGame.setNumberOfPlayers(getNumberOfAIPlayers()+1);

				// user enters username
				System.out.println("Please pick a username: ");	
				newGame.setUsername(getInput());
				newGame.initialiseGame();

				while (newGame.getStatus() == false)
				{
					runGame(); 
				}

				winner = newGame.getWinner();
				displayWinner();
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
	private static void runGame()
	{
		newGame.chooseActivePlayer();

		if (newGame.getActivePlayer().isHuman()==true)
		{

			chooseCategory();
			newGame.setCurrentCategory(category);
		}

		else
		{
			newGame.findBestCategory();
		}

		newGame.startRound();

	}


	/**
	 * The human player is able to choose the category through command line input. 
	 * The player must enter the category name as a String without any spelling mistakes.
	 * "Description" is not considered to be a valid category.
	 */

	private static void chooseCategory ()

	{
		System.out.println("It's your turn to choose! Please enter the name of the category.");

		String s = ("\n The human player " + newGame.getActivePlayer().getName() + " chose the category for the current round");

		// roundLog.append(s);
		// 	roundLog.append("\n");

		// enter category name
		String categoryString = getInput();

		int c;
		int temp = -1;

		// checks if entered category is valid
		Card drawnCard = newGame.getActivePlayer().getTopCard();
		for (int i = 0; i < drawnCard.getCategories().size(); i++)
		{
			newGame.getActivePlayer().getTopCard();
			if (categoryString.equalsIgnoreCase(drawnCard.getCategories().get(i)))
				temp = i;
		}

		if (temp<1) // if category name not found or "description"
		{
			System.out.println("You must enter a valid category name.");
			chooseCategory();
		}

		else 
		{
			category = temp;
		}

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

	private static void displayWinner ()
	{

		System.out.println("The winner of the game is " + winner.getName());

		if (winner.isHuman()==true) // human player always has index 0
		{
			System.out.print ("\n CONGRATULATIONS! \n You just won the game!");
		}

	}

}
