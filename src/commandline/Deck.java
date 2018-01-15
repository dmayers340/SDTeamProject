package commandline; 
import java.util.ArrayList;

public class Deck {

	/**
	 * instance variables
	 */
	private ArrayList<String> categories;
	private ArrayList<Card> cardsInDeck;
	private int numberOfCards;
	private int numPlayers;

	/**
	 * constructor
	 */
	public Deck () 
	{
		cardsInDeck = new ArrayList<Card>(); // we have a new deck!
		categories = new ArrayList<String>();
	}


	public void setNumPlayers(int nP) {
		
		this.numPlayers = nP;
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
		cardsInDeck.add(new Card(cAttributes));
		numberOfCards++; 
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
	
	private String cString ()
	{
		String cString = "";
		
		// print categories
		for (int i = 0; i < categories.size(); i++)
		{
			cString = cString + String.format( "%s ", categories.get(i));
		}
		
		return cString; 	
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