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
	
	<!-- Haven't been able to get CSS or a BASE HTML properly referenced. HOW to do this so we don't repeat so much? --> 
	<style>
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
		.column2{
		position:absolute;
		right: 0px;
		margin-left:150px;
		margin-right:150px;
		
		}
		.column{
		position:absolute;
		left: 0px;
		margin-left:150px;
		margin-right:150px;
		}
	</style
	
    <body onload="initalize()"> <!-- Call the initalize method when the page loads -->
    	<div class = "nav">
    	<nav class="navbar navbar-inverse navbar-fixed-top">
	      	<div class="container">
	        	<div class="navbar-header">
	          		<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
			            <span class="sr-only">Toggle navigation</span>
			            <span class="icon-bar"></span>
			            <span class="icon-bar"></span>
			            <span class="icon-bar"></span>
	          		</button>
	          	<a class="navbar-brand" href="http://localhost:7777/toptrumps/">Top Trumps</a>
	          	<ul>
	          		<li class="nav-item">
              			<a class="nav-link" href="http://localhost:7777/toptrumps/stats">Statistics</a>
            		</li>
	          			
	        </div>
	        <div id="navbar" class="collapse navbar-collapse">
	       </div><!--/.nav-collapse -->
	      </div>
	    </nav>
	  </div>
	  
	  <div class="container">
	  	  <center><h1><b>Please make a selection</b></h1></center>

	  <div class="column">
		<div class="card border-dark mb-3" style="max-width: 18rem; height: 50rem;">  		
		<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  		<div class="card-block">
    		<h4 class="card-title">Play Game</h4>
    		<p class="card-text">If you would like to play the game select the button below.</p>
    		<a href="http://localhost:7777/toptrumps/game" class="btn btn-primary">Play Game</a>
  		</div>
		</div>
		</div>

		<div class="column2">
		<div class="card border-dark mb-3" style="max-width: 18rem; height: 50rem;"> 
  		<img class="card-img-top" src="https://cdn.spacetelescope.org/archives/images/wallpaper2/heic1509a.jpg" alt="Card image">
  		<div class="card-block">
    		<center><b><h4 class="card-title">View Statistics</h4></b></center>
    		<p class="card-text">If you would like to view the statistics press the button below.</p>
    		<a href="http://localhost:7777/toptrumps/stats" class="btn btn-primary">View Statistics</a>
  		</div>
		</div>
		</div>
    	
			<!-- Add your HTML Here -->
		
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
			function helloWord(word) 
			{
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