package commandline;
import java.util.ArrayList;

public class Player { 

	private String playerName;
	private ArrayList<Card> hand;
	
	
	public Player(String pName) { //can't create a player without assigning them a name
		
		this.playerName = pName;
	}
}

	