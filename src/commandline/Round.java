package commandline;

import java.util.ArrayList;
import java.util.Scanner;

public class Round {

	/**
	 * instance variables
	 */

	private Player activePlayer;
	private ArrayList<Player> players;
	public static String cate;

	private static int c; // category
	private Player winner;

	private static int roundCount;
	private int drawCount;
	private static int [] playerRoundWins = new int [Game.numberOfPlayers];

	public static boolean draw = false; // 1st round starts with no draw
	public static ArrayList<Card> communalPile = new ArrayList<Card>(); 

	private StringBuilder roundLog = new StringBuilder();
	private String logSeparator = "-------------------------------------------------------------------------------------------------------";


	/**
	 * constructor method 
	 */

	public Round (ArrayList <Player> p, Player ap, Integer cat)

	{
		addRound();
		roundLog.append(logSeparator);
		String roundLog1 = String.format("%s%d", "ROUND ", roundCount); // sets top line for round log
		roundLog.append("\n" + roundLog1 + "\n");
		System.out.println();

		players = p;  
		activePlayer = ap; 
		c = cat;
		
		// remove later
		String cate = String.format("%s%d%s%s", "The category for round ", roundCount," is ", 
				activePlayer.getTopCard().getCategories().get(c).toUpperCase());
		System.out.println(cat);
		System.out.println("+ + + ");
		
		// wtf
		roundLog.append("\n" + cat + "\n");
	}


	/**
	 * game sequence
	 */

	public void playRound() 

	{	
		System.out.println("ROUND NUMBER " + (roundCount)); 
		String a = "The active player is " + activePlayer.getName().toUpperCase();

		roundLog.append("\n" + a + "\n");
		System.out.println(a);
		System.out.println(" + + + ");
		System.out.print(getCommunalPile()); // prints contents of communal pile
		System.out.println(" + + + ");

		// print human player's current card
		if (players.get(0).isInGame())

		{	
			System.out.println("Your card details are printed below:");

			// print human player's current card
			System.out.println(players.get(0).getTopCard().cString());
			System.out.println(players.get(0).getTopCard().toString());
			System.out.println(" + + + ");
		}

		compareCards();

		setWinner();  

	} 


	/**
	*/

	public int getDrawCount() 
	{	
	return drawCount;
	}
	


	/**
	 *  sets winner
	 */

	public void setWinner() 

	{
		if (draw == false)

		{
			String w = "\nThe winner is " + winner.getName();
			roundLog.append("\n");
			roundLog.append(w);
			roundLog.append("\n");
			System.out.println(w);
			System.out.println("\nThe winning card for round " + roundCount + " was: ");
			System.out.println(winner.getTopCard().cString());
			System.out.println(winner.getTopCard().toString());

			distributeCards();
			communalPile = new ArrayList<Card>(); // resets communal pile
		}

		if (draw == true)

		{
			draw();
			drawCount++; // for database statistics
			winner = null; // no winner
		}

		System.out.println(logSeparator);
	}


	/**
	 * winner receives cards 
	 */

	private void distributeCards() 

	{
		if (communalPile.size()>0)

		{
			// from communal pile 
			winner.receiveExtraCards(communalPile);
			communalPile.removeAll(communalPile); // empties pile

			String winnerIs = winner.getName() + " took all cards from the communal pile";
			roundLog.append(winnerIs);
			roundLog.append(System.getProperty("line.separator"));
			roundLog.append(getCommunalPile());
			roundLog.append(System.getProperty("line.separator"));


		}

		// winner receives cards from other players
		for (int i = 0; i<players.size(); i++)
		{
			Player p = players.get(i); // for readability
			if (p.isInGame())
			{
				winner.receiveCard(p.getTopCard());
				p.removeCard();
			}	
		}
	}


	/**
	 * sets round winner
	 */

	public void compareCards()

	{
		int j = 0; // winner's index
		int w = 0; // highest value
		int t = 0; // temp score

		String cards = String.format("%s%d%s\n\n%s\n%-15s%s", 
				"Comparing cards for round ", roundCount, "...", 
				"\nThe cards in play are:\n", " ", activePlayer.getTopCard().cString());
		String values = String.format("%s", "\n" + "The competing values are printed below:\n");

		// finds the highest value
		for (int i = 0; i<players.size(); i++)

		{
			if (players.get(i).isInGame()) 

			{
				t = Integer.valueOf(players.get(i).getTopCard().getAttribute(c));

				cards = String.format("%s\n%-15s%s", cards, players.get(i).getName(),
						players.get(i).getTopCard().toString());
				values = String.format("%s\n%s%s%d", values, players.get(i).getName(), ": ", t);

				if (t>w)

				{	
					w = t; // stores highest value
					j = i; // stores player index
					winner = players.get(j); // sets winner 
					playerRoundWins[j]++; //increment index in array which keeps track of num of player wins
					draw = false;
				}

				else if (t==w)

				{
					draw = true;
				}
			}
		}
		roundLog.append("\n" + cards + "\n");
		roundLog.append(" ");
		roundLog.append(values);


	}


	/**
	 * if draw
	 */

	private void draw() 

	{
		// adds cards to communal pile 
		for (int i = 0; i < players.size(); i++)

		{
			if (players.get(i).isInGame())
			{	
				communalPile.add(players.get(i).getTopCard());
				players.get(i).removeCard();
			}


		}
		
		String d = "\nRound " + roundCount + " was a draw";
		System.out.println(d);
		
		roundLog.append(d);	
		roundLog.append("\nNew cards have been added to the communal pile!");
		roundLog.append("\n" + getCommunalPile() + "\n");

	}




	public String getCate() {
		activePlayer.getTopCard();
		String cate = Card.getCategories().get(c).toUpperCase();
		return cate;
	}



	/**
	 * returns round winner
	 */

	public Player getWinner()

	{
		return winner;
	}


	/**
	 * accessor method 
	 * @return the contents of the communal pile as String
	 * if empty, @return a message stating that
	 */

	public String getCommunalPile()

	{
		if (communalPile.size()>0)

		{
			String cPile = ("\nThere are " + communalPile.size() + 
					" cards in the communal pile: ");

			cPile = String.format("%s\n%s\n", cPile, activePlayer.getTopCard().cString());
			for (int i = 0; i<communalPile.size(); i++)
			{
				cPile = String.format("%s%s\n", cPile, communalPile.get(i));
			}

			return cPile;
		}

		else 

		{
			String noCards = String.format("%s\n", "There are currently no cards in the communal pile");
			roundLog.append(noCards);
			roundLog.append(System.getProperty("line.separator"));
			return noCards;
		}

	}


	/**
	 * accessor method
	 * @return if draw or not (boolean)
	 */

	public boolean isDraw()

	{
		return draw;
	}

	/**
	 * accessor method
	 * @return int
	 */

	public static int getRoundCount() {
		return roundCount;
	}


	/**
	 * String 
	 * stores round info
	 * @return all info about round
	 */


	public String getRoundLog() 

	{
		return roundLog.toString();
	}


	/**
	 * round counter
	 */

	private static void addRound() 
	{
		roundCount++;
	}

	public static String getPlayerRoundWins() 
	{
	
 	return playerRoundWins.toString(); //for passing to Game for database update
	}


}