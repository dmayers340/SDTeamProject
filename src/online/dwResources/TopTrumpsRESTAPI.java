package online.dwResources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import online.configuration.TopTrumpsJSONConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

//imports from commandline
import commandline.Card;
import commandline.Deck;
import commandline.Game;
import commandline.Player;
import commandline.Round;
import commandline.TopTrumpsCLIApplication;
//import commandline.DatabaseConnection;

@Path("/toptrumps")
// Resources specified here should be hosted at http://localhost:7777/toptrumps
@Produces(MediaType.APPLICATION_JSON)
// This resource returns JSON content
@Consumes(MediaType.APPLICATION_JSON)
// This resource can take JSON content as input
/**
 * This is a Dropwizard Resource that specifies what to provide when a user
 * requests a particular URL. In this case, the URLs are associated to the
 * different REST API methods that you will need to expose the game commands
 * to the Web page.
 * 
 * Below are provided some sample methods that illustrate how to create
 * REST API methods in Dropwizard. You will need to replace these with
 * methods that allow a TopTrumps game to be controled from a Web page.
 */
public class TopTrumpsRESTAPI 
{  
	private Game game;

	private String deck;
	private int numberOfPlayers;

	//Database Connection
//	private DatabaseConnection db = new DatabaseConnection();

	ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();

	//Constructor, sets the deck, and number of players
	public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) 
	{	
		//THIS IS STRING==need to pass to deck class to make categories
		deck = conf.getDeckFile();
		numberOfPlayers=conf.getNumAIPlayers()+1;
	}



	// API Methods 

	//This starts the game for game screen
	@GET
	@Path("/newgame")
	public void newGame() throws IOException
	{
		String card = "";	
		String loser;
		
		Game game = new Game();
		game.setNumberOfPlayers(numberOfPlayers);
		game.setUsername("Human");
		game.initialiseGame();

		if (game.getHumanPlayer().isInGame() == false)
		{
			loser = "You are out of cards";
		}
		else
		{
			String values = game.getHumanPlayer().getTopCard().toString();
			card = game.getHumanPlayer().getTopCard().cString();

		}
		//start the game by getting number of players and dealing cards
		//game.startOnlineRound();
	//	return card;
		//display all player cards

		//get new game from game.java
		game = new Game();
		//game.setOnline(true);
		game.setNumberOfPlayers(numberOfPlayers);
		game.setUsername("Human");

		//start the game by getting number of players and dealing cards
		game.initialiseGame();

		while (game.getStatus() == false)
		{
			game.chooseActivePlayer();

			if (game.getActivePlayer().isHuman()==true)
			{

				game.setCurrentCategory(0);;
			}

			else
			{
				game.getActivePlayer().findBestCategory();
			}

			game.startRound();
			//round.numberOfGames++; /// add variable
		}
		
	}

	//This method saves the data from the game that was just played, and sends to the database
	@GET
	@Path("/saveandquit")
	public void saveAndQuit() throws IOException
	{
		//save the game data, and send to database

	}

	//Goes to next round
	@GET
	@Path("/nextround")
	public void nextRound() throws IOException
	{
		game.startRound();
		System.err.println("asdsdada");
	}

	@GET
	@Path("/cardDescription")
	public String cardDescription() throws IOException
	{
		String cD=oWriter.writeValueAsString(Card.getCategories());
		return cD;		
	}

	@GET
	@Path("/cardCategories1")
	public String cardDescription1() throws IOException
	{
		String cD=oWriter.writeValueAsString(game.getPlayer(0).getTopCard().toString());
		return cD;
	}
	
	@GET
	@Path("/cardCategories2")
	public String cardDescription2() throws IOException
	{
		String cD=oWriter.writeValueAsString(game.getPlayer(1).getTopCard().toString());
		return cD;
	}

	@GET
	@Path("/cardCategories3")
	public String cardDescription3() throws IOException
	{
		String cD=oWriter.writeValueAsString(game.getPlayer(2).getTopCard().toString());
		return cD;
	}

	@GET
	@Path("/cardCategories4")
	public String cardDescription4() throws IOException
	{
		String cD=oWriter.writeValueAsString(game.getPlayer(3).getTopCard().toString());
		return cD;
	}

	@GET
	@Path("/cardCategories5")
	public String cardDescription5() throws IOException
	{
		String cD=oWriter.writeValueAsString(game.getPlayer(4).getTopCard().toString());
		return cD;
	}


//	//Get the number of games played from database for statistic screen
//	@GET
//	@Path("/numGames")
//	public int numberOfGames() throws IOException
//	{
//		int numGames = db.getNumberOfGames();
//		return numGames;
//	}
//
//	//Get number of times computer has won from database for stat screen
//	@GET
//	@Path("/timescomputerwon")
//	public int timesComputerWon() throws IOException
//	{
//		int compWins = db.getComputerWin();
//		return compWins;
//	}
//
//	//Get number of times human has won from database for stat screen
//	@GET
//	@Path("/humanwin")
//	public int timesPersonWon() throws IOException
//	{
//		int humanwin = db.getHumanWin();
//		return humanwin;
//	}
//
//	//Get average number of draws from database for stat screen
//	@GET
//	@Path("/numDraws")
//	public double numDraws() throws IOException
//	{
//		double numDraws = db.getNumberOfDraws();
//		return numDraws;
//	}
//
//	//Get the maximum amount of rounds from database for stat screen
//	@GET 
//	@Path("/numRounds")
//	public int numRounds() throws IOException
//	{
//		int numRounds = db.getMaxRounds();
//		return numRounds;
//	}
//
//	//Not sure where to close the database, made this an attempt to close
//	@GET
//	@Path("/closedb")
//	public void closedb() throws IOException
//	{
//		db.closeConnection();
//	}
}
