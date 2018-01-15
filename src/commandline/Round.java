package commandline;

public class Round {
	
	/**
	 * instance variables
	 */
	
	private Player activePlayer;
	private int remainingPlayers;
	
	private static int roundCount = 1;
	
	/**
	 * * constructor method 
	 */
	
	public Round (int r)
	
	{
		remainingPlayers = r;
		activePlayer = getActivePlayer(remainingPlayers);
	}
	
	
	
	
	/**
	 * algorithm finds the active player
	 */
	
	private Player getActivePlayer(int r)
	
	{
		// algorithm
		
		if (roundCount == 1)
			
				{
			
			// return a random integer
			// selects the player to start the game
			
			return activePlayer;
			
				}
		else 
		
		return activePlayer;
	}
	
	/**
	 * active player chooses category
	 * @return
	 */
	
	private int chooseCategory ()
	
	{
		return 0;
	}
	
	/**
	 * methods: 
	 * 
	 * 1. compare method 
	 * 2. draw method 
	 */
	
	private int compareTo() 
	
	{
		// run algorithm
		
		/**
		 * winner index = x; 
		 * 
		 * if draw
		 * 
		 * then 
		 * 
		 * x = drawMethod();
		 * 
		 * round ++;
		 * return int x.;
		 */
		
		return 0; 
	}


	private static void roundCounter() {
		
		System.out.println();
		System.out.println("The round number is " + roundCount);
		System.out.println();
		
	}
}