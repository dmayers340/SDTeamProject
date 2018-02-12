package commandline;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents different players of the game
 * and stores information about the particular player object. 
 * It also contains methods that update player information as 
 * the game progresses.
 */

public class Player { 

	/**
	 * instance variables
	 */

	private String playerName;
	private ArrayList<Card> hand; // stores cards in hand
	private int roundWins; // number of won rounds
	private boolean human; 
	private boolean isInGame;

	private int numberOfCardsEach;

	/**
	 * Constructor method.
	 * Once a new player is created, they are always in game.
	 */
	public Player() 
	{ 
		isInGame = true;
		hand = new ArrayList<Card>();
	}

	/**
	 * Adds an array of new cards to the player's hand.
	 * @param cards = the cards to be added
	 */

	public void receiveCards(ArrayList<Card>cards) 
	{
				this.hand.addAll(cards);
	}



	/**
	 * Adds a single card to the player's hand
	 * @param c = the card to be added
	 */
	public void receiveCard(Card c) 
	{
		this.hand.add(c);
	}


	/**
	 * Removes top card from the hand
	 */
	public void removeCard ()

	{
		hand.remove(getTopCard());
	}


	/**
	 * The human player is able to choose the category through command line input. 
	 * The player must enter the category name as a String without any spelling mistakes.
	 * "Description" is not considered to be a valid category.
	 */
	public int chooseCategory ()

	{
		Card drawnCard = this.getTopCard();
		Scanner in = new Scanner (System.in);
		String input = "";

		int c = -1;
		int temp = -1;

		// loops until valid category is entered
		while (c<1) 
		{
			input = in.next();
			// checks if entered category is valid
			for (int i = 0; i < drawnCard.getCategories().size(); i++)
			{
				if (input.equalsIgnoreCase(drawnCard.getCategories().get(i)))
					temp = i;
			}

			if (temp<1) // if category name not found or "description"
			{
				System.out.println("You must enter a valid category name.");
				chooseCategory();
			}

			else 
			{
				c = temp;
			}
		}

		return c;

	}


	/**
	 * Used for auto-finding a category for the roundl.
	 * Searches player's top card and finds the category with the highest value.
	 * 
	 * @return the index of the category as it's stored in the Card object.
	 */
	public int findBestCategory () 

	{ 
		int curr; // current value
		int temp = 0; // temp highest value
		int index = 0; // index of the highest value

		for (int i = 1; i < this.getTopCard().getCategories().size(); i++)
		{
			// finds the category with the highest value
			curr = Integer.valueOf(this.getTopCard().getAttribute(i)); // looks horrendous

			if (curr>temp)
			{	
				temp=curr;
				index = i;
			}
		}

		return index;
	}


	/**
	 *  increments the number of rounds the player won
	 */
	public void addWin()
	{
		roundWins++;
	}


	/**
	 * Setter methods below
	 */

	public void setPlayerName (String name) 

	{
		this.playerName = name.toUpperCase();
	}


	/**
	 * @param numC = number of cards the player receives once cards are dealt
	 */
	public void setNumberOfCardsEach(int numC) {

		this.numberOfCardsEach = numC;
	}


	/**
	 * @param whether the player is still in game.
	 */
	public void setStatus (boolean inGame)
	{
		isInGame = inGame;
	}


	/**
	 * set player type 
	 */
	public void setHuman ()
	{
		human = true;
	}




	/**
	 * Getter methods below
	 */


	/**
	 * @return hand
	 */
	public ArrayList<Card> getHand() 
	{
		return hand;
	}


	/**
	 * Returns the Player object as a String
	 */
	public String toString() 
	{
		return playerName + hand;
	}


	/**
	 * @return top card in hand
	 */
	public Card getTopCard() 
	{	
		return hand.get(0);

	}

	/**
	 * @return cards in hand as a String
	 */
	public String handToString()

	{
		String h = System.getProperty("line.separator") + this.getName() + "'s cards: " + System.getProperty("line.separator");

		if (this.isInGame)

		{
			h = h + this.getTopCard().cString() + System.getProperty("line.separator");

			for (int i = 0; i<hand.size(); i++)

			{
				h = h + hand.get(i) + System.getProperty("line.separator");
			}
		}


		else 
		{
			h = h + this.getName() + " has no cards" + System.getProperty("line.separator");
		}

		return h;
	}


	/**
	 * @return player name
	 */
	public String getName()
	{
		return playerName;
	}


	/**
	 * @return the number of rounds the player has won
	 */
	public int getRoundWins()
	{
		return roundWins;
	}


	/**
	 * @return whether the user is human or not
	 */
	public boolean isHuman ()
	{
		return human;
	}


	/**
	 * @return whether the player is still in game.
	 */
	public boolean isInGame ()

	{
		return isInGame; 
	}


}