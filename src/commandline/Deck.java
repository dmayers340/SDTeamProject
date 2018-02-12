package commandline; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Represents a deck of cards. 
 * Any methods for modifying the deck (adding new cards, shuffling)
 * are also stored in this class.
 */

public class Deck {

	/**
	 * instance variables
	 */
	private ArrayList<String> categories;
	private ArrayList<Card> cardsInDeck;
	private int numberOfCards;

	
	/**
	 * class constants
	 */
	private static final int MAX_ATTRIBUTES = 6; // max No. of attributes per card

	
	/**
	 * Constructor
	 */
	public Deck () 
	{
		cardsInDeck = new ArrayList<Card>(); // we have a new deck!
		categories = new ArrayList<String>();
	}


	/**
	 * Category names are stored in an array list ('categories')
	 * @param String c = a category name
	 */
	public void setCategories (String line)	
	{
		String [] split = new String [MAX_ATTRIBUTES];
		split = line.split(" ");
		Collections.addAll(categories, split);
	}


	/**
	 * Adds a new card to the deck
	 * @param a String containing the card's attributes
	 */
	public void addCard(String line) 
	{

		String [] split = new String [MAX_ATTRIBUTES];
		split = line.split(" ");

		ArrayList<String> card = new ArrayList<String>();
		Collections.addAll(card, split);

		Card newCard = new Card(categories, card);
		cardsInDeck.add(newCard);
		numberOfCards++; 
	}
	
	/**
	 * Shuffles current deck
	 * Swaps each card with another card at a random index
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
			int random = (int)Math.floor(Math.random() * listLength);

			// swaps cards at indexes 'i' and 'random'
			temp = cardsInDeck.get(i);
			cardsInDeck.set(i, cardsInDeck.get(random));
			cardsInDeck.set(random,temp);
		}
	}
	
	

	/**
	 * Getter methods below
	 */
	
	
	
	/**
	 * @return deck size
	 */
	public int getNumberOfCards() {

		return numberOfCards;
	}
	

	/**
	 * Returns a card at a particular index.
	 * @param card index
	 * @return a Card object
	 */
	public Card getCardAt(int i) {

		return cardsInDeck.get(i);
	}


	/**
	 * @return category names as an array list
	 */
	public ArrayList<String> getCategories ()
	{
		return categories;
	}


	/**
	 * @return deck as a string 
	 */
	public String dString () 
	{
		String dString = "";
		Card temp;

		dString = dString + String.format( "%s ", cString());

		// iterates through each of the cards
		for (int i = 0; i < numberOfCards; i++)
		{
			temp = cardsInDeck.get(i);
			dString = dString + System.getProperty("line.separator") + temp.toString();
		}

		return dString;
	}


	/**
	 * @return categories in one String 
	 */
	public String cString ()
	{
		String cString = "";

		// print categories
		for (int i = 0; i < categories.size(); i++)
		{
			cString = cString + String.format( "%-14s ", categories.get(i));
		}

		return cString.toUpperCase(); 	
	}


	/**
	 * @return current deck
	 */
	public ArrayList<Card> getDeck()
	{
		return cardsInDeck;
	}


}