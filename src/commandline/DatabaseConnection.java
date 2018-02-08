package commandline;
import java.sql.*;

public class DatabaseConnection 
{
	
	private Connection connection = null;
	private final String DBNAME = "m_17_0806849r"; 
	private final String USERNAME = "m_17_0806849r";
	private final String PASSWORD = "0806849r";
	
	public DatabaseConnection()
	{
		this.makeConnection();
	}
	
	public void createConnection()
	{
		this.makeConnection();
	}
	
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
	
	
	public int getNumberOfGames()
	{
		Statement numGameStmt = null;
		String numGamesQuery = "SELECT COUNT (gameNumber) FROM GameStatistics.Game;\r\n";
		int numberOfGames = 0;
		
		try
		{
			numGameStmt = connection.createStatement();
			ResultSet numGameResults = numGameStmt.executeQuery(numGamesQuery);
			while (numGameResults.next())
			{
				//TODO 
				numberOfGames = numGameResults.getInt("gameNumber"); //do we want to do number or column label?				
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
	
	public int getHumanWin()
	{
		Statement humanWinStmt = null;
		String humanWinQuery = "SELECT COUNT (gameNumber) FROM GameStatistics.Game WHERE gameWinner = 'Player0';";
		int humanWin = 0;
		
		try
		{
			humanWinStmt = connection.createStatement();
			ResultSet humanWinResults = humanWinStmt.executeQuery(humanWinQuery);
			while (humanWinResults.next())
			{
				//TODO 
				humanWin = humanWinResults.getInt("gameNumber"); //do we want to do number or column label?				
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
	
	public int getComputerWin()
	{
		Statement numComputerWinStmt = null;
		String numCompWinQuery = "SELECT COUNT (gameNumber) FROM GameStatistics.Game WHERE gameWinner <> 'Player0';";
		int computerWin = 0;
		
		try
		{
			numComputerWinStmt = connection.createStatement();
			ResultSet compWinResults = numComputerWinStmt.executeQuery(numCompWinQuery);
			while (compWinResults.next())
			{
				//TODO 
				computerWin = compWinResults.getInt("gameNumber"); //do we want to do number or column label?				
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
				//TODO 
				maxRounds = maxRoundResults.getInt("roundsPlayed"); //do we want to do number or column label?				
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
	
	public int getNumberOfDraws()
	{
		Statement numDraws = null;
		String numDrawsQuery = "SELECT cast(AVG(numberDraws)as decimal (3,2)) FROM GameStatistics.Rounds\r\n";
		int numberOfDraws = 0;
		
		try
		{
			numDraws = connection.createStatement();
			ResultSet numDrawResults = numDraws.executeQuery(numDrawsQuery);
			while (numDrawResults.next())
			{
				//TODO 
				numberOfDraws = numDrawResults.getInt("numberDraws"); //do we want to do number or column label?				
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
	
	//TODO Update Database when save/quit--send string into db?
	public void updateDB()
	{
		//INSERT INTO 
	}
	
}


