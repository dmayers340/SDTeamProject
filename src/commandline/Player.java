package commandline;
import java.util.ArrayList;

public class Player { 

	private String playerName;
	private ArrayList<Card> hand;
	private Card card;
	private int numberOfCardsEach;
	
	
	public Player(String pName) { 
		
		this.playerName = pName;
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
	
	public ArrayList<Card> getHand() {
		
		return hand;
		
	}
}
