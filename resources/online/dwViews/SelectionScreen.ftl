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

		.stats
		{
			position:absolute;
			right: 0px;
			margin:auto;
			margin-top:150px;
			margin-left:150px;
			margin-right:350px;
			width:auto;
			height:auto;
		}
		
		.playgame
		{
			position:absolute;
			left: 0px;
			margin:auto;
			margin-top:150px;
			margin-left:350px;
			margin-right:150px;
			width:auto;
			height:auto;
		}
		
		.nav li 
		{
      		color: black;
      		display: inline-block;
  	  	}
  	  
  	  	.navbar-default .navbar-nav > li > a 
  	  	{
    		color: black;
		}
	    
	    .navbar-default 
	    {
    		background-color: black;
    		border-color: #E7E7E7;
    		padding-left:10px;
  			padding-right:10px;
  			margin-left:0px;
  			margin-right:0px;
  			margin-bottom:20px;
		}

		.color 
		{
			color:White;
			font-size:1.2em;
 			font-weight: bold;	
 		}
	
		
			
		.btn-primary {
			border-color: #3079ED;
			color: #F3F7FC;
			background-color: #4D90FE;
			background: -webkit-linear-gradient(top, #4D90FE, #4787ED);
			background: -moz-linear-gradient(top, #4D90FE, #4787ED);
			background: linear-gradient(top, #4D90FE, #4787ED);
		}
		
		body{
			background-image: url("http://kb4images.com/images/pictures-of-outer-space/37759075-pictures-of-outer-space.jpg");
			margin: auto;
			background-repeat: no-repeat;
			background-size: 100% 100%;
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
	
	<div class="playgame">
			<div class="card border-dark mb-3" style="max-width: 18rem; height: 27rem;">  		
				<img class="card-img-top" src="https://img.purch.com/h/1000/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzA2Ni8yODcvb3JpZ2luYWwveHMtMS1zcGFjZS1wbGFuZS1ib2Vpbmctb3JiaXRhcnQuanBn" alt="Card Image">
  				<br />
  				<div class="card-block">
    				<center><h4><b>Play Game</b></h4>
    				<br />
    				<p class="card-text">If you would like to play the game select the button below.</p>
    				<br />	
    				<a href="http://localhost:7777/toptrumps/game" class="btn btn-primary">Play Game</a></center>
					</center>    
  				</div>
			</div>
	</div>

		<div class="stats">
			<div class="card border-dark mb-3" style="max-width: 18rem; height: 27rem;"> 
  				<img class="card-img-top" src="https://i.kinja-img.com/gawker-media/image/upload/t_original/othklnrkmmln2e8vv3ug.jpg" alt="Card image" style="height:220px">
  				<br />
  				<div class="card-block">
    				<center><h4><b>View Statistics</b></h4>
    				<br />
    				<p class="card-text">If you would like to view the statistics select the button below.</p>
    				<br />
    				<a href="http://localhost:7777/toptrumps/stats" class="btn btn-primary">View Statistics</a></center>
  				</div>
			</div>
		</div>

		
		<script type="text/javascript">


			function initalize() 
      {
				
			}
			
			
			function createCORSRequest(method, url) 
      {
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
		
		</body>
</html>