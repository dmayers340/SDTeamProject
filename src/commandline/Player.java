package commandline;
import java.util.ArrayList;
import java.util.Scanner;

public class Player { 

	private String playerName;
	private ArrayList<Card> hand;
	private boolean human; 
	private boolean isInGame;
	private int roundWins; // number of won rounds

	// what are these?
	private Card card;
	private int numberOfCardsEach;

	public Player(String pName) { 

		this.playerName = pName;
		isInGame = true;
	}

	public void setUsername (String username) 

	{
		this.playerName = username.toUpperCase();
	}

	public void setNumberOfCardsEach(int numC) {

		this.numberOfCardsEach = numC;
	}



	public String toString() {

		return playerName + hand;
	}

	public void receiveCards(ArrayList<Card>dealtCards) {

		this.hand = dealtCards; 
	}

	public void receiveExtraCards(ArrayList<Card>extraCards) {

		this.hand.addAll(extraCards);
	}

	public void receiveCard(Card c) 

	{
		this.hand.add(c);
	}

	/**
	 * removes top card from the hand
	 * removes the player if needed
	 */

	public void removeCard ()

	{
		hand.remove(getTopCard());
	}


	/**
	 * @return hand
	 */

	public ArrayList<Card> getHand() {

		return hand;

	}




	/**
	 * @return top card in hand
	 */

	public Card getTopCard() 

	{	
		return hand.get(0);

	}

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
			h = h + this.getName() + "has no cards" + System.getProperty("line.separator");
		}

		return h;
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
	 * searches active user's top card 
	 * finds the category with the highest value
	 * sets it to c
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
	 * @return player name
	 * need this for the interface
	 */

	public String getName()

	{
		return playerName;
	}


	/**
	 * set player type 
	 */

	public void setHuman ()

	{
		human = true;
	}

	// increments the number of rounds the player won
	public void addWin()
	{
		roundWins++;
	}

	// returns the number of rounds the player has won
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


	public boolean isInGame ()

	{
		return isInGame; 
	}

	public void setStatus (boolean inGame)

	{
		isInGame = inGame;
	}
}