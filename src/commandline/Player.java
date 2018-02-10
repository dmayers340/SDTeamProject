package commandline;
import java.util.ArrayList;

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