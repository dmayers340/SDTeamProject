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

import commandline.Card;
import commandline.DatabaseConnection;
import commandline.Deck;
import commandline.Game;
import commandline.Player;
import commandline.Round;
import commandline.TopTrumpsCLIApplication;

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
public class TopTrumpsRESTAPI {
	private Game game;
	private String deck;
	private int numberOfPlayers;

	private DatabaseConnection db = new DatabaseConnection();
	ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();

	public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) {
		
		deck = conf.getDeckFile();
		
		numberOfPlayers=conf.getNumAIPlayers()+1;
		try {
			newGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	@GET
	@Path("/newgame")
	public void newGame() throws IOException
	{
		//display all player cards

		//get new game from game.java
		game = new Game(db);
		game.setOnline(true);
		game.setNumberOfPlayers(numberOfPlayers);
		game.setUsername("Human");

		//start the game by getting number of players and dealing cards
		game.initialiseGame();

		while (game.getStatus() == false)
		{
			game.chooseActivePlayer();

			if (game.getActivePlayer().isHuman()==true)
			{

				game.setCurrentCategory(1);
			}

			else
			{
				game.findBestCategory();
			}

		
			//round.numberOfGames++; /// add variable
		}
		
	}

	

	// ----------------------------------------------------
	// Add relevant API methods here
	// ----------------------------------------------------
	@GET
	@Path("/activeplayer")
	public String activePlayer() throws IOException {
		game.startRound();
		
		String aa = game.getActivePlayer().getName();
			//	+ game.getcurrentWinner().getName() + "-----------------"
			//	+ game.getCate();
		
		String d = oWriter.writeValueAsString(aa);
		return d;
	}
	
	@GET
	@Path("/roundwinner")
	public String roundwinner() throws IOException{
		String rw=game.getcurrentWinner().getName();
		String nrw = oWriter.writeValueAsString(rw);
		return nrw;
	}

	@GET
	@Path("/cateSele")
	public String cateSelection() throws IOException{
		String cs=game.getCate();
		String ncs = oWriter.writeValueAsString(cs);
		return ncs;
	}
	
	@GET
	@Path("/draw")
	public String draw() throws IOException {
	
		String dr = oWriter.writeValueAsString(game.isDraw());
		return dr;
	}

	@GET
	@Path("/compilesize")
	public String pileSize() throws IOException {
		String dr = oWriter.writeValueAsString(game.getPileSize());
		return dr;
	}

	@GET
	@Path("/handnum")
	public String handnum() throws IOException {
		
		String hn;
		if (game.getPlayer(0).isInGame()==true)
		{
		hn = oWriter.writeValueAsString(game.getPlayer(0).getHand().size());
		}
		
		else 		
		{
			hn = "This player is out of cards";
		}
		
		return hn;
	}

	@GET
	@Path("/cc1")
	public String cardCategories1() throws IOException {
		String m= "yyu";
		String pc = oWriter.writeValueAsString(m);
	
		return pc;
	}
	
	@GET
	@Path("/sca")

	public int sca(@QueryParam("num") int a) throws IOException {
		System.err.println(a);
	
		return a;
		
	}

	@GET
	@Path("/cardDescription")
	public String cardDescription() throws IOException {
		
		System.err.println("0000000000000000000000000000000000000000000000000000000000000000000001");
		System.err.println(game.getActivePlayer().getName() + "PLAYAAAAAAAAAAAAAAAAAAA NAME");
		
		Card card = game.getActivePlayer().getTopCard();
		System.err.println("000000000000000000000000000000000000000000000000000000000000000000000");
		System.out.println(card.toString() + "cAAAAAAAAAAAAaaaaRD");
		String cD=oWriter.writeValueAsString(card.toString());
		return cD;
	}

	
	@GET
	@Path("/saveandquit")
	public void saveAndQuit() throws IOException
	{
		//save the game data, and send to database

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
