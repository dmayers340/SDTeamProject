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
import commandline.DatabaseConnection;
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
	private Player activePlayer;
	private String deck;
	private int numberOfPlayers;
	private String card="";
	private String winner;
	private int numcard;
	private int cm;
	//Database Connection
	private DatabaseConnection db = new DatabaseConnection();

	ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();

	//Constructor, sets the deck, and number of players
	public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) 
	{	
		//THIS IS STRING==need to pass to deck class to make categories
		deck = conf.getDeckFile();
		numberOfPlayers=conf.getNumAIPlayers()+1;
		game = new Game(db);
		game.setNumberOfPlayers(numberOfPlayers);
		game.setUsername("Human");
		game.initialiseGame();


	}



	//This starts the game for game screen and user get his top card at hand
	@GET
	@Path("/newgame")
	public String newGame() throws IOException

	{ 
		game.chooseActivePlayer();
		activePlayer = game.getActivePlayer();
		
		if (activePlayer.isHuman() == true)
		{ 
			game.setCurrentCategory(cm);
		}
		
		else
		{
			cm = activePlayer.findBestCategory();
			game.setCurrentCategory(cm);
		}
	
		game.startRound();
		winner=game.getWinner().getName();	

		if (game.isFinished() == true)		
		{
			card = "The game is over! The winner is " + winner;
		}

		//start the game by getting number of players and dealing cards	
		else if (game.getHumanPlayer().isInGame() == false)
		{
			card = "You are out of cards";
		}


		else
		{
			String values1 = game.getHumanPlayer().getTopCard().toString();		
			card = game.getHumanPlayer().getTopCard().cString() + values1;		
		}


		numcard=game.getHumanPlayer().getHand().size();

		return card;
	}

	//get AI1 top card at hand
	@GET
	@Path("/cardCategories2")
	public String cardDescription2() throws IOException
	{	
		String AI1;

		if (game.getPlayer(1).isInGame() == false)
		{AI1 = game.getPlayer(1).getName() + " is out of cards";}

		else 
		{
			String values2 = game.getPlayer(1).getTopCard().toString();
			AI1=oWriter.writeValueAsString(game.getPlayer(1).getTopCard().cString() + values2);
		}
		return AI1;
	}

	//This method saves the data from the game that was just played, and sends to the database
	@GET
	@Path("/saveandquit")
	public void saveAndQuit() throws IOException
	{
		//save the game data, and send to database

	}

	//get AI2 top card at hand
	@GET
	@Path("/cardCategories3")
	public String cardDescription3() throws IOException
	{

		String AI2;

		if (game.getPlayer(2).isInGame() == false)
		{AI2 = game.getPlayer(2).getName() + " is out of cards";}

		else 
		{
			String values3 = game.getPlayer(2).getTopCard().toString();
			AI2=oWriter.writeValueAsString(game.getPlayer(2).getTopCard().cString() + values3);
		}

		return AI2;
	}

	//get AI3 top card at hand
	@GET
	@Path("/cardCategories4")
	public String cardDescription4() throws IOException
	{

		String AI3;

		if (game.getPlayer(3).isInGame() == false)
		{AI3 = game.getPlayer(3).getName() + " is out of cards";}

		else 
		{
			String values4 = game.getPlayer(3).getTopCard().toString();
			AI3=oWriter.writeValueAsString(game.getPlayer(3).getTopCard().cString() + values4);
		}
		return AI3;
	}

	//get AI4 top card at hand
	@GET
	@Path("/cardCategories5")
	public String cardDescription5() throws IOException
	{

		String AI4;

		if (game.getPlayer(4).isInGame() == false)
		{AI4 = game.getPlayer(4).getName() + " is out of cards";}

		else 
		{
			String values3 = game.getPlayer(4).getTopCard().toString();
			AI4=oWriter.writeValueAsString(game.getPlayer(4).getTopCard().cString() + values3);
		}
		return AI4;
	}

	//get the round winner
	@GET
	@Path("/roundwinner")
	public String showRoundWinner() throws IOException
	{

		return winner;
	}

	//get the active player name
	@GET
	@Path("/activeplayer")
	public String showActivePlayer() throws IOException
	{
		String act=activePlayer.getName();
		return act;
	}

	// send round result of whether is a draw to html but returns not true/false or 0/1
	@GET
	@Path("/draw")
	public String isdraw() throws IOException
	{
		String dr=oWriter.writeValueAsString(game.wasADraw());
		return dr;
	}

	//send card number of human player hand to html but number not correct
	@GET
	@Path("/cardnum")
	public String getCardNum() throws IOException
	{
		String CardNum=oWriter.writeValueAsString(numcard);
		return CardNum;
	}

	// when user click the new game button should start a new game
	@GET
	@Path("/newg")

	public int startNewGame(@QueryParam("num") int a) throws IOException {
		if (a==1)
		{
			game = new Game(db);
			game.setNumberOfPlayers(numberOfPlayers);
			game.setUsername("Human");
			game.initialiseGame();
		}
		System.err.println(a);
		return a;	
	}

	// return the category of user selected as int 1:size 2: speed 3:range 4:firepower  5:cargo
	@GET
	@Path("/sca")

	public int getCategory(@QueryParam("num") int category) throws IOException {
		System.err.println(category);
		
		cm = category;
		
		return category; 

	}


		//Get the number of games played from database for statistic screen
		@GET
		@Path("/numGames")
		public int numberOfGames() throws IOException
		{
			int numGames = db.getNumberOfGames();
			return numGames;
		}
	
		//Get number of times computer has won from database for stat screen
		@GET
		@Path("/timescomputerwon")
		public int timesComputerWon() throws IOException
		{
			int compWins = db.getComputerWin();
			return compWins;
		}
	
		//Get number of times human has won from database for stat screen
		@GET
		@Path("/humanwin")
		public int timesPersonWon() throws IOException
		{
			int humanwin = db.getHumanWin();
			return humanwin;
		}
	
		//Get average number of draws from database for stat screen
		@GET
		@Path("/numDraws")
		public double numDraws() throws IOException
		{
			double numDraws = db.getNumberOfDraws();
			return numDraws;
		}
	
		//Get the maximum amount of rounds from database for stat screen
		@GET 
		@Path("/numRounds")
		public int numRounds() throws IOException
		{
			int numRounds = db.getMaxRounds();
			return numRounds;
		}
	
		//Not sure where to close the database, made this an attempt to close
		@GET
		@Path("/closedb")
		public void closedb() throws IOException
		{
			db.closeConnection();
		}
}
