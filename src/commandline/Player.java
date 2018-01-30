package commandline;
import java.util.ArrayList;

public class Player { 

	private String playerName;
	private static ArrayList<Card> hand;
	private Card card;
	private int numberOfCardsEach;
	private boolean human; 
	private boolean isInGame;

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

	public static Card getTopCard() 

	{	
		return hand.get(0);

	}

	public String handToString()

	{
		String h = String.format("\n %s \n", this.getName() + "'s cards:\n  ");
		
		if (this.isInGame)
			
		{
			h = String.format("%s %s \n", h, this.getTopCard().cString());
			
			for (int i = 0; i<hand.size(); i++)

			{
					h = String.format("%s %s \n", h, hand.get(i));
			}
		}
		
	
		else 
		{
			h = String.format("%s %s \n", h, (this.getName() + " has no cards"));
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