package commandline;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a single game round. 
 * It also contains methods necessary to run the round and redistribute cards
 * at the end of the round.
 */

public class Round {

	/**
	 * instance variables
	 */
	private Player activePlayer;
	private ArrayList<Player> players;

	private int c; // category
	public int roundCount;
	private Player winner;

	public static boolean draw = false; // 1st round starts with no draw
	public static ArrayList<Card> communalPile = new ArrayList<Card>(); // static because communal pile is shared between rounds

	// related to the log 
	private boolean writeToLog;
	private StringBuilder roundLog = new StringBuilder(); // each round has a new log
	private static final String newLine = (System.getProperty("line.separator"));
	private static final String logSeparator = newLine + 
			"------------------------------------------------------------------------------------------------" + newLine;
	private static final String logSeparator2 = newLine + 
			"================================================================================================" + newLine;

	/**
	 * Constructor method 
	 */
	public Round (int r, boolean w)
	{
		roundCount = r;
		writeToLog = w;
	}


	/**
	 * Calls methods necessary to execute the round
	 */
	public void playRound() 
	{	
		if ( writeToLog == true)	
		{ 
			startLog();
		}

		compareCards();
		finishRound();  
	} 

	/**
	 * Starts appending the log
	 */
	private void startLog() 
	{	
		roundLog.append(logSeparator2);
		roundLog.append(String.format("%s%d", "ROUND LOG FOR ROUND ", roundCount));
		roundLog.append(logSeparator2 + newLine);
		roundLog.append(String.format("%s%d%s%s", "The active player for round ", roundCount, " is ", 
				activePlayer.getName()));
		roundLog.append(newLine + String.format("%s%d%s%s", "The category for round ", roundCount, " is ", 
				activePlayer.getTopCard().getCategories().get(c)));
		roundLog.append(newLine + getCommunalPile() + newLine);

		if (players.get(0).isInGame() == true)
		{
			roundLog.append(newLine + "Your card details are printed below:" + newLine); 
			roundLog.append(players.get(0).getTopCard().cString()); 
			roundLog.append(newLine +  players.get(0).getTopCard().toString() + newLine); 
			roundLog.append(logSeparator);
		}

		else 
		{
			roundLog.append(newLine + "You are currently  out of cards" + newLine + logSeparator + newLine);
		}
	}



	/**
	 *  Updates the round as needed
	 */
	public void finishRound() 
	{
		if (draw == false)
		{
			distributeCards();			
			communalPile = new ArrayList<Card>(); // resets communal pile

			if (writeToLog == true)
			{
				String w = (newLine + "The winner of round " + roundCount + " was " + winner.getName() + newLine);		
				roundLog.append(w + logSeparator);
			}
		}

		if (draw == true)
		{
			draw();
			winner = null; // no winner
		} 
	}


	/**
	 * Compares all the cards in play and determines the winner of the round.
	 * Updates the currentWinner variable.
	 */
	public void compareCards()
	{
		int j = 0; // winner's index
		int w = 0; // highest value
		int t = 0; // temp score

		String cards = "Comparing cards for round " + roundCount + newLine + newLine + "The cards in play are:" + 
				newLine + String.format("\t\t%s", activePlayer.getTopCard().cString()) + newLine;

		String values = "The competing values are printed below:" + newLine;

		// finds the highest value - iterates through all player cards
		for (int i = 0; i<players.size(); i++)
		{
			if (players.get(i).isInGame()) 
			{
				t = Integer.valueOf(players.get(i).getTopCard().getAttribute(c));

				cards = cards + players.get(i).getName() + String.format("\t\t%s", players.get(i).getTopCard().toString()) + newLine;
				values = values + players.get(i).getName() + ": " + t + newLine;

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

		// writes to log
		if (writeToLog == true)
		{
			roundLog.append(newLine);
			roundLog.append(cards);
			roundLog.append(newLine);
			roundLog.append(values);
			roundLog.append(logSeparator);
		}
	}



	/**
	 * Distributes cards at the end of a round.
	 * Winner receives cards from other players.
	 * If there are cards in the communal pile, winner receives all cards in it.
	 */
	private void distributeCards() 
	{
		if (communalPile.size()>0) // from communal pile 
		{
			winner.receiveCards(communalPile);
			communalPile.removeAll(communalPile); // empties pile

			if (writeToLog == true)
			{
				String winnerIs = winner.getName() + " took all cards from the communal pile";			
				roundLog.append(newLine + winnerIs + newLine);
				roundLog.append(getCommunalPile() + newLine); 
			}

		}

		for (int i = 0; i<players.size(); i++) // from other players
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
	 * This method adds new cards to the communal pile.
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

		// updates log
		if (writeToLog == true)
		{
			String d = newLine + "Round " + roundCount + " was a draw" + newLine;
			roundLog.append(d);	
			roundLog.append(newLine + "New cards have been added to the communal pile!");
			roundLog.append(newLine + getCommunalPile() + newLine);
		}
	}




	/**
	 * Setter methods below
	 */


	/**
	 * @param p - players in game
	 */
	public void setPlayers(ArrayList<Player> p)
	{
		players = p;
	}

	/**
	 * @param ap - active Player
	 */
	public void setActivePlayer(Player ap)
	{
		activePlayer = ap;
	}

	/**
	 * @param category of the round
	 */
	public void setCategroy(int category)
	{
		c = category;
	}




	/** 
	 * Getter methods below
	 */



	/**
	 * returns round winner
	 */
	public Player getWinner()
	{
		return winner;
	}


	/**
	 * @return the contents of the communal pile as String
	 * if empty, @return a message saying that it's empty
	 */
	public String getCommunalPile()
	{
		if (communalPile.size()>0)

		{
			String cPile = "There are " + communalPile.size() +  " cards in the communal pile:" + newLine;

			cPile = cPile + newLine + activePlayer.getTopCard().cString() + newLine;
			for (int i = 0; i<communalPile.size(); i++)
			{
				cPile = cPile + communalPile.get(i) + newLine;
			}

			return cPile;
		}

		else 

		{
			String noCards = "There are no cards in the communal pile";	
			return noCards;
		}

	}


	/**
	 * @return if round was a or not  
	 */
	public boolean isDraw()
	{
		return draw;
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

}