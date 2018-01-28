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
	
	.color {
	color:White;
	font-size:1.2em;
 	font-weight: bold;	
 	}
			
	body{
	background-image: url("http://kb4images.com/images/pictures-of-outer-space/37759075-pictures-of-outer-space.jpg")
	}
	
	.text{
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
	
	  	<div class="container">
	  		<div class="text">
  					<div class="card-block">
    					<center>
    						<h4><b>Active Player:</b></h4><br />
    						<h4><b>Winner of last round:</b></h4><br />
    						<h4><b>Category Selected:</b></h4><br />
						</center>
  					</div>
				</div>
			</div>
		
  							<div class="main">
  								<img src="http://www.hearthstonetopdecks.com/wp-content/uploads/2014/06/card-back-gold-open-202x300.png";  style="width:140px; height:250px; position:absolute; left:400px; top:275px; ">
                               
                                <div style="left: 170px; position: absolute; top: 275px;"> <font size=12px; font  face="Impact"; color="White">Player</font></div>
                                <div style="left: 170px; position: absolute; top: 355px;"> <font size=5px; font  face="Arial" ; color="White">Card in Hands: </font></div>
                                <div style="left: 170px; position: absolute; top: 425px;"> <font size=5px; font  face="Arial" ; color="White">Round wins: </font></div>
                            </div>
                            
                            <div class="main1">
                           <img src="http://www.hearthstonetopdecks.com/wp-content/uploads/2014/06/card-back-gold-open-202x300.png"; style="width:140px; height:250px; position:absolute; right:600px; top:50px; ">
                            	
                                <div style="left: 600px; position: absolute; top: 50px;"> <font size="16";font  face="Impact" color="White">AI1</font></div>
                                <div style="left: 600px; position: absolute; top: 130px;"> <font size=5px; font  face="Arial" ; color="White">Card in Hands: </font></div>
								<div style="left: 600px; position: absolute; top: 200px;"> <font size=5px; font  face="Arial" ; color="White">Round wins: </font></div>
                            </div>
                            
                           <div class="main2">
                            <img src="http://www.hearthstonetopdecks.com/wp-content/uploads/2014/06/card-back-gold-open-202x300.png"; style="width:140px; height:250px; position:absolute; right:20px; top:50px; ">
                            
                                <div style="left: 1150px; position: absolute; top: 50px;"> <font size="16";font  face="Impact" color="White">AI2</font></div>
								<div style="left: 1150px; position: absolute; top: 130px;"> <font size=5px; font  face="Arial" ; color="White">Card in Hands: </font></div>
								<div style="left: 1150px; position: absolute; top: 200px;"> <font size=5px; font  face="Arial" ; color="White">Round wins: </font></div>
                            </div>
                            
                            <div class="main3">
                             <img src="http://www.hearthstonetopdecks.com/wp-content/uploads/2014/06/card-back-gold-open-202x300.png"; style="width:140px; height:250px; position:absolute; right:600px; top:500px; ">
                            <div style="left: 600px; position: absolute; top: 500px;"> <font size="16";font  face="Impact" color="White">AI3</font></div>
                            <div style="left: 600px; position: absolute; top: 580px;"> <font size=5px; font  face="Arial" ; color="White">Card in Hands: </font></div>
								<div style="left: 600px; position: absolute; top: 650px;"> <font size=5px; font  face="Arial" ; color="White">Round wins: </font></div>
                            </div>
                            
                            <div class="main4">
                  			 <img src="http://www.hearthstonetopdecks.com/wp-content/uploads/2014/06/card-back-gold-open-202x300.png"; style="width:140px; height:250px; position:absolute; right:20px; top:500px; ">
                               <div style="left: 1150px; position: absolute; top: 500px;"> <font size="16";font  face="Impact" color="White">AI4</font></div>
                            <div style="left: 1150px; position: absolute; top: 580px;"> <font size=5px; font  face="Arial" ; color="White">Card in Hands: </font></div>
								<div style="left: 1150px; position: absolute; top: 650px;"> <font size=5px; font  face="Arial" ; color="White">Round wins: </font></div>
                            </div>
                            
                            <div class="btn btn-primary">
                            <div style="left: 10px; position: absolute; top: 700px"><a href="http://localhost:7777/toptrumps/game"><button style="width:200px;height:60px; font-size:1.4em; font-family: Arial; font-weight: bold;">New Game</button></a></div>
                            <div style="left: 300px; position: absolute; top: 700px"><a href="http://localhost:7777/toptrumps/stats"><button style="width:200px;height:60px; font-size:1.4em; font-family: Arial; font-weight: bold;">Statistics</button></a></div>
                   
						</div>
			
	 <div class="container">

	  <div class="frame">
		<div class="card border-dark mb-3" style="max-width: 10rem; height: 20rem;">  		
		<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  		<br />
  		<div class="carddisplay">
    		
    		<br />
    		<div class="category">
    		<p id="paragraphWhereMyListWillGo"></p> 
    		<div id="topcard"></div> 
    		</div>	
  		</div>
		</div>
		</div>

		<script type="text/javascript">
		
			// Method that is called on page load
			function initalize() {
			cateJSONList();
			//document.getElementById('paragraphWhereMyListWillGo').innerHTML = cateJSONList;
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
			
			function cateJSONList() {

				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/cateJSONList"); // Request type and URL

				if (!xhr) {
  					alert("CORS not supported");
				}

				xhr.onload = function(e) {
			
 					var Text =xhr.response; 
					document.getElementById('paragraphWhereMyListWillGo').innerHTML = Text;
				};

				xhr.send();		
			}
			
			</script>
			<script type="text/javascript">
			function topcard() {

				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/topcard"); // Request type and URL

				if (!xhr) {
  					alert("CORS not supported");
				}

				xhr.onload = function(e) {
			
 					var t =xhr.response; 
					document.getElementById('topcard').innerHTML = t;
				};

				xhr.send();		
			}
		</script>	
		</body>
</html>