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

		<!-- link rel="../../resources/css/gameview.css" rel="stylesheet" /> -->

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
			background-image: url("http://kb4images.com/images/pictures-of-outer-space/37759075-pictures-of-outer-space.jpg");	
			}
			
		h1{
			color: White
		}
		
		background-image{
						opacity: 0.5;
						}
			
		.numGames{
		position:absolute;
		left: 0px;
		margin-left:150px;
		margin-right:150px;
		}
		
		.computerWin{
		position:absolute;
		right: 0px;
		margin-left:150px;
		margin-right:150px;
		}
		
		.playerWin{
		position: absolute;
        top: 37%;
        left: 50%;
        margin-right: -50%;
       	transform: translate(-50%, -50%)
			}
		
		.draws{
		position:absolute;
		left: 0px;
		margin-top: 400px;
		margin-left:400px;
		margin-right:150px;
		
		}
		
		.rounds{
		position:absolute;
		right: 0px;
		margin-top: 400px;
		margin-left:150px;
		margin-right:400px;
		}

	
		
		.left{
		position: left;
		}
		
		.right{
			position: right;
			}
	</style
	
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
	 	  	
    	<div class="container">
    	<center><h1><b>Past Statistics</b></h1></center>
    	
			<!-- Add your HTML Here -->
			
		<div class="numGames">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 23rem;">  		
		<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  		<br />
  		<div class="card-block">
    		<center><h4><b>Total Number of Games:</b></h4>
    		<br />
    		<p class="card-text">Link from db</p>
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
    		<p class="card-text">Link from db</p>
    		<br />
  		</div>
		</div>
		</div>
		
		<div class="playerWin">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 23rem;">  		
		<img class="card-img-top" src="https://i.kinja-img.com/gawker-media/image/upload/t_original/othklnrkmmln2e8vv3ug.jpg" alt="Card Image">
  		<br />
  		<div class="card-block">
    		<center><h4><b>Number of Games Player Has Won:</b></h4>
    		<br />
    		<p class="card-text">Link from db</p>
    		<br />
  		</div>
		</div>
		</div>
		
		<div class="draws">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 23rem;">  		
		<img class="card-img-top" src="https://i.kinja-img.com/gawker-media/image/upload/t_original/othklnrkmmln2e8vv3ug.jpg" alt="Card Image">
  		<br />
  		<div class="card-block">
    		<center><h4><b>Average Number of Draws:</b></h4>
    		<br />
    		<p class="card-text">Link from db</p>
    		<br />
  		</div>
		</div>
		</div>
		
		<div class="rounds">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 23rem;">  		
		<img class="card-img-top" src="https://i.kinja-img.com/gawker-media/image/upload/t_original/othklnrkmmln2e8vv3ug.jpg" alt="Card Image">
  		<br />
  		<div class="card-block">
    		<center><h4><b>Highest Number of Rounds in a Single Game:</b></h4>
    		<br />
    		<p class="card-text">Link from db</p>
    		<br />
  		</div>
		</div>
		</div>
    			
    		<a href="http://localhost:7777/toptrumps/game" class="btn btn-primary">Play Game</a></center>
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
				helloJSONList();
				helloWord("Student");
				
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
		
		</script>
		
		<!-- Here are examples of how to call REST API Methods -->
		<script type="text/javascript">
		
			// This calls the helloJSONList REST method from TopTrumpsRESTAPI
			function helloJSONList() {
			
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloJSONList"); // Request type and URL
				
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					alert(responseText); // lets produce an alert
				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();		
			}
			
			// This calls the helloJSONList REST method from TopTrumpsRESTAPI
			function helloWord(word) {
			
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloWord?Word="+word); // Request type and URL+parameters
				
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					alert(responseText); // lets produce an alert
				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();		
			}

		</script>
		
		</body>
</html>