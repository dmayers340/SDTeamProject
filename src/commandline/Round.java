package commandline;

import java.util.ArrayList;
import java.util.Scanner;

public class Round {

	/**
	 * instance variables
	 */

	private Player activePlayer;
	private ArrayList<Player> players;

	private int c; // category
	private Player winner;

	public static boolean draw = false; // 1st round starts with no draw
	private static ArrayList<Card> communalPile = new ArrayList<Card>(); 

	private String roundLog;
	private String logSeparator = "--------------------------------------------------------------------------------------";
	private static int roundCount = 0;


	/**
	 * constructor method 
	 */

	public Round (ArrayList <Player> p, Player ap)

	{
		addRound();
		roundLog = String.format("%s%d\n", "ROUND LOG FOR ROUND ", roundCount); // sets top line for round log
		System.out.println();

		players = p;  
		activePlayer = ap;
	}


	/**
	 * game sequence
	 */

	public void playRound() 

	{	
		System.out.println("ROUND NUMBER " + (roundCount)); 
		String a = "The active player is " + activePlayer.getName();

		updateRoundLog(a);
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

		setCategory();
		compareCards();
		setWinner();  

	} 

	/**
	 * determines which method to call
	 * prints system.out
	 */

	private void setCategory()

	{
		// player chooses category
		if (activePlayer.isHuman()) 
		{
			chooseCategory(); // player chooses category
		}

		// auto-picks category
		else 
		{	
			findBestCategory();  
			String s = String.format("%s%s%d%s\n", 
					activePlayer.getName(), " is choosing the category for round ", roundCount, "...");
			System.out.println(s);
			roundLog = roundLog + s;
		}

		// system out
		String cat = String.format("%s%d%s%s", "The category for round ", roundCount," is ", 
				activePlayer.getTopCard().getCategories().get(c));
		System.out.println(cat);
		System.out.println("+ + + ");
		updateRoundLog(cat);

	}

	/**
	 *  sets winner
	 */

	private void setWinner() 

	{
		if (draw == false)

		{
			String w = "The winner is " + winner.getName();
			updateRoundLog(w);
			System.out.println(w);
			System.out.println("The winning card for round " + roundCount + " was: ");
			System.out.println(winner.getTopCard().cString());
			System.out.println(winner.getTopCard().toString());

			distributeCards();
			communalPile = new ArrayList<Card>(); // resets communal pile
		}

		if (draw == true)

		{
			draw();
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

			updateRoundLog(winner.getName() + " took all cards from the communal pile");
			updateRoundLog(getCommunalPile());

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

	private void compareCards()

	{
		int j = 0; // winner's index
		int w = 0; // highest value
		int t = 0; // temp score

		String cards = String.format("%s%d%s\n\n%s\n%-15s%s", 
				"Comparing cards for round ", roundCount, "...", 
				"The contents of cards in game are printed below:", " ", activePlayer.getTopCard().cString());
		String values = String.format("%s", "The competing values are printed below:");

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
					draw = false;
				}

				else if (t==w)

				{
					draw = true;
				}
			}
		}

		updateRoundLog(cards);
		updateRoundLog(values);

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
		
		String d = "Round " + roundCount + " was a draw";
		System.out.println(d);
		
		updateRoundLog(d);
		updateRoundLog("New cards have been added to the communal pile!");
		updateRoundLog(getCommunalPile());

	}


	/**
	 * active player chooses category
	 */

	private void chooseCategory ()

	{
		System.out.println("It's your turn to choose! Please enter the name of the category.");

		String s = ("The human player " + activePlayer.getName() + " chose the category for round " + roundCount);
		roundLog = roundLog + s;

		// enter category name
		Scanner in = new Scanner (System.in);
		String category = "";
		category = in.next();

		int temp = -1;

		// checks if a valid category name was entered
		for (int i = 0; i < players.get(0).getTopCard().getCategories().size(); i++)
		{
			if (category.equals(activePlayer.getTopCard().getCategories().get(i)))
				temp = i;
		}

		if (temp<1) // if category name not found or "description"
		{
			System.out.println("You must enter a valid category name.");
			chooseCategory();
		}

		else
			this.c=temp; // sets category
	}


	/**
	 * searches active user's top card 
	 * finds the category with the highest value
	 * sets it to c
	 */

	private void findBestCategory () 

	{ 
		int curr; // current value
		int temp = 0; // temp highest value
		int index = 0; // index of the highest value

		for (int i = 1; i < activePlayer.getTopCard().getCategories().size(); i++)
		{
			// finds the category with the highest value
			curr = Integer.valueOf(activePlayer.getTopCard().getAttribute(i)); // looks horrendous

			if (curr>temp)
			{	
				temp=curr;
				index = i;
			}
		}

		c = index;
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
			String cPile = ("There are " + communalPile.size() + 
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
			roundLog = roundLog + noCards;
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

	public int getRoundCount() {
		return roundCount;
	}

	/**
	 * adds more stuff to log
	 */

	private void updateRoundLog(String s)

	{
		roundLog = String.format("%s\n%s\n", roundLog, s); 
	}


	/**
	 * String 
	 * stores round info
	 * @return all info about round
	 */


	public String getRoundLog() 

	{
		roundLog = String.format("%s\n%s%d", roundLog, "END OF LOG FOR ROUND ", roundCount);
		return roundLog; 
	}


	/**
	 * round counter
	 */

	private static void addRound() {

		roundCount++;
	}


}