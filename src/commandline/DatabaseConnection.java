package commandline;
import java.sql.*;

public class DatabaseConnection 
{
	
	private Connection connection = null;
	
	//Jemma's Details for DB
	private final String DBNAME = "m_17_0806849r"; 
	private final String USERNAME = "m_17_0806849r";
	private final String PASSWORD = "0806849r";
	
	//Constructor makes connection
	public DatabaseConnection()
	{
		this.makeConnection();
	}
	
	//This tries to make a connection, if it fails it should print out
	public void makeConnection()
	{	
		try 
		{
			connection= DriverManager.getConnection("jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/" + DBNAME, USERNAME, PASSWORD);
		}
		
		catch(SQLException e)
		{
			System.err.println("Database Connection Failed!");
			e.printStackTrace();
			return;
		}
		
		if (connection != null)
		{
			System.err.println("Database Connection Successful");
		}
		else
		{
			System.err.println("Database Failed to make a connection");
		}
	}

	
	//Close database connection
	//TODO find place to close
	public void closeConnection()
	{
		try
		{
			connection.close();
			System.out.println("Database Connection is closed");
		}
		catch(SQLException exceptSQL)
		{
			exceptSQL.printStackTrace();
			System.err.println("Connection to db could not be closed-SQL Exception");
			
		}
	}
	
	//This method connects to the database to return Number of Games played for stat screen
	public int getNumberOfGames()
	{
		Statement numGameStmt = null;
		String numGamesQuery = "SELECT COUNT (gameNumber) AS gameNumber FROM GameStatistics.Game;";
		int numberOfGames = 0;
		
		try
		{
			numGameStmt = connection.createStatement();
			ResultSet numGameResults = numGameStmt.executeQuery(numGamesQuery);
			while (numGameResults.next())
			{ 
				numberOfGames = numGameResults.getInt("gameNumber"); 			
			}
			return numberOfGames;
		}
			
		catch (SQLException noNumber)
		{
			noNumber.printStackTrace();
			System.err.println("Could not execute " + numGamesQuery);
		}
		
		return numberOfGames;
	}
	
	//This method connects to the database to return last Round Winner for game screen
	public String getWinner()
	{
		Statement whoWon = null;
		String whoWonQuery = "SELECT gameNumber, gameWinner FROM GameStatistics.Game;";
		String winnerName = "";
		
		try
		{
			whoWon = connection.createStatement();
			ResultSet winnerResult = whoWon.executeQuery(whoWonQuery);
			
			while(winnerResult.next())
			{
				winnerName = winnerResult.getString("gameWinner");
			}
		}
		
		catch (SQLException noWinner)
		{
			noWinner.printStackTrace();
			System.err.println("Could not execute " + whoWonQuery);
		}
		return winnerName;
	}
	
	//This method connects to the database to return Number of Games the Player won for stat screen
	public int getHumanWin()
	{
		Statement humanWinStmt = null;
		String humanWinQuery = "SELECT COUNT (gameNumber) AS gameNumber FROM GameStatistics.Game WHERE gameWinner = 'Player0';";
		int humanWin = 0;
		
		try
		{
			humanWinStmt = connection.createStatement();
			ResultSet humanWinResults = humanWinStmt.executeQuery(humanWinQuery);
			while (humanWinResults.next())
			{
				humanWin = humanWinResults.getInt("gameNumber"); 
			}
			return humanWin;
		}
			
		catch (SQLException noNumber)
		{
			noNumber.printStackTrace();
			System.err.println("Could not execute " + humanWinQuery);
		}
		return humanWin;
	}
	
	//This method connects to the database to return Number of Games the Computer won for stat screen
	public int getComputerWin()
	{
		Statement numComputerWinStmt = null;
		String numCompWinQuery = "SELECT COUNT (gameNumber) AS gameNumber FROM GameStatistics.Game WHERE gameWinner <> 'Player0';";
		int computerWin = 0;
		
		try
		{
			numComputerWinStmt = connection.createStatement();
			ResultSet compWinResults = numComputerWinStmt.executeQuery(numCompWinQuery);
			while (compWinResults.next())
			{
				computerWin = compWinResults.getInt("gameNumber"); 	
			}
			return computerWin;
		}
			
		catch (SQLException noNumber)
		{
			noNumber.printStackTrace();
			System.err.println("Could not execute " + numCompWinQuery);
		}
		
		return computerWin;
	}
	
	//This method connects to the database to return the most rounds played for stat screen
	public int getMaxRounds()
	{
		Statement maxRoundStmt = null;
		String maxRoundsQuery = "SELECT gameNumber, roundsPlayed FROM GameStatistics.Rounds WHERE roundsPlayed IN (SELECT MAX (roundsPlayed) FROM GameStatistics.Rounds);";
		int maxRounds = 0;
		
		try
		{
			maxRoundStmt = connection.createStatement();
			ResultSet maxRoundResults = maxRoundStmt.executeQuery(maxRoundsQuery);
			while (maxRoundResults.next())
			{
				maxRounds = maxRoundResults.getInt("roundsPlayed"); 			
			}
			return maxRounds;
		}
			
		catch (SQLException noNumber)
		{
			noNumber.printStackTrace();
			System.err.println("Could not execute " + maxRoundsQuery);
		}
		
		return maxRounds;
	}
	
	//This method connects to the database to return the average number of draw for stat screen
	public double getNumberOfDraws()
	{
		Statement numDraws = null;
		String numDrawsQuery = "SELECT cast(AVG(numberDraws)as decimal (3, 2)) AS numberDraws FROM GameStatistics.Rounds;";
		double numberOfDraws = 0;
		
		try
		{
			numDraws = connection.createStatement();
			ResultSet numDrawResults = numDraws.executeQuery(numDrawsQuery);
			while (numDrawResults.next())
			{ 
				numberOfDraws = numDrawResults.getDouble("numberDraws");			
			}
			return numberOfDraws;
		}
			
		catch (SQLException noNumber)
		{
			noNumber.printStackTrace();
			System.err.println("Could not execute " + numDrawsQuery);
		}
		return numberOfDraws;
	}

	
	//TODO add a game from the online game
	public void addGame(Game game)
	{
		
		
	}
	

	/**
	 * 
	 * Inserts values into database GameStatistics.Rounds table at the end of each Game
	 */
	
	public String updateDBRounds() {
//			int nRounds) { 
//			int nDraws, String roundWins) { 
		
		Statement stmt = null;
//		String [] roundWinsPerPlayer = roundWins.split(" ");
		String round = "";
//		if (roundWinsPerPlayer.length > 2) {
//			
//			switch (roundWinsPerPlayer.length) {
//			case 3: round = "INSERT INTO GameStatistics.Rounds (gameNumber, roundsPlayed, numberDraws, roundsWonP0, roundsWonP1, roundsWonP2) VALUES ('"+TopTrumpsCLIApplication.getNumberOfGames()+"', '"+nRounds+"','"+nDraws+"', '"+roundWinsPerPlayer[0]+"', '"+roundWinsPerPlayer[1]+"', '"+roundWinsPerPlayer[2]+"')";
//			case 4: round = "INSERT INTO GameStatistics.Rounds (gameNumber, roundsPlayed, numberDraws, roundsWonP0, roundsWonP1, roundsWonP2, roundsWonP3) VALUES ('"+TopTrumpsCLIApplication.getNumberOfGames()+"', '"+nRounds+"','"+nDraws+"', '"+roundWinsPerPlayer[0]+"', '"+roundWinsPerPlayer[1]+"', '"+roundWinsPerPlayer[2]+"', '"+roundWinsPerPlayer[3]+"')";
//			case 5: round = "INSERT INTO GameStatistics.Rounds (gameNumber, roundsPlayed, numberDraws, roundsWonP0, roundsWonP1, roundsWonP2, roundsWonP3, roundsWonP4) VALUES ('"+TopTrumpsCLIApplication.getNumberOfGames()+"', '"+nRounds+"','"+nDraws+"', '"+roundWinsPerPlayer[0]+"', '"+roundWinsPerPlayer[1]+"', '"+roundWinsPerPlayer[2]+"', '"+roundWinsPerPlayer[3]+"', '"+roundWinsPerPlayer[4]+"')";
//			}
//		}
//		
//		else {
		round = "INSERT INTO GameStatistics.Rounds (gameNumber, roundsPlayed, numberDraws, roundsWonP0, roundsWonP1) VALUES ('"+TopTrumpsCLIApplication.getNumberOfGames()+"',) ";
//				+ "'"+nRounds+"',)";
//		'"+nDraws+"', '"+roundWinsPerPlayer[0]+"', '"+roundWinsPerPlayer[1]+"')";
//		}
		String result = "";
		
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(round);
			result = "Round data inserted";
		}
		catch(SQLException e) {
			e.printStackTrace();
			result = "Error, round data not inserted";
		}
		return result;
	}
	
	/**
	 * 
	 * Inserts values into the database GameStatistics.Game table at the end of each Game
	 */
	
	public String updateDBGame(int nPlayers, String winner) {
		
		Statement stmt = null;
		
		String game = "INSERT INTO GameStatistics.Game (gameNumber, numberPlayers, gameWinner) VALUES ('"+TopTrumpsCLIApplication.getNumberOfGames()+"', '"+nPlayers+"', '"+winner+"',)";
		String result = "";
		
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(game);
			result = "Game data inserted";
		}
		catch(SQLException e) {
			e.printStackTrace();
			result = "Error, game data not inserted";
		}
		return result;
	}
}



