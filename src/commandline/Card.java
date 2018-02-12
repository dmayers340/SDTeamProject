package commandline;
import java.util.ArrayList;

/**
 * This class represents a single card object as it was read in from the deck.
 * This class also contains getter methods. 
 * Once read in, a card cannot be modified.
 */

public class Card {


	private static ArrayList<String> categories;  //e.g. Speed, Firepower etc
	private ArrayList<String> cardAttributes;     //numerical value of the attribute


	/**
	 * constructor
	 * @param c = categories
	 * @param cA = array containing attributes
	 */
	public Card(ArrayList<String> c, ArrayList<String> cA)

	{ 
		categories = c;
		cardAttributes = cA;
	}


	/**
	 * getter method
	 * @param index of the attribute
	 * @return the value 
	 */
	public String getAttribute(int index) 

	{
		return cardAttributes.get(index);
	}


	/**
	 * write to String
	 */
	public String toString () 

	{
		String cardString = "";

		for (int i = 0; i<cardAttributes.size(); i++)
		{
			cardString = cardString + String.format ("%-15s", cardAttributes.get(i)); 
		}

		cardString = String.format ("%s ", cardString);
		return cardString;
	}


	/**
	 * @return categories;
	 */
	public ArrayList<String> getCategories ()
	{
		return categories;
	}


	/**
	 * @return card's categories as a String
	 */
	public String cString ()
	{
		String cString = "";

		// print categories
		for (int i = 0; i < categories.size(); i++)
		{
			cString = cString + String.format( "%-15s", categories.get(i));
		}

		return cString.toUpperCase(); 	
	}


	/**
	 * @return the card's values
	 */
	public ArrayList<String> getCard () 

	{
		return cardAttributes;
	}

}
