package commandline;
import java.util.ArrayList;

public class Card {

	/**
	 * the only instance variable
	 * all the attributes are stored in one array
	 */

	private ArrayList<String> cardAttributes;
	private static ArrayList<String> categories;

	/**
	 * constructor
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
	 * self explanatory
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
	 * writes categories to String
	 * @return
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
	 * return array 
	 */
	
	public ArrayList<String> getCard () 
	
	{
		return cardAttributes;
	}

}
