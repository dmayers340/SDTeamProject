package commandline;

import java.util.ArrayList;
import java.util.Scanner;

public class Round {

	/**
	 * instance variables
	 */

	private Player activePlayer;
	private ArrayList<Player> players;

	private int currentCategory;

	private static int roundCount = 1;
	private static Deck currentDeck;
	private static Player winner;

	/**
	 * * constructor method 
	 */

	public Round (ArrayList <Player> p, Player ap, Deck cd)

	{
		players = p;  
		activePlayer = ap;
		currentDeck = cd;
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
			System.out.println(currentDeck.cString());
			System.out.println(players.get(0).getTopCard().toString());
			System.out.println();

		}

		chooseCategory();
		compareCards();
		addRound();

		// winner gets cards
		for (int i = 0; i<players.size()-1; i++)
		{
			
			if (players.get(i)!=winner)
				
			{
				
			winner.receiveCard(players.get(i).getTopCard());
			players.get(i).getHand().remove(players.get(i).getTopCard());
			
			System.out.println(players.get(i).getName() + " had a card removed from their deck");
			System.out.println("They now have " + players.get(i).getHand().size() + " cards in their deck. ");
			System.out.println();
			
			// hurts my eyes
			}
		}

	}


	/**
	 * sets round winner
	 */

	private void compareCards()

	{
		int i;
		int w = 0; // winning player
		int t = 0; // temp value

		for (i = 0; i<players.size()-1; i++)

		{
			// takes an attribute and compares it with temp
			t = Integer.valueOf(players.get(i).getTopCard().getAttribute(currentCategory));

			if (t>w)
				w=t;
		}

		if (t==w)
			draw();

		else 
			winner = players.get(i);
		
		System.out.println("Finished comparing cards ");

	}


	/**
	 * if draw
	 */

	private void draw() 

	{

		System.out.println("It's a draw!");
		System.out.println("This method is not going to write itself.");
		
		chooseCategory();

		winner = activePlayer;
	}


	/**
	 * active player chooses category
	 * @return
	 */

	private void chooseCategory ()

	{
		// if active player is human
		if (activePlayer.isHuman())

		{
			System.out.println("It's your turn! Please enter the name of the category.");

			// enter category name
			Scanner in = new Scanner (System.in);
			String category;

			System.out.print("You selected:");
			category = in.next();

			// update variable
			findCategoryIndex(category);
		}

		else 

		{	
			// update variable
			findBestCategory();

			// print out the choice
			System.out.println();
			System.out.println(activePlayer.getName() + " has selected "  
					+ currentDeck.getCategories().get(currentCategory));
		}
	}


	/**
	 * searches active user's top card 
	 * finds the category with the highest value
	 * sets it to currentCategory
	 */

	private void findBestCategory () 

	{
		int temp1;
		int temp2 = 0;
		for (int i = 1; i < currentDeck.getCategories().size(); i++)

		{
			// finds the category with the highest value
			temp1 = Integer.valueOf(activePlayer.getTopCard().getAttribute(i)); // looks horrendous

			if (temp1>temp2)
			{	
				temp2=temp1;
				currentCategory = i;
			}
		}


	}

	/**
	 * @param category name
	 * sets currentCategory (index)
	 */

	private void findCategoryIndex (String category)

	{
		for (int i = 0; i < currentDeck.getCategories().size(); i++)

		{
			if (category.equals(currentDeck.getCategories().get(i))) 

				// set category
				currentCategory = i;
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
	 * round counter
	 */

	private static void addRound() {

		roundCount++;
		System.out.println();
		System.out.println("Round " + roundCount + " has finished");
	}
}