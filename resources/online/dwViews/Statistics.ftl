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

	</head>
	
	<style>
		.navbar-default {
	   		background-color: black;
	    	border-color: #E7E7E7;
	    	padding-left:10px;
	  		padding-right:10px;
	  		margin-left:0px;
	  		margin-right:0px;
	  		margin-bottom:20px;
		}
	
		.color {
			color:White;
			font-size:1.2em;
	 		font-weight: bold;	
	 	}
		
		.nav{
			padding-left:10px;
	  		padding-right:10px;
	  		margin-left:0px;
	  		margin-right:0px;
	  		margin-bottom:20px;
		}
				
		body{
			margin: auto;
			background-repeat: no-repeat;
			background-size: 100% 100%;	
			background-image: url("http://kb4images.com/images/pictures-of-outer-space/37759075-pictures-of-outer-space.jpg");
		}
				
		h1{
			color: White
		}
		
		.numGames{
		float: left;
		margin-left: 200px;
		margin-top:100px;
		}
	
		.computerWin{
		float: left;
		margin-left:100px;
		margin-top:100px;
		
		}
	
		.playerWin{
		float: left;
		margin-left:100px;
		margin-top:100px;
	
		}
	
	.draws{
		float: left;
		margin-left:100px;
		margin-top:100px;
		
	}
	
	.rounds{
		float: left;
		margin-left:100px;
		margin-top:100px;

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
	 	  	
	<center><h1><b>Past Statistics</b></h1></center>
    				
	<div class="numGames">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 23rem;">  		
			<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  			<br />
  			<div class="card-block">
    			<center><h4><b>Total Number of Games:</b></h4>
    			<br />
    			<p id="numberOfGames">Games</p>
    			<br />
  			</div>
		</div>
	</div>
		
	<div class="computerWin">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 23rem;;">  		
			<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  			<br />
  			<div class="card-block">
    			<center><h4><b>Number of Times Computer Has Won:</b></h4>
    			<br />
    			<p id ="timesComputerWon"><Computer</p>
    			<br />
  			</div>
		</div>
	</div>
		
	<div class="playerWin">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 23rem;">  		
			<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  			<br />
  			<div class="card-block">
    			<center><h4><b>Number of Games Player Has Won:</b></h4>
    			<br />
    			<p id = "humanWin">Human Win</p>
    			<br />
  			</div>
		</div>
	</div>
		
	<div class="draws">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 23rem;">  		
			<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  			<br />
  			<div class="card-block">
    			<center><h4><b>Average Number of Draws:</b></h4>
    			<br />
    			<p id = "numDraws">Num Draws</p>
    			<br />
  			</div>
		</div>
	</div>
		
	<div class="rounds">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 23rem;">  		
			<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  			<br />
  			<div class="card-block">
    			<center><h4><b>Total Number of Rounds:</b></h4>
    			<br />
    			<p id="numberOfRounds">Rounds</p>
    			<br />
  			</div>
		</div>
	</div>
    			
  		</div>
		
		<script type="text/javascript">
		
			// Method that is called on page load
			function initalize() {
				// --------------------------------------------------------------------------
				// You can call other methods you want to run when the page first loads here
				// --------------------------------------------------------------------------
				
				// For example, lets call our sample methods
				numberOfGames();
				timesComputerWon();
				timesPersonWon();
				numDraws();
				numRounds();
				
			}
			
			// -----------------------------------------
			// Add your other Javascript methods Here
			// -----------------------------------------
		
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
		
		
		<!-- Here are examples of how to call REST API Methods -->
		
			// This calls the helloJSONList REST method from TopTrumpsRESTAPI
			function numberOfGames() {
			
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/numGames"); // Request type and URL
				
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					document.getElementById('numberOfGames').innerHTML=responseText;
				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();		
			}
			
			function timesComputerWon()
			{
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/timescomputerwon");
				
				if (!xhr)
				{
					alert("CORS not supported");
				}
				
				xhr.onload = function(e)
				{
					var compWinResponse = xhr.response;
					document.getElementById('timesComputerWon').innerHTML= compWinResponse;
				};
				
				xhr.send();
			}
			
				function timesPersonWon() {
			
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/humanwin"); // Request type and URL
				
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					document.getElementById('humanWin').innerHTML=responseText;
				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();		
			}
			
			function numDraws() {
			
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/numDraws"); // Request type and URL
				
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var numDraws = xhr.response; // the text of the response
					document.getElementById('numDraws').innerHTML=numDraws;
				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();		
			}
			
				function numRounds() {
			
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/numRounds"); // Request type and URL
				
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var roundsResponseText = xhr.response; // the text of the response
					document.getElementById('numberOfRounds').innerHTML=roundsResponseText;
				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();		
			}
			</script>
		
		
		</body>
</html>