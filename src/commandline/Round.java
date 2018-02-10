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

	public int roundCount;

	public static boolean draw = false; // 1st round starts with no draw
	public static ArrayList<Card> communalPile = new ArrayList<Card>(); 

	private StringBuilder roundLog = new StringBuilder();

	private static final String newLine = (System.getProperty("line.separator"));
	private static final String logSeparator = newLine + 
			"------------------------------------------------------------------------------------------------" + newLine;
	private static final String logSeparator2 = newLine + 
			"================================================================================================" + newLine;
	private boolean writeToLog;

	private boolean online;


	/**
	 * constructor method 
	 */

	public Round (ArrayList <Player> p, Player ap, Integer category, int r, boolean w, boolean o)
	{
		roundCount = r;
		writeToLog = w;
		online = o;

		players = p;  
		activePlayer = ap; 
		c = category; // sets category

		if ( writeToLog == true)	
		{ 
			startLog();
		}

	}

	public void addRound() 

	{
		try{
			Thread.sleep(2000);

		}

		catch(InterruptedException e){		
		}
	}

	/**
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
	 * game sequence
	 */

	public void playRound() 

	{	
		compareCards();
		setWinner();  
	} 


	/**
	 *  sets winner
	 */

	public void setWinner() 
	{
		if (draw == false)

		{
			distributeCards();			
			communalPile = new ArrayList<Card>(); // resets communal pile

			String w = (newLine + "The winner of round " + roundCount + " was " + winner.getName() + newLine);		
			roundLog.append(w + logSeparator);
		}

		if (draw == true)

		{
			draw();
			winner = null; // no winner
		} 
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

			if (writeToLog == true)
			{
				String winnerIs = winner.getName() + " took all cards from the communal pile";			
				roundLog.append(newLine + winnerIs + newLine);
				roundLog.append(getCommunalPile() + newLine); 
			}

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

		String cards = "Comparing cards for round " + roundCount + newLine + newLine + "The cards in play are:" + 
				newLine + String.format("\t\t%s", activePlayer.getTopCard().cString()) + newLine;

		String values = "The competing values are printed below:" + newLine;

		// finds the highest value
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

		String d = newLine + "Round " + roundCount + " was a draw" + newLine;

		roundLog.append(d);	
		roundLog.append(newLine + "New cards have been added to the communal pile!");
		roundLog.append(newLine + getCommunalPile() + newLine);

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
	 * String 
	 * stores round info
	 * @return all info about round
	 */
	public String getRoundLog() 

	{
		return roundLog.toString();
	}

}