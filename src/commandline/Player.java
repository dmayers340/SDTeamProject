package commandline;
import java.util.ArrayList;

public class Player { 

	private String playerName;
	private ArrayList<Card> hand;
	private Card card;
	private int numberOfCardsEach;
	private boolean human; 
	private boolean hasCards;

	public Player(String pName) { 

		this.playerName = pName;
		hasCards = true;
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
	 */
	
	public void removeCard ()
	
	{
		this.hand.remove(getTopCard());
		System.out.println(this.getName() + " had a card removed from their deck");
		System.out.println("They now have " + hand.size() + " cards in their deck. ");
		System.out.println();
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
	

	/**
	 * checks if the user has cards
	 * @return true / false
	 */
	
	
	public boolean hasCards ()

	{
		if (hand.size()<1 || hand == null)
			hasCards = false;
		
		return hasCards;
	}
	
}