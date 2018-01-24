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
	private ArrayList<Card> communalPile;
	private boolean draw; 

	private static int roundCount = 1;

	/**
	 * constructor method 
	 */

	public Round (ArrayList <Player> p, Player ap)

	{
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
		distributeCards();
		addRound();

	}


	/**
	 * winner receives cards 
	 */

	private void distributeCards() 

	{
		// from communal pile 
		if (communalPile.size()>0)

		{
			winner.receiveCards(communalPile);
			communalPile.removeAll(communalPile); // empties pile
		}

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
				}

				else if (t==w)

				{
					draw = true;
				}

			}

		}

		if (draw==true)
			draw();

		else 
			winner = players.get(j); // sets winner and returns

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
			System.out.println();

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

		chooseCategory();
		compareCards();

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
				findCategoryIndex(category);
			}
		}

		else 

		{	
			// update variable
			findBestCategory();
		}
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
		for (int i = 1; i < activePlayer.getTopCard().getCategories().size(); i++)

		{
			if (category.equals(activePlayer.getTopCard().getCategories().get(i))) 

				// set category
				c = i;
		}

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
	 * @return int
	 */

	public int getRoundCount() {
		return roundCount;
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