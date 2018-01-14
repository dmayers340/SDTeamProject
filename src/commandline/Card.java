package commandline;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Card implements Comparable<Card>{

	/**
	 * the only instance variable
	 * all the attributes are stored in one array
	 */

	String [] cardAttributes;


	/**
	 * constructor
	 * @param cA = array containing attributes
	 */

	public Card(String [] cA)

	{ 
		cardAttributes = cA;
		System.out.println(toString());
	}


	/**
	 * getter method
	 * @param index of the attribute
	 * @return the value 
	 */

	public String getAttribute(int index) 

	{
		return cardAttributes[index];
	}


	/**
	 * write to String
	 * self explanatory
	 */

	public String toString () 

	{
		String cardString = "";

		for (int i = 0; i<cardAttributes.length; i++)
		{
			cardString = cardString + String.format ("%s ", cardAttributes[i]); 
		}

		cardString = String.format ("%s ", cardString);
		return cardString;
	}

	/**
	 * need to write
	 */

	public int compareTo(Card o) 

	{

		// add code here
		return 0;
	}

}
