package commandline;
import java.util.ArrayList;

public class Player { 

	private String playerName;
	private ArrayList<Card> hand;
	private Card card;
	
	
	public Player(String pName) { 
		
		this.playerName = pName;
	}


	public String toString() {

		return playerName;
	}

	public void addCardsToHand(Card c) {
		
		
	}
	
}
