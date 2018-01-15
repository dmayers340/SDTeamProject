package commandline;

public class Game {
	
	/**
	 *  instance variables
	 */
	
	private int numberOfPlayers;
	private Deck currentDeck;
	
	private int activePlayers;
	
	
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
		
	}

}
