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
		margin-left: 250px;
	}
	
	.AI1{
		float: left;
		margin-top: 100px;		
		margin-left:50px;
	}
	
	.AI2{
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
	
	.left{
		float:left;
	}
	
	.topcard-block{
		color:White;
		font-size:1.3em;
		font-weight: bold;
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

	<div class="topcard-block" style="left:10px; top:50px;position:absolute;">   						
    	<p id="activeplayer"><h4></h4></p><br>		
    	<p id="roundwinner"><h4></h4></p><br>
		<p id="draw"></p>
    	<div id="category" style="display: none;">	
        	<li><strong>Please choose one category</strong><br>
        		<button type="submit" id="nu" value="1" onclick="sca(1)" style="width:120px;height:30px; font-size:1.4em; font-family: Arial; font-weight: bold;">Size</button><br>
        		<button type="submit" id="nu" value="2" onclick="sca(2)" style="width:120px;height:30px; font-size:1.4em; font-family: Arial; font-weight: bold;">Speed</button><br>
        		<button type="submit" id="nu" value="3" onclick="sca(3)" style="width:120px;height:30px; font-size:1.4em; font-family: Arial; font-weight: bold;">Range</button><br>
        		<button type="submit" id="nu" value="4" onclick="sca(4)" style="width:120px;height:30px; font-size:1.4em; font-family: Arial; font-weight: bold;">Firepower</button><br>
        		<button type="submit" id="nu" value="5" onclick="sca(5)" style="width:120px;height:30px; font-size:1.4em; font-family: Arial; font-weight: bold;">Cargo</button><br>
        </div>
    </div>

	<div class="player">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 30rem;">  		
			<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  			<br />	
  			<div class="card-block">
    			<center><h4><b>Human</b></h4></center>
  				<div class="left">
          			<p id = "cardDescription"></p></h4>
    				<br />
  					<p id = "playerCard"></p><br>
  					<p id ="cardnum"></p>
    			</div>
  			</div>
		</div>
	</div>

	<div class="AI1">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 30rem;">  		
			<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  			<br />
  			<div class="card-block">
    			<center><h4><b>AI 1</b></h4></center>
    			<p id = "cardCategories2"></p>          
  			</div>
		</div>
	</div>

	<div class="AI2">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 30rem;">  		
			<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  			<br />
  			<div class="card-block">
    			<center><h4><b>AI 2</b></h4></center>			
        		<p id="cardCategories3"></p>
    		</div>
		</div>
	</div>		
 
 	<div class="AI3">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 30rem;">  		
			<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  			<br />
  			<div class="card-block">
				<center><h4><b>AI 3</b></h4></center>  	
       			<p id="cardCategories4"></p>  		    			
  			</div>
		</div>
	</div>    

	<div class="AI4">
		<div class="card border-dark mb-3" style="max-width: 15rem; height: 30rem;">  		
			<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  			<br />
  			<div class="card-block">
				<center><h4><b>AI 4</b></h4></center>		
     			<p id="cardCategories5"></p>	    			
  			</div>
		</div>
	</div>	  
	                 
  	<div class="btn btn-primary">
  		<div style="left: 300px; position: absolute; top: 700px"><button type="submit" value="1" onclick="newg(1)" style="width:200px;height:60px; font-size:1.4em; font-family: Arial; font-weight: bold;"><a href="http://localhost:7777/toptrumps">New Game</a></button></div>
    	<div style="left: 300px; position: absolute; top: 800px"><a href="http://localhost:7777/toptrumps/stats"><button style="width:200px;height:60px; font-size:1.4em; font-family: Arial; font-weight: bold;">Statistics</button></a></div>
        <div style="left: 10px; position: absolute; top: 800px"><a href="http://localhost:7777/toptrumps/game"><button style="width:200px;height:60px; font-size:1.4em; font-family: Arial; font-weight: bold;">Save</button></a></div>
		<div style="left: 10px; position: absolute; top: 700px; "><input type="button" value="Next Round" onClick="window.location.href=window.location.href" style="width:200px;height:60px; font-size:1.4em; font-family: Arial; font-weight: bold;"></div>		
  	</div>		

	<script type="text/javascript">
	
		// Method that is called on page load
		function initalize() 
		{
			newGame();
			cardCategories2();
			cardCategories3();
			cardCategories4();
			cardCategories5();
			activeplayer();
			roundwinner();
			draw();
			cardnum();
			newg();
			category();
		}	
		
			// --------------------------------------------------------------------------
			// You can call other methods you want to run when the page first loads here
			// --------------------------------------------------------------------------
		
		// This is a reusable method for creating a CORS request. Do not edit this.
		function createCORSRequest(method, url) 
		{
			var xhr = new XMLHttpRequest();
			if ("withCredentials" in xhr) 
			{
				// Check if the XMLHttpRequest object has a "withCredentials" property.
				// "withCredentials" only exists on XMLHTTPRequest2 objects.
				xhr.open(method, url, true);
			}
			 
			else if (typeof XDomainRequest != "undefined") 
			{
				// Otherwise, check if XDomainRequest.
				// XDomainRequest only exists in IE, and is IE's way of making CORS requests.
				xhr = new XDomainRequest();
				xhr.open(method, url);
			} 
			
			else 
			{
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
			if (!xhr) 
      		{
  		  		alert("CORS not supported");
			}
			
      		// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
			// to do when the response arrives 
			xhr.onload = function(e) 
      		{
 				var responseText = xhr.response; // the text of the response
				document.getElementById('playerCard').innerHTML=responseText;
			};
				
			// We have done everything we need to prepare the CORS request, so send it
			xhr.send();		
		}
			
		function cardCategories2()
		{
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/cardCategories2");
			
			if (!xhr) 
			{
				alert("CORS not supported");
			}

			xhr.onload = function(e) 
			{
				var n= xhr.response; 
				document.getElementById('cardCategories2').innerHTML=n;
			};

			xhr.send();	
		}
		
		function nextRound()
		{
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/nextround"); // Request type and URL
			if (!xhr) {
					alert("CORS not supported");
			}

			xhr.onload = function(e) {
					var ResponseText = xhr.response; // the text of the response
			
			};

			xhr.send();		
		}
		
		function cardCategories3()
		{
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/cardCategories3");
			if (!xhr) 
			{
				alert("CORS not supported");
			}

			xhr.onload = function(e) 
			{
				var n= xhr.response; // the text of the response
				document.getElementById('cardCategories3').innerHTML=n;
			};

			xhr.send();	
		}
			
		function cardCategories4()
		{
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/cardCategories4");
			if (!xhr) 
			{
				alert("CORS not supported");
			}

			xhr.onload = function(e) 
			{
				var n= xhr.response; // the text of the response
				document.getElementById('cardCategories4').innerHTML=n;
			};

			xhr.send();	
		}
			
		function cardCategories5()
		{
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/cardCategories5");
			if (!xhr) 
			{
				alert("CORS not supported");
			}

			xhr.onload = function(e) 
			{
				var n= xhr.response; // the text of the response
				document.getElementById('cardCategories5').innerHTML=n;
			};

			xhr.send();	
		}
		
		function activeplayer()
		{			
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/activeplayer");
			if (!xhr) 
			{
				alert("CORS not supported");
			}

			xhr.onload = function(e) 
			{
				var n= xhr.response; // the text of the response
				if (n.indexOf('Human') > -1)
				{		
 					document.getElementById('activeplayer').innerHTML="The Active Player is: "+n;
 					document.getElementById("category").style.display="";
 				}
				
				else
				{
					document.getElementById('activeplayer').innerHTML="The Active Player is: "+n;
					document.getElementById("category").style.display="none";
				}
			};

			xhr.send();
		}
			
		function roundwinner()
		{	
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/roundwinner");
			if (!xhr) 
			{
				alert("CORS not supported");
			}

			xhr.onload = function(e) 
			{
				var n= xhr.response; // the text of the response
				document.getElementById('roundwinner').innerHTML="Round Winner is: "+n;
			};

			xhr.send();
		}
			
		function draw()
		{	
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/draw");
			
			if (!xhr) 
			{
				alert("CORS not supported");
			}

			xhr.onload = function(e) 
			{
				var n= xhr.response; // the text of the response
				if (n.indexOf("false")>-1)
				{
 						document.getElementById('draw').innerHTML = "Not a draw";
 				}
				else
				{
					document.getElementById('draw').innerHTML = "It's a draw";
				}
			};

			xhr.send();
		}
			
		function cardnum()
		{
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/cardnum");
				
			if (!xhr) 
			{
				alert("CORS not supported");
			}
				
			xhr.onload = function(e) 
			{
				var n= xhr.response; // the text of the response
				document.getElementById('cardnum').innerHTML = n+" cards left";
			};
			
			xhr.send();
		}
			
		function playagain(i)
		{
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/playagain?num="+i);
			
			if (!xhr) 
			{
				alert("CORS not supported");
			}
				
			xhr.onload = function(e) 
			{
				var n= xhr.response; // the text of the response			
			};
				
			xhr.send();
		}
			
		function category(i)
		{
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/category?num="+i);
				
			if (!xhr) 
			{
				alert("CORS not supported");
			}
				
			xhr.onload = function(e) 
			{
				var n= xhr.response; // the text of the response			
			};
				
			xhr.send();
		s}

	</script>	
	</body>
</html>