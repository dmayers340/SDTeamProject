package commandline;

public class Game {
	
	/**
	 *  instance variables
	 */
	
	private int numberOfPlayers;
	private Deck currentDeck; 
	
	
	/**
	 * constructor method;
	 * every new game shuffles the deck
	 * @param d (current deck)
	 */
	
	public Game (Deck d)
	{
		d.shuffleDeck();
		currentDeck = d;
	}

}
