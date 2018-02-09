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
	
	.player{
		float: left;
		margin-top: 100px;
		margin-left: 450px;
	}
	
	.aione{
		float: left;
		margin-top: 100px;		
		margin-left:50px;
	}
	
	.aitwo{
		float: left;
		margin-top: 100px;	
		margin-left:50px;
		
	}
	
	.AI3{
		margin-top: 100px;	
		float: left;
		margin-left:50px;
		
	}
	
	.AI4{
		margin-top: 100px;	
		float: left;
		margin-left:50px;

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

	<div class="player">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 23rem;">  		
			<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  			<br />
  			<div class="card-block">
    			<center><h4><b><p id = "cardDescription"></p></b></h4>
    			<br />
    			<p id="cardCategories"></p>
    			<br />
  			</div>
		</div>
	</div>

	<div class="aione">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 23rem;">  		
			<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  			<br />
  			<div class="card-block">
    			<center><h4><b><p id = "cardDescription"></p></b></h4>
    			<br />
    			<p id="cardCategories"></p>
    			<br />
  			</div>
		</div>
	</div>

	<div class="aitwo">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 23rem;">  		
			<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  			<br />
  			<div class="card-block">
    			<center><h4><b><p id = "cardDescription"></p></b></h4>
    			<br />
    			<p id="cardCategories"></p>
    			<br />
  			</div>
		</div>
	</div>		
 
 	<div class="AI3">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 23rem;">  		
			<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  			<br />
  			<div class="card-block">
    			<center><h4><b><p id = "cardDescription"></p></b></h4>
    			<br />
    			<p id="cardCategories"></p>
    			<br />
  			</div>
		</div>
	</div>    

	<div class="AI4">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 23rem;">  		
			<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  			<br />
  			<div class="card-block">
    			<center><h4><b><p id = "cardDescription"></p></b></h4>
    			<br />
    			<p id="cardCategories"></p>
    			<br />
  			</div>
		</div>
	</div>	                   
  <div class="btn btn-primary">
  		<div style="left: 300px; position: absolute; top: 700px"><a href="http://localhost:7777/toptrumps"><button style="width:200px;height:60px; font-size:1.4em; font-family: Arial; font-weight: bold;">New Game</button></a></div>
    	<div style="left: 300px; position: absolute; top: 800px"><a href="http://localhost:7777/toptrumps/stats"><button style="width:200px;height:60px; font-size:1.4em; font-family: Arial; font-weight: bold;">Statistics</button></a></div>
        <div style="left: 10px; position: absolute; top: 800px"><a href="http://localhost:7777/toptrumps/game"><button style="width:200px;height:60px; font-size:1.4em; font-family: Arial; font-weight: bold;">Save</button></a></div>
		<div style="left: 10px; position: absolute; top: 700px"><<a href="http://localhost:7777/toptrumps/nextround"><button style="width:200px;height:60px; font-size:1.4em; font-family: Arial; font-weight: bold;">Next Round</button></form></div>		
  </div>		
	</div>

	<script type="text/javascript">
	
		// Method that is called on page load
		function initalize() 
		{
			newGame();
			nextRound();
			saveAndQuit();
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
		
		function newGame() 
		{
		
			// First create a CORS request, this is the message we are going to send (a get request in this case)
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/newgame"); // Request type and URL
			
			// Message is not sent yet, but we can check that the browser supports CORS
			if (!xhr) {
					alert("CORS not supported");
			}

			// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
			// to do when the response arrives 
			xhr.onload = function(e) {
					var roundsResponseText = xhr.response; // the text of the response
				document.getElementById('newGame').innerHTML=roundsResponseText;
			};
			
			// We have done everything we need to prepare the CORS request, so send it
			xhr.send();		
		}
		
		function nextRound()
		{
			// First create a CORS request, this is the message we are going to send (a get request in this case)
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/nextround"); // Request type and URL
			
			// Message is not sent yet, but we can check that the browser supports CORS
			if (!xhr) {
					alert("CORS not supported");
			}

			// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
			// to do when the response arrives 
			xhr.onload = function(e) {
					var roundsResponseText = xhr.response; // the text of the response
				document.getElementById('nextround').innerHTML=roundsResponseText;
			};
			
			// We have done everything we need to prepare the CORS request, so send it
			xhr.send();		
		}


	</script>	
	</body>
</html>