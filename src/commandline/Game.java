package commandline;

import java.util.ArrayList;

public class Game {
	
	/**
	 *  instance variables
	 */
	
	private int numberOfPlayers;
	private Deck currentDeck; 
	private Player player;
	
	
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

	private void createPlayers() {
		
		numberOfPlayers = currentDeck.getNumPlayers();
		ArrayList <Player> listOfPlayers = new ArrayList<Player>(numberOfPlayers);
		
		int i;
		for (i = 0; i < numberOfPlayers; i++) {
			
			Player p = new Player("Player" + "" + (i+1));
			listOfPlayers.add(p);
		}
		
		
		
		
		
		
		
	
		}
		
		
	}
	
	

