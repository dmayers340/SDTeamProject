package commandline;

public class Game {

	/**
	 *  instance variables
	 */

	private int numberOfPlayers;
	private Deck currentDeck;

	private int activePlayers;
	
	// array with all the player references


	/**
	 * constructor method;
	 * every new game shuffles the deck
	 * @param d (current deck)
	 */

	public Game (Deck d)
	{
		d.shuffleDeck();
		currentDeck = d;

		System.out.println();
		System.out.println("Current deck printed below:");
		System.out.println(currentDeck.dString());
		System.out.println();
		System.out.println();

		System.out.println("Starting a new round");

		// TEMPORARY
		Player player = new Player();
		numberOfPlayers = 3;
		activePlayers = numberOfPlayers;
		// d.dealCards(); <- this is a loop 
		// d = the name of the current deck, see above

		/**
		 * rounds continue until there is only 1 active player left
		 * the last active player is the winner 
		 */
		
		while (activePlayers > 1)

		{
			Round newRound = new Round(activePlayers);
			// newRound.compare();
			
		}

		
		/**
		 * method print winnerGame
		 */
		
		// code goes here
	}

}
