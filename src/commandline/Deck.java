package commandline; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
	private static final int maxAttributes = 6;

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

	public void setCategories (String line)	
	{
		String [] split = new String [maxAttributes];
		split = line.split(" ");
		Collections.addAll(categories, split);
	}
	

	/**
	 * adds a new card to the deck
	 * @param String line
	 */

	public void addCard(String line) 
	{
	
		String [] split = new String [maxAttributes];
		split = line.split(" ");
		
		ArrayList<String> card = new ArrayList<String>();
		Collections.addAll(card, split);
		
		Card newCard = new Card(categories, card);
		cardsInDeck.add(newCard);
		numberOfCards++; 
	}
	

	public int getNumberOfCards() {

		return numberOfCards;
	}

	public Card getCardAt(int i) {

		return cardsInDeck.get(i);
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
			int random = (int)Math.floor(Math.random() * listLength);

			// swaps cards at indexes 'i' and 'random'
			temp = cardsInDeck.get(i);
			cardsInDeck.set(i, cardsInDeck.get(random));
			cardsInDeck.set(random,temp);
		}
	}


	/**
	 * accessor method 
	 * @returns category names as an array list
	 * could also return a long String....
	 */

	public ArrayList<String> getCategories ()
	{
		return categories;
	}


	/**
	 * toString
	 * @return deck as a string 
	 */

	public String dString () 
	{
		String dString = "";
		Card temp;

		dString = dString + String.format( "%s ", cString());

		for (int i = 0; i < numberOfCards; i++)
		{
			temp = cardsInDeck.get(i);
			dString = dString + String.format( "\n%s ", temp.toString());
		}

		return dString;
	}


	/**
	 * toString method
	 * @return categories in one String 
	 */

	public String cString ()
	{
		String cString = "";

		// print categories
		for (int i = 0; i < categories.size(); i++)
		{
			cString = cString + String.format( "%14s ", categories.get(i));
		}

		return cString.toUpperCase(); 	
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