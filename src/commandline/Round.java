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
	private ArrayList<Card> communalPile; 

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

	
	public int getRoundCount() {
		return roundCount;
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

		String topCard = players.get(0).getTopCard().toString();
		chooseCategory();
		compareCards();
		addRound();

		// winner gets cards
		for (int i = 0; i<players.size()-1; i++)
		{
			
			if (players.get(i)!=winner)
				
			{
			
			// winner receives cards 
			winner.receiveCard(players.get(i).getTopCard());
			players.get(i).removeCard();
	
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
			System.out.println("The index of player: " + i); System.out.println();
			// takes an attribute and compares it with temp
			// hurts my brain

			String playerName = (players.get(i).getName());
			System.out.println ("player in this position" + playerName);

			String isActiveOrNot = "";
			if (players.get(i).hasCards())

			{
				isActiveOrNot = ("Player has cards");
			}

			if (!players.get(i).hasCards())

			{
				isActiveOrNot = ("Player has NO cards");
			}

			System.out.println ("Does the player have cards? " + isActiveOrNot);

			int hSize = players.get(i).getHand().size();
			String handSize = ("the player's hand size is " + hSize);
			System.out.println(handSize);

			String topCard = (players.get(i).getHand().get(0).toString());
			System.out.println("player's uppermost card is: " + topCard);

			t = Integer.valueOf(players.get(i).getTopCard().getAttribute(currentCategory));


			if (t>w)
				w=t;

			System.out.println("Loop finished");
		}

		if (t==w)
			draw();

		else 
			winner = players.get(i);

	}


	/**
	 * if draw
	 */

	private void draw() 

	{
		
		System.out.println("It's a draw!");
		
		// all cards go into the communal pile (add top card)
		
		for (int i = 0; i < players.size(); i++)
			
		{
			// adds all top cards to the communal pile
			communalPile.add(players.get(i).getTopCard());
			players.get(i).removeCard();
		}
		
		System.out.println("There currently " + communalPile.size() + " cards in the communal pile");
		System.out.println();
		
		chooseCategory();
		compareCards();
		
		// winner receives all cards in the communal pile
		winner.receiveCards(communalPile);
		

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
				System.out.println(activePlayer.getTopCard().getAttribute(0)); // description
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