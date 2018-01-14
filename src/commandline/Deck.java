package commandline; 
import java.util.ArrayList;

public class Deck {

	/**
	 * instance variables
	 */
	private ArrayList<String> categories;
	private ArrayList<Card> cardsInDeck;
	private int numberOfCards;

	/**
	 * constructor
	 */
	public Deck () 
	{
		cardsInDeck = new ArrayList<Card>(); // we have a new deck!
		categories = new ArrayList<String>();
	}


	/**
	 * category names are stored in an array list ('categories')
	 * @param String c = a category name
	 */

	public void addCategory (String c)	
	{
		categories.add(c); 
	}


	/**
	 * adds a new card to the deck
	 * @param String [] cAttributes 
	 */

	public void addCard(String [] cAttributes) 
	{
		cardsInDeck.add(new Card(cAttributes)); // 
	}


	/**
	 * shuffles current deck
	 * swaps each card with another card at a random index
	 */

	public void shuffleDeck()
	{
		// get length of the list
		int listLength = cardsInDeck.size();
		Card temp; 

		for (int i = 0; i < listLength; i++)

			// shuffle

		{
			// generates a random integer
			int random = (int)Math.random() * (listLength);

			// swaps cards at indexes 'i' and 'random'
			temp = cardsInDeck.get(i);
			cardsInDeck.set(i, cardsInDeck.get(random));
			cardsInDeck.set(random, temp);
		}
	}


	/**
	 * accessor method 
	 * @returns category names as an array list
	 * could also return a long String....
	 */

	private ArrayList<String> getCategories ()
	{
		return categories;
	}

	/**
	 * accessor method
	 * @return current deck
	 * will need this after each shuffle!
	 */

	public ArrayList<Card> getDeck()
	{
		return cardsInDeck;
	}

}