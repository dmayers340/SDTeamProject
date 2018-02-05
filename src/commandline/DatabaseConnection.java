package commandline;
import java.sql.*;

public class DatabaseConnection 
{
	
	private Connection connection = null;
	//TODO fill these in
	private final String DBNAME = ""; 
	private final String USERNAME = "";
	private final String PASSWORD = "";
	
	//do we need a default constructor as we pass values? Probably?
	public DatabaseConnection()
	{
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
			System.out.println("Database Connection Successful");
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
			System.out.println("Connection to db could not be closed-SQL Exception");
			
		}
	}
	
	public int getNumberOfGames()
	{
		Statement numGameStmt = null;
		String numGamesQuery = "SELECT .......;";
		int numberOfGames;
		
		try
		{
			numGameStmt = connection.createStatement();
			ResultSet numGameResults = numGameStmt.executeQuery(numGamesQuery);
			while (numGameResults.next())
			{
				//TODO 
				numberOfGames = numGameResults.getInt(columnLabel); //do we want to do number or column label?				
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
	
	public int getHumanWin()
	{
		Statement humanWinStmt = null;
		String humanWinQuery = "SELECT .......;";
		int humanWin;
		
		try
		{
			humanWinStmt = connection.createStatement();
			ResultSet humanWinResults = humanWinStmt.executeQuery(humanWinQuery);
			while (humanWinResults.next())
			{
				//TODO 
				humanWin = humanWinResults.getInt(columnLabel); //do we want to do number or column label?				
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
		String numCompWinQuery = "SELECT .......;";
		int computerWin;
		
		try
		{
			numComputerWinStmt = connection.createStatement();
			ResultSet compWinResults = numComputerWinStmt.executeQuery(numCompWinQuery);
			while (compWinResults.next())
			{
				//TODO 
				computerWin = compWinResults.getInt(columnLabel); //do we want to do number or column label?				
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
		String maxRoundsQuery = "SELECT .......;";
		int maxRounds;
		
		try
		{
			maxRoundStmt = connection.createStatement();
			ResultSet maxRoundResults = maxRoundStmt.executeQuery(maxRoundsQuery);
			while (maxRoundResults.next())
			{
				//TODO 
				maxRounds = maxRoundResults.getInt(columnLabel); //do we want to do number or column label?				
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
		String numDrawsQuery = "SELECT .......;";
		int numberOfDraws;
		
		try
		{
			numDraws = connection.createStatement();
			ResultSet numDrawResults = numDraws.executeQuery(numDrawsQuery);
			while (numDrawResults.next())
			{
				//TODO 
				numberOfDraws = numDrawResults.getInt(columnLabel); //do we want to do number or column label?				
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


