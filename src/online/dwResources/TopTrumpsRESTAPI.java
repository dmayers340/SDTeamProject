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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import online.configuration.TopTrumpsJSONConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import commandline.Card;
import commandline.Deck;
import commandline.Game;
import commandline.Player;
import commandline.Round;
import commandline.TopTrumpsCLIApplication;

@Path("/toptrumps") // Resources specified here should be hosted at http://localhost:7777/toptrumps
@Produces(MediaType.APPLICATION_JSON) // This resource returns JSON content
@Consumes(MediaType.APPLICATION_JSON) // This resource can take JSON content as input
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
	String deck;
	String line;
	String am;
	private Card topcard;

	private static  ArrayList<String> categories;
	private ArrayList<Card> cardsInDeck;
	private int numberOfCards;
	private final int maxAttributes = 6;
	private  Deck newDeck;
	private Round newRound;
	
	
	/** A Jackson Object writer. It allows us to turn Java objects
	 * into JSON strings easily. */
	ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
	
	/**
	 * Contructor method for the REST API. This is called first. It provides
	 * a TopTrumpsJSONConfiguration from which you can get the location of
	 * the deck file and the number of AI players.
	 * @param conf
	 */
	public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) {
		// ----------------------------------------------------
		// Add relevant initalization here
		// ----------------------------------------------------
		
		deck=conf.getDeckFile();
		//String FILE_NAME = deck;
		int n=conf.getNumAIPlayers();
	//	TopTrumpsCLIApplication.readIn();
		categories = new ArrayList<String>();
		cardsInDeck = new ArrayList<Card>();
		newDeck = new Deck();
		
			FileReader reader;
			try 
			{
				reader = new FileReader(deck);

				Scanner in = new Scanner (reader);
				String line = in.nextLine();
				
				// sets categories 
				String [] split = new String [maxAttributes];
				split = line.split(" ");
				Collections.addAll(categories, split);
				// adds cards to the deck
				while (in.hasNextLine())
				{
					line = in.nextLine();	
					ArrayList<String> card = new ArrayList<String>();
					Collections.addAll(card, split);
					
					Card newCard = new Card(categories, card);
					cardsInDeck.add(newCard);
					numberOfCards++;
				}
			}
				catch (FileNotFoundException e) 
				{
					
				}
				
			Game newGame = new Game(newDeck);
			newDeck.shuffleDeck();
			int numberOfPlayers = n+1; // AI players + human player
			int remainingPlayers = n+1; // starts with all players still in game
			
			while (remainingPlayers > 1)

			{
				ArrayList <Player> listOfPlayers = null;
				Player activePlayer = null;
				newGame.setActivePlayer(); // set deciding player 
				newRound = new Round(listOfPlayers, activePlayer);

				
				 
				newRound.playRound();

				 // updates number of remaining players
			}

			newRound.getWinner();
	}
//
//		}
			// shuffle
//			
//			int listLength = cardsInDeck.size();
//			Card temp; 
//
//			for (int i = 0; i < listLength; i++)
//
//			
//
//			{
//				// generates a random integer
//				int random = (int)Math.floor(Math.random() * listLength);
//
//				// swaps cards at indexes 'i' and 'random'
//				temp = cardsInDeck.get(i);
//				cardsInDeck.set(i, cardsInDeck.get(random));
//				cardsInDeck.set(random,temp);
//			}
//	
	
	

		
			
			
			// sets categories 
			
			
			// Shuffle
			
	
	
	// ----------------------------------------------------
	// Add relevant API methods here
	// ----------------------------------------------------
	
	@GET
	@Path("/cateJSONList")
	/**
	 * Here is an example of a simple REST get request that returns a String.
	 * We also illustrate here how we can convert Java objects to JSON strings.
	 * @return - List of words as JSON
	 * @throws IOException
	 */
	public String cateJSONList() throws IOException {
		
	
		ArrayList<String> listOfCate = new ArrayList<String>();
		listOfCate=Card.getCategories();
		String listAsJSONString = oWriter.writeValueAsString(listOfCate);
		return listAsJSONString;
	}
//		
		
	@GET
	@Path("/topcard")
	public String topcard() throws IOException {
		String listAsJSONString = oWriter.writeValueAsString(Player.getTopCard());
		return listAsJSONString;
		
	}
		
		// We can turn arbatory Java objects directly into JSON strings using
		// Jackson seralization, assuming that the Java objects are not too complex.
		
	
	
	
	@GET
	@Path("/helloWord")
	/**
	 * Here is an example of how to read parameters provided in an HTML Get request.
	 * @param Word - A word
	 * @return - A String
	 * @throws IOException
	 */
	public String helloWord(@QueryParam("Word") String Word) throws IOException {
		return "Hello "+Word;
	}
	
	
	
}
