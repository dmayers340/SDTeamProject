package commandline;

import java.util.ArrayList;

public class Game {
	
	/**
	 *  instance variables
	 */
	
	private int numberOfPlayers = 4; //hard-coded for testing
	private Deck currentDeck; 
	private ArrayList <Player> listOfPlayers;
	
	
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

	public void createPlayers() {
		
//		numberOfPlayers = currentDeck.getNumPlayers(); //commented out while testing
		listOfPlayers = new ArrayList<Player>(numberOfPlayers);
		
		int i;
		for (i = 0; i < numberOfPlayers; i++) {
			
			Player p = new Player("Player" + "" + (i+1));
			listOfPlayers.add(p);
		}
	
//		System.out.print(listOfPlayers.toString());
	}



	public void dealCards() {

		int numCardsEach = currentDeck.getNumberOfCards() / numberOfPlayers;

		int i;
		for (i = 0; i < numberOfPlayers; i++) {

			ArrayList<Card> cardsForEachPlayer = new ArrayList<Card>(currentDeck.getDeck().subList(0, numCardsEach));
			listOfPlayers.get(i).receiveCards(cardsForEachPlayer);
			currentDeck.getDeck().removeAll(cardsForEachPlayer);

		}

		if (!currentDeck.getDeck().isEmpty()) { //if cards remaining in deck

			listOfPlayers.get(pickRandomPlayer()).receiveExtraCards(currentDeck.getDeck());
		
	}
	 System.out.println(listOfPlayers.get(0).getHand().toString());
	 System.out.println(listOfPlayers.get(1).getHand().toString());
	 System.out.println(listOfPlayers.get(2).getHand().toString());
	 System.out.println(listOfPlayers.get(3).getHand().toString());
	 
	
	}


	public int pickRandomPlayer() { //returns random index number

		int randomIndex = (int)Math.floor(Math.random() * numberOfPlayers);
		return randomIndex;
	}

}




	

