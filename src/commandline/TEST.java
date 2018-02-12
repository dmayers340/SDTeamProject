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
import commandline.DatabaseConnection;

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
	private int roundCount, numberOfCards, category;
	private final int MAXATTRIBUTES = 6;
	public static int numberOfPlayers;
	private static int remainingPlayers;
	
	private String deck;
	private static String playername;
	
	private Card topcard;	
	private Deck newDeck;
	private static Deck currentDeck;
	private Player player;
	private static Player activePlayer, gameWinner;
	private static Round newRound;
	private static ArrayList<Player> listOfPlayers;
	private static ArrayList<String> categories;
	private ArrayList<Card> cardsInDeck;
	
	//Database Connection
	private DatabaseConnection db = new DatabaseConnection();
	
	ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();

	//Constructor, sets the deck, categories, cards, number of players
	public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) 
	{	
		deck = conf.getDeckFile();
		categories = new ArrayList<String>();
		cardsInDeck = new ArrayList<Card>();
		newDeck = new Deck();
		numberOfPlayers=conf.getNumAIPlayers()+1;
		remainingPlayers=numberOfPlayers;
		FileReader reader;
		
		//read the deck file in to get cards/categories--THIS IS DONE IN DECK CLASS ALREADY??????
		try 
		{
			reader = new FileReader(deck);
			Scanner in = new Scanner(reader);
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
			e.printStackTrace();

		}
		//start the game based on this information
		gamestart();
	}

	
	public void gamestart() 
	{
		newDeck.shuffleDeck();
		currentDeck = newDeck;
		createPlayers();
		dealCards();
	}

	public static void setActivePlayer()
	{
		if (newRound == null) // if new game
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

	public void run()

	{
		if (remainingPlayers > 1)
		{
			setActivePlayer(); // set deciding player
			newRound = new Round(listOfPlayers, activePlayer);
			playRound();
			updatePlayers();
		}

		newRound.getWinner();
		showWinner();

	}
	
	public void playRound() 
	{	
		System.out.println("ROUND NUMBER " + (roundCount)); 
		String a = "The active player is " + activePlayer.getName().toUpperCase();

		setCategory();
		newRound.compareCards();
		newRound.setWinner();  
	} 
	
	private void chooseCategory () 
	{	
		this.category= getCategoryNumber();
		System.err.println(category);	
	}
	
	private void setCategory()
	{
		// player chooses category
		if (activePlayer.isHuman()) 
		{
			chooseCategory(); // player chooses category
		}

		// auto-picks category
		else 
		{	
			newRound.findBestCategory();  		
		}
	}
	
	private static void updatePlayers()
	{
		for (int i = 0; i < listOfPlayers.size(); i++)

		{
			Player p = listOfPlayers.get(i);

			if (p.isInGame() && p.getHand().size() < 1)

			{
				p.setStatus(false);
				remainingPlayers--;
			}
		}
	}

	private static void showWinner()
	{
		// if only one player is left with cards after a draw
		// they automatically become the winner
		if (newRound.isDraw()) 
		{
			for (int i = 0; i < listOfPlayers.size(); i++) 
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

	public static void dealCards() 
	{
		int numCardsEach = currentDeck.getNumberOfCards() / numberOfPlayers;
		int i;
		for (i = 0; i < numberOfPlayers; i++) 
		{
			ArrayList<Card> cardsForEachPlayer = new ArrayList<Card>(
					currentDeck.getDeck().subList(0, numCardsEach));
			
			playername = listOfPlayers.get(i).getName();
			listOfPlayers.get(i).receiveCards(cardsForEachPlayer);
			currentDeck.getDeck().removeAll(cardsForEachPlayer);
		}

		if (!currentDeck.getDeck().isEmpty()) { // if cards remaining in deck

			listOfPlayers.get(pickRandomPlayer()).receiveExtraCards(
					currentDeck.getDeck());
		}
		System.out.println("Dealing cards...");
		System.out.println();
	}

	private static int pickRandomPlayer() 
	{ // returns random index number
		int randomIndex = (int) Math.floor(Math.random() * numberOfPlayers);
		return randomIndex;
	}

	public void createPlayers() 
	{
     	// numberOfPlayers = currentDeck.getNumPlayers();
		listOfPlayers = new ArrayList<Player>();
		String username = "player";
		int i = 0;
		// creates the human player
		player = new Player(username);
		player.setHuman();
		listOfPlayers.add(player);
		// create AI players
		for (i = 1; i < numberOfPlayers; i++)
		{
			Player p = new Player("AI_Player" + i);
			listOfPlayers.add(p);
		}
	}

	// API Methods 
	
	@GET
	@Path("/newgame")
	public void newGame() throws IOException
	{
		//get new game from game folder
		Game game = new Game();
		Deck currentDeck = new Deck();
		
		//start the game by getting number of players and dealing cards
		game.playGame(currentDeck);
	}
	@GET
	@Path("/ga")
	public String game() throws IOException 
	{
		run();
		String aa = activePlayer.getName() + "-----------------"
				+ gameWinner.getName() + "-----------------"
				+ newRound.getCate();
		String d = oWriter.writeValueAsString(aa);
		return d;
	}

	@GET
	@Path("/draw")
	public String draw() throws IOException 
	{
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

		String hn = oWriter.writeValueAsString(player.getHand().size());
		return hn;
	}

	@GET
	@Path("/playercard")
	public String playercard() throws IOException {

		String pc = oWriter.writeValueAsString(player.getTopCard());
		return pc;
	}

	@GET
	@Path("/sca")

	public int sca(@QueryParam("num") int a) throws IOException {
		
		
		return a;
	}

	public void setCategory(int c) throws IOException 
	{
	
		this.category = sca(c);
	}

	public int getCategoryNumber() 
	{
		return category;
	}
	
	//Get the number of games played from database for statistic screen
	@GET
	@Path("/numGames")
	public int numberOfGames()
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




//GAMESCREEN:

<html>

<head>
	<!-- Web page title -->
	<title>Top Trumps</title>
	
	<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/flick/jquery-ui.css">

	<!-- Optional Styling of the Website, for the demo I used Bootstrap (see https://getbootstrap.com/docs/4.0/getting-started/introduction/) -->
	<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/TREC_IS/bootstrap.min.css">
	<script src="http://dcs.gla.ac.uk/~richardm/vex.combined.min.js"></script>
	<script>vex.defaultOptions.className = 'vex-theme-os';</script>
	<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex.css"/>
	<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex-theme-os.css"/>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
	
	<!--link href="../../resources/css/gameview.css" rel="stylesheet" />-->

<style>

	.nav li {
 		color: black;
   		display: inline-block;
	 	 }
	 	 
	    .navbar-default .navbar-nav > li > a {
		 	color: black;	
	}
	
    .navbar-default {
			background-color: black;
			border-color: #E7E7E7;
		 	padding-left:10px;
			padding-right:10px;
			margin-left:0px;
			margin-right:0px;
			margin-bottom:20px;
	}

	.column{
		position:absolute;
		left: 0px;
		margin-top:150px;
		margin-left:350px;
		margin-right:150px;
	}
	
	.button{
		position:absolute;
		left: 10px;
		margin-top:500px;
	}
	
	.color {
		color:White;
		font-size:1.2em;
			font-weight: bold;	
		}
	
		h5,li,ol,strong{
		margin:0;
		padding:0;
	}
		
	body{
		background-image: url("http://kb4images.com/images/pictures-of-outer-space/37759075-pictures-of-outer-space.jpg");
		margin: auto;
		background-repeat: no-repeat;
		background-size: 100% 100%;
	}

	.infoDisplay{
		color:White;
		font-size:1.2em;
	 	font-weight: bold;
		position:absolute;
		left: 0px;
		margin-top:10px;
		margin-left:10px;
		margin-right:150px;
		max-width: 400px; 
		height: 15rem;
	}

</style>

<body onload="initalize()"> <!-- Call the initalize method when the page loads --> 

 <nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
     <li class="pull-left"><a href="http://localhost:7777/toptrumps/" class="color">Top Trumps</a></li>
     <li class="pull-left"><a href="http://localhost:7777/toptrumps/game" class="color">Play Game</a</li>	
     <li class="pull-left"><a href="http://localhost:7777/toptrumps/stats" class="color">Statistics</a></li>
    </div>
  </div>
</nav> 

<div class = "container">
<div class="infoDisplay">
		<div class="card-block" style="width:550px; height:50px; position:absolute; left:10px; top:30px ">
		<p id="head"><h4>Last Round Active Player:--------Round Winner-------Category Selected</h4></p><br>
		<p id="act"><h4></h4></p>
		<p id="draw"></p>
		<p id="com"></p>
	</div>
</div>
						
<div id="cate" style="display: none;">	
	<li><strong>Please choose one category</strong><br>
    <button type="submit" id="nu" value="0" onclick="sca(0)" style="width:120px;height:30px; font-size:1.4em; font-family: Arial; font-weight: bold;">Size</button><br>
    <button type="submit" id="nu" value="1" onclick="sca(1)" style="width:120px;height:30px; font-size:1.4em; font-family: Arial; font-weight: bold;">Speed</button><br>
    <button type="submit" id="nu" value="2" onclick="sca(2)" style="width:120px;height:30px; font-size:1.4em; font-family: Arial; font-weight: bold;">Range</button><br>
    <button type="submit" id="nu" value="3" onclick="sca(3)" style="width:120px;height:30px; font-size:1.4em; font-family: Arial; font-weight: bold;">Firepower</button><br>
    <button type="submit" id="nu" value="4" onclick="sca(4)" style="width:120px;height:30px; font-size:1.4em; font-family: Arial; font-weight: bold;">Cargo</button><br>
</div>

					
	<div class="main">
		<div class="card border-dark mb-3" style="width:150px; height:280px; position:absolute; left:600px; top:275px ">  		
		<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image"><br />
			<div class="category">
				<p> Description 
				Size 
				Speed 
				Range 
				Firepower 
				Cargo</p>
				<p id="card"></p>
       		</div>
    </div>
                           	
    <div class = "player">                	
    	<div style="left: 370px; position: absolute; top:260px;"> <font size="16";font face="Impact" color="White">Player</font></div>
    	<div id="na" style="left: 370px; position: absolute; top: 275px;"> <font size=12px; font  face="Impact"; color="White"></font></div>
    	<div class="aa" style="left: 370px; position: absolute; top: 355px;"> <font size=5px; font  face="Arial" ; color="White"><p id="handnum">Card in Hands: </p></font></div>
   		<div style="left: 370px; position: absolute; top: 425px;"> <font size=5px; font  face="Arial" ; color="White">Round wins: </font></div>
    </div>
                        
    <div class="main1">                      		
		<img src="http://www.hearthstonetopdecks.com/wp-content/uploads/2014/06/card-back-gold-open-202x300.png"; style="width:140px; height:250px; position:absolute; right:480px; top:100px">
		<div style="left: 800px; position: absolute; top:100px;"> <font size="16";font  face="Impact" color="White">AI1</font></div>
       	<div style="left: 800px; position: absolute; top: 180px;"> <font size=5px; font  face="Arial" ; color="White">Card in Hands: </font></div>
		<div style="left: 800px; position: absolute; top: 250px;"> <font size=5px; font  face="Arial" ; color="White">Round wins: </font></div>
     </div>
                        
     <div class="main2">
     	<img src="http://www.hearthstonetopdecks.com/wp-content/uploads/2014/06/card-back-gold-open-202x300.png"; style="width:140px; height:250px; position:absolute; right:20px; top:100px ">
        <div style="left: 1250px; position: absolute; top: 100px;"> <font size="16";font  face="Impact" color="White">AI2</font></div>
		<div style="left: 1250px; position: absolute; top: 180px;"> <font size=5px; font  face="Arial" ; color="White">Card in Hands: </font></div>
		<div style="left: 1250px; position: absolute; top: 250px;"> <font size=5px; font  face="Arial" ; color="White">Round wins: </font></div>
    </div>
                        
    <div class="main3">
    	<img src="http://www.hearthstonetopdecks.com/wp-content/uploads/2014/06/card-back-gold-open-202x300.png"; style="width:140px; height:250px; position:absolute; right:480px; top:500px ">
        <div style="left: 800px; position: absolute; top: 500px;"> <font size="16";font  face="Impact" color="White">AI3</font></div>
        <div style="left: 800px; position: absolute; top: 580px;"> <font size=5px; font  face="Arial" ; color="White">Card in Hands: </font></div>
		<div style="left: 800px; position: absolute; top: 650px;"> <font size=5px; font  face="Arial" ; color="White">Round wins: </font></div>
    </div>
    
    <div class="main4">
       <img src="http://www.hearthstonetopdecks.com/wp-content/uploads/2014/06/card-back-gold-open-202x300.png"; style="width:140px; height:250px; position:absolute; right:20px; top:500px; ">
       <div style="left: 1250px; position: absolute; top: 500px;"> <font size="16";font  face="Impact" color="White">AI4</font></div>
       <div style="left: 1250px; position: absolute; top: 580px;"> <font size=5px; font  face="Arial" ; color="White">Card in Hands: </font></div>
	   <div style="left: 1250px; position: absolute; top: 650px;"> <font size=5px; font  face="Arial" ; color="White">Round wins: </font></div>
  </div>
                        
  <div class="btn btn-primary">
  		<div id="newg" style="display: none; left: 300px; position: absolute; top: 700px"><a href="http://localhost:7777/toptrumps"><button style="width:200px;height:60px; font-size:1.4em; font-family: Arial; font-weight: bold;">New Game</button></a></div>
    	<div style="left: 300px; position: absolute; top: 800px"><a href="http://localhost:7777/toptrumps/stats"><button style="width:200px;height:60px; font-size:1.4em; font-family: Arial; font-weight: bold;">Statistics</button></a></div>
        <div style="left: 10px; position: absolute; top: 800px"><a href="http://localhost:7777/toptrumps/game"><button style="width:200px;height:60px; font-size:1.4em; font-family: Arial; font-weight: bold;">Save</button></a></div>
		<div style="left: 10px; position: absolute; top: 700px"><form><button type="submit" name="button" value="button1" style="width:200px;height:60px; font-size:1.4em; font-family: Arial; font-weight: bold;">Next Round</button></form></div>		
  </div>		
	</div>

	<script type="text/javascript">
	
		// Method that is called on page load
		function initalize() 
		{
			ga();
			playercard();
			handnum();
			draw();
			com();
			sca();
		}	
			// --------------------------------------------------------------------------
			// You can call other methods you want to run when the page first loads here
			// --------------------------------------------------------------------------
		
		// This is a reusable method for creating a CORS request. Do not edit this.
		function createCORSRequest(method, url) {
				var xhr = new XMLHttpRequest();
				if ("withCredentials" in xhr) {
				// Check if the XMLHttpRequest object has a "withCredentials" property.
				// "withCredentials" only exists on XMLHTTPRequest2 objects.
				xhr.open(method, url, true);
				} else if (typeof XDomainRequest != "undefined") {
				// Otherwise, check if XDomainRequest.
				// XDomainRequest only exists in IE, and is IE's way of making CORS requests.
				xhr = new XDomainRequest();
				xhr.open(method, url);
				 } else {
				// Otherwise, CORS is not supported by the browser.
				xhr = null;
				 }
				 return xhr;
		}
	</script>
		
			
		<script type="text/javascript">
		
		function ga(){
		var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/ga");
		if (!xhr) {
					alert("CORS not supported");
			}

			xhr.onload = function(e) {
		
					var n =xhr.response; 
					if (n.indexOf('player') > -1){
						
						document.getElementById('act').innerHTML = n;
					
						document.getElementById("cate").style.display="";

					}
				else{
				document.getElementById('act').innerHTML = n;
				document.getElementById("cate").style.display="none";
				}
			};

			xhr.send();	
		}
		
	
		
		function handnum(){
		var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/handnum");
		if (!xhr) {
					alert("CORS not supported");
			}

			xhr.onload = function(e) {
		
					var n =xhr.response; 
					var y=parseInt(n);
					if(y===0){
					alert("You Loss!");
					document.getElementById("newg").style.display="";
					}
					else if(y===40){
					alert("You Win!");
					document.getElementById("newg").style.display="";
					}
				else{
				document.getElementById('handnum').innerHTML = "Cards in Hand:         "+n;
				}
			};

			xhr.send();	
		
		}
		
		function draw(){
		var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/draw");
		if (!xhr) {
					alert("CORS not supported");
			}

			xhr.onload = function(e) {
		
					var n =xhr.response; 
					if (n.indexOf("false")>-1){
						document.getElementById('draw').innerHTML = "Not a draw";
					}
				else{
				document.getElementById('draw').innerHTML = "It's a draw";
				alert("Draw");
				}
			};

			xhr.send();	
		}
		
		function sca(i){
		
		var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/sca?num="+i);
		if (!xhr) {
					alert("CORS not supported");
			}

			xhr.onload = function(e) {
					var responseText = xhr.response; // the text of the response
									
			};
			
			xhr.send();		
		}
	
		
		
		function com(){
		var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/com");
		if (!xhr) {
					alert("CORS not supported");
			}

			xhr.onload = function(e) {
		
					var n =xhr.response; 
				document.getElementById('com').innerHTML = "Communal pile has "+n+" cards";
			};

			xhr.send();	
		
		}
		
		function playercard(){
		var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/playercard");
		if (!xhr) {
					alert("CORS not supported");
			}

			xhr.onload = function(e) {
		
					var n =xhr.response; 
				document.getElementById('card').innerHTML = n;
			};

			xhr.send();	
		
		}
		
		

	</script>	
	</body>
</html>