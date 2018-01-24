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
	private static ArrayList<Card> communalPile;
	public static boolean draw = false; // the first round starts with no draw
	
	private String roundLog = "ROUND LOG BELOW";
	

	private static int roundCount = 1;

	
	/**
	 * constructor method 
	 */

	public Round (ArrayList <Player> p, Player ap)

	{
		System.out.println("Round number " + (roundCount+1) + " is starting");

		players = p;  
		activePlayer = ap;
		communalPile = new ArrayList<Card>();
	}


	/**
	 * game sequence
	 */

	public void playRound() 

	{
		if (players.get(0).isHuman())

		{
			System.out.println("Your card details are printed below:");
			System.out.println();

			// print current card
			System.out.println(players.get(0).getTopCard().cString());
			System.out.println(players.get(0).getTopCard().toString());
			System.out.println();
		}

		chooseCategory();
		compareCards();

		if (!draw)

		{
			distributeCards();
		}

		addRound();

	}


	/**
	 * winner receives cards 
	 */

	private void distributeCards() 

	{
		// from communal pile 
		winner.receiveCards(communalPile);
		communalPile.removeAll(communalPile); // empties pile

		// winner receives cards from other players
		for (int i = 0; i<players.size(); i++)
		{
			Player p = players.get(i); // for readability

			if (p.hasCards())
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
		int i; // temp index
		int j = 0; // winner's index
		int w = 0; // highest value
		int t = 0; // temp score

		// finds the highest value
		for (i = 0; i<players.size(); i++)

		{
			if (players.get(i).hasCards()) 

			{
				t = Integer.valueOf(players.get(i).getTopCard().getAttribute(c));

				if (t>w)

				{	
					w = t; // stores highest value
					j = i; // stores player index
					draw = false;
					winner = players.get(j); // sets winner 
				}

				else if (t==w)

				{
					winner = activePlayer;
					draw = true;
					draw();

					System.out.println("The round was a draw");
					System.out.println("This should start a new round now");
					System.out.println();
				}

			}

		}
	}


	/**
	 * if draw
	 */

	private void draw() 

	{
		System.out.println("It's a draw!");

		if (players.get(0).isHuman())

		{
			System.out.println("Your card details are printed below:");

			// print current card
			System.out.println(players.get(0).getTopCard().cString());
			System.out.println(players.get(0).getTopCard().toString());
			System.out.println();
		}

		// adds cards to communal pile 
		for (int i = 0; i < players.size(); i++)

		{
			if (players.get(i).hasCards())
			{	
				communalPile.add(players.get(i).getTopCard());
				players.get(i).removeCard();
			}
		}

	}


	/**
	 * active player chooses category
	 * @return
	 */

	private void chooseCategory ()

	{

		if (!activePlayer.hasCards())
		{ 
			return; // category stays unchanged
		}


		else if (activePlayer.isHuman()) 

		{
			System.out.println("It's your turn! Please enter the name of the category.");

			// enter category name
			Scanner in = new Scanner (System.in);
			String category;
			category = in.next();

			if (category==null)
			{
				System.out.println("Please enter the name of the category");
				chooseCategory();
			}

			// update variable
			else

			{ 
				System.out.print("You selected: " + category);
				System.out.println();
				findCategoryIndex(category);
			}
		}

		else 

		{	
			// update variable
			findBestCategory();
		}
		
		updateRoundLog("The current category is: " + c);
	}


	/**
	 * searches active user's top card 
	 * finds the category with the highest value
	 * sets it to c
	 */

	private void findBestCategory () 

	{
		int temp1;
		int temp2 = 0;

		for (int i = 1; i < activePlayer.getTopCard().getCategories().size(); i++)

		{
			// finds the category with the highest value
			temp1 = Integer.valueOf(activePlayer.getTopCard().getAttribute(i)); // looks horrendous

			if (temp1>temp2)
			{	
				temp2=temp1;
				c = i;  
			}
		}


	}

	/**
	 * @param category name
	 * sets c (index)
	 */

	private void findCategoryIndex (String category)

	{
		int i;
		int temp = 0;

		for (i = 0; i < activePlayer.getTopCard().getCategories().size(); i++)

		{
			if (category.equals(activePlayer.getTopCard().getCategories().get(i)));
			temp = i;
		}

		if (temp==0) 

		{
			chooseCategory();
		}

		else
			c=temp;
	}


	/**
	 * returns round winner
	 */

	public Player getWinner()

	{
		System.out.println("The winner is " + winner.getName());
		System.out.println();

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
			return communalPile.toString();
		}

		else 

		{
			String noCards = ("There are currently no cards in the communal pile");
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
		roundLog = roundLog + s; 
	}

	/**
	 * String 
	 * stores round info
	 * @return all info about round
	 */
	
	public String getRoundLog() 
	
	{
		return roundLog; 
	}

	/**
	 * round counter
	 */

	private static void addRound() {

		roundCount++;
		System.out.println();
		System.out.println("Round " + roundCount + " has finished");
	}
}