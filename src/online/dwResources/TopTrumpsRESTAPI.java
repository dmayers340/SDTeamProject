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

import org.eclipse.jetty.server.Response;

import online.configuration.TopTrumpsJSONConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import commandline.Card;
import commandline.Deck;
import commandline.Game;
import commandline.Player;
import commandline.Round;
import commandline.TopTrumpsCLIApplication;
import commandline.TypeServlet;

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
	private String deck; 
	// private String line;
//	private String am;
	private Card topcard;
	private static ArrayList <Player> listOfPlayers;

	 private static  ArrayList<String> categories;
	 private ArrayList<Card> cardsInDeck;
	private int numberOfCards;
	private final int maxAttributes = 6;
	
	private static Round newRound;
	private Deck newDeck;
	private static Deck currentDeck;
	public static int numberOfPlayers=4; 
	private static int remainingPlayers=4;
	private static Player activePlayer;
	private static Player gameWinner;
	private static String playername;
	private Player h;
	/** A Jackson Object writer. It allows us to turn Java objects
	 * into JSON strings easily. */
	ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
//	ObjectReader oReader=new ObjectMapper().readValue(null, null);
	
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
		//Deck newdeck=new Deck();
		deck=conf.getDeckFile();
		categories = new ArrayList<String>();
		 cardsInDeck = new ArrayList<Card>();
		 newDeck = new Deck();
		
		 System.out.println(TypeServlet.uname + "printing something");
		 System.out.println(TypeServlet.uname);
			FileReader reader;
			try 
			{
				reader = new FileReader(deck);

				Scanner in = new Scanner (reader);
				String line = in.nextLine();
				newDeck.setCategories(line);
				
				// adds cards to the deck
				while (in.hasNextLine())
				{
					line = in.nextLine();	
					newDeck.addCard(line);
				}

			}
				catch (FileNotFoundException e) 
				{
					
				}
			gamestart();
	}
	
	public void gamestart()
	{

		newDeck.shuffleDeck();
		
		currentDeck = newDeck;

		createPlayers();
		dealCards();
		
	}
	
	private static void setActivePlayer()

	{
		if (newRound==null) // if new game

		{
			activePlayer = listOfPlayers.get(pickRandomPlayer());
		}

		else if (newRound.getWinner() == null) // if draw

		{
			return;  
		}

		else 

		{
			activePlayer = newRound.getWinner();
		}

	}
	public  void run()

	{

		if (remainingPlayers > 1)

		{
			setActivePlayer(); // set deciding player 
			newRound = new Round(listOfPlayers, activePlayer);
			newRound.playRound();
			updatePlayers(); 
		}

		newRound.getWinner();
		showWinner();

	}

	private static void updatePlayers ()

	{
		for (int i=0; i<listOfPlayers.size(); i++)

		{
			Player p = listOfPlayers.get(i);

			if (p.isInGame() && p.getHand().size()<1)

			{
				p.setStatus(false);
				remainingPlayers--;
			}
		}
	}
			
//	public static int howManyPlayers() { //add validation of input
//
//		numberofplayer=Integer.parseInt(TypeServlet.uname);
//			return numberofplayer;
//
//	}
	
	private static void showWinner ()

	{
		// if only one player is left with cards after a draw
		// they automatically become the winner
		if (newRound.isDraw())		
		{
			for (int i=0; i<listOfPlayers.size(); i++)
			{
				if (listOfPlayers.get(i).isInGame())
					gameWinner = listOfPlayers.get(i); 
			}
		}

		else 
		{
			gameWinner = newRound.getWinner();
		
		}	
		}
		
	public static void dealCards() {

		int numCardsEach = currentDeck.getNumberOfCards() / numberOfPlayers;

		int i;
		for (i = 0; i < numberOfPlayers; i++) {

			ArrayList<Card> cardsForEachPlayer = new ArrayList<Card>(currentDeck.getDeck().subList(0, numCardsEach));
			playername = listOfPlayers.get(i).getName();
			listOfPlayers.get(i).receiveCards(cardsForEachPlayer);
			currentDeck.getDeck().removeAll(cardsForEachPlayer);

		}

		if (!currentDeck.getDeck().isEmpty()) { //if cards remaining in deck

			listOfPlayers.get(pickRandomPlayer()).receiveExtraCards(currentDeck.getDeck());

		}

		System.out.println("Dealing cards...");
		System.out.println();
	}

	
	private static int pickRandomPlayer() { //returns random index number

		int randomIndex = (int)Math.floor(Math.random() * numberOfPlayers);
		return randomIndex;
	}
	
	public void createPlayers() {

		// numberOfPlayers = currentDeck.getNumPlayers(); 
		listOfPlayers = new ArrayList<Player>();
		String username = "player";

		int i = 0;

		// creates the human player
		h = new Player(username);
		h.setHuman();
		listOfPlayers.add(h);

		// create AI players
		for (i = 1; i < numberOfPlayers; i++) 

		{
			Player p = new Player("AI_Player" + i);
			listOfPlayers.add(p);
		}

	}
	
	// ----------------------------------------------------
	// Add relevant API methods here
	// ----------------------------------------------------
	@GET
	@Path("/ga")
	
	public String ga() throws IOException {
		run();
		String aa=activePlayer.getName()+"-----------------"+gameWinner.getName()+"-----------------"+newRound.getCate();
		String d = oWriter.writeValueAsString(aa);
		return d;
	}
	
	@GET
	@Path("/draw")
	
	public String draw() throws IOException {
		String dr = oWriter.writeValueAsString(newRound.isDraw());
		return dr;
	}
	
	@GET
	@Path("/com")
	
	public String com() throws IOException {
		String dr = oWriter.writeValueAsString(newRound.communalPile.size());
		return dr;
	}
	
	@GET
	@Path("/handnum")
	
	public String handnum() throws IOException {
	
		
		String hn = oWriter.writeValueAsString(h.getHand().size());
		return hn;
	}

	@GET
	@Path("/playercard")
	
	public String playercard() throws IOException {
	
		
		String pc = oWriter.writeValueAsString(h.getTopCard());
		return pc;
	}
	
	@GET
	@Path("/ai1")
	
	public String ai1() throws IOException {
	
		
		String m = oWriter.writeValueAsString(h.getTopCard());
		return m;
	}
//	@GET
//	@Path("/wina")
//	
//	public String win() throws IOException {
//		
//	
//		Player wine=gameWinner;
//		String win=wine.getName();
//		String wina = oWriter.writeValueAsString(win);
//		return wina;
//	}

	@GET
	@Path("/na")
	/**
	 * Here is an example of a simple REST get request that returns a String.
	 * We also illustrate here how we can convert Java objects to JSON strings.
	 * @return - List of words as JSON
	 * @throws IOException
	 */
	public String name() throws IOException {
		
		TypeServlet am=new TypeServlet();
		String c=am.uname;
		String m = oWriter.writeValueAsString(c);
		return m;
	}
	
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
		String a="";
		for (int i=0;i<listOfCate.size();i++){
			a=String.format("%s \n %s",a,listOfCate.get(i));
		}
		String listAsJSONString = oWriter.writeValueAsString(listOfCate);
		return listAsJSONString;
	}
		
		
	@POST
    @Path("/example")
    public Response testEndpoint(Example example) {

        String intermediate = example.getTest();
        System.err.println(intermediate);

        // Returns 200 back to the caller, meaning everything was ok.
        return Response.ok().build();
    }

    private static class Example {
        String test;

        // It's important to have at least empty constructor,
        // otherwise request may not be transformed into an object
        public Example() {}

        // Both getters/setters need to be provided. That's java beans standard.
        // It's possible to avoid this, but it's easier not to for now.
        public String getTest() {
            return test;
        }

        public void setTest(String test) {
            this.test = test;
        }
    }
	
	
	
}
