import java.sql.*;

import com.mysql.cj.exceptions.WrongArgumentException;

public class SQLDatabase {

	static String blackjack = "blackjack";
	static String slotMachine = "slot machine";
	static String deposit = "deposit";
    static String withdraw = "withdraw";

	static String selectFromAccountBalance = "SELECT balance FROM account WHERE username=";
    static String selectFromAccountSignIn = "SELECT * FROM account WHERE username=";
    static String selectFromAccountUsername = "Select username FROM account WHERE username=";
    static String selectFromAccountCCN = "Select credit_card_number FROM account WHERE credit_card_number=";
    static String selectFromAccountPhone = "Select phone_number FROM account WHERE phone_number=";
	static String selectFromGame = "SELECT time_played, games_won, games_lost, largest_win, longest_streak "
			+ "FROM game WHERE game_name=";
	static String selectFromDeposit = "SELECT id, date, amount FROM deposit WHERE username=";
	static String selectFromWithdraw = "SELECT id, date, amount FROM withdraw WHERE username=";
    static String selectCountDeposit = "SELECT COUNT(*) FROM deposit WHERE username=";
    static String selectCountWithdraw = "SELECT COUNT(*) FROM withdraw WHERE username=";

	static String insertIntoAccount = "INSERT INTO "
			+ "account(username,password,balance,credit_card_number,first_name,last_name,phone_number,age)";
	static String insertIntoDeposit = "INSERT INTO deposit(date,amount,username)";
	static String insertIntoWithdraw = "INSERT INTO withdraw(date,amount,username)";
	static String insertIntoGame = "INSERT INTO game(game_name,username,time_played,games_won,games_lost,"
			+ "largest_win, longest_streak)";
			
	String database;
	String user;
	String password;
    String connectionURL;
    Connection connection;
    
    public SQLDatabase(String database, String user, String password)
    {
    	this.database = database;
    	this.user = user;
    	this.password = password;
    	connectionURL = "jdbc:mysql://localhost:3306/" + this.database;
    	ConnectToDatabase();
    }
    
    private void ConnectToDatabase()
    {
    	try
    	{
        	Class.forName("com.mysql.cj.jdbc.Driver"); 
    	}
    	catch(ClassNotFoundException e)
    	{
    	e.printStackTrace();
    	}
    	
    	try //(Connection connection = DriverManager.getConnection(connectionURL, user, password);)
    	{
    		this.connection = DriverManager.getConnection(connectionURL, user, password);
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}

    }
    
    public void CloseConnection()
    {
        try
        {
            if (connection != null)
            {
                connection.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void Close(Statement statement)
    {
        try
        {
            if (statement != null)
            {
                statement.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void Close(ResultSet resultSet)
    {
        try
        {
            if (resultSet != null)
            {
                resultSet.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
	
	public int SelectFromAccount(String username) // FOR BALANCE
	{
		ResultSet resultSet = null;
        try (Statement statement = connection.createStatement();) 
        {
            // Create and execute a SELECT SQL statement.
        	String select = SQLDatabase.selectFromAccountBalance + "'" + username + "'";
            resultSet = statement.executeQuery(select);
            // Print results from select statement
            int balance = 0;
            while (resultSet.next()) {
                balance = resultSet.getInt("balance");
            }
            return balance;
        
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        	return -1;
        }
        
		/*
		select games won 
		from games 
		where username ="aziz"
		*/
	}

    public boolean UsernameExists(String username)
    {
        ResultSet resultSet = null;
        String exists = "Not There";
        try (Statement statement = connection.createStatement();) 
        {
            // Create and execute a SELECT SQL statement.
        	String select = SQLDatabase.selectFromAccountUsername + "'" + username + "'";
            resultSet = statement.executeQuery(select);
            // Print results from select statement
            while (resultSet.next()) {
                exists = resultSet.getString("username");
            }
            return !exists.equals("Not There");
        
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        	return !exists.equals("Not There");
        }
    }

    public boolean CCNExists(int ccn)
    {
        ResultSet resultSet = null;
        int exists = -1;
        try (Statement statement = connection.createStatement();) 
        {
            // Create and execute a SELECT SQL statement.
        	String select = SQLDatabase.selectFromAccountCCN + ccn;
            resultSet = statement.executeQuery(select);
            // Print results from select statement
            while (resultSet.next()) {
                exists = resultSet.getInt("credit_card_number");
            }
            return exists!=-1;
        
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        	return exists!=-1;
        }
    }

    public boolean PhoneNumberExists(int phone)
    {
        ResultSet resultSet = null;
        int exists = -1;
        try (Statement statement = connection.createStatement();) 
        {
            // Create and execute a SELECT SQL statement.
        	String select = SQLDatabase.selectFromAccountPhone + phone;
            resultSet = statement.executeQuery(select);
            // Print results from select statement
            while (resultSet.next()) {
                exists = resultSet.getInt("phone_number");
            }
            return exists!=-1;
        
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        	return exists!=-1;
        }
    }

    public AccountInfo SelectFromAccount(String username, String password) // FOR SIGN IN
    {
        ResultSet resultSet = null;
		AccountInfo info = new AccountInfo();
        try (Statement statement = connection.createStatement();) 
        {
            // Create and execute a SELECT SQL statement.
        	String select = SQLDatabase.selectFromAccountSignIn + "'" + username + "' AND password=" + "'" + password + "'";
            resultSet = statement.executeQuery(select);
            // Print results from select statement
            while (resultSet.next()) {
                info.username = resultSet.getString("username");
                info.password = resultSet.getString("password");
                info.balance = resultSet.getInt("balance");
                info.ccn = resultSet.getInt("credit_card_number");
                info.firstName = resultSet.getString("first_name");
                info.lastName = resultSet.getString("last_name");
                info.phone = resultSet.getInt("phone_number");
                info.age = resultSet.getInt("age");
            }
            return info;
        
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        	return info;
        }
    }

	public GameStats SelectFromGame(String gameName, String username) // FOR GAME STATS
	{
		ResultSet resultSet = null;
		GameStats stats = new GameStats(gameName);
        try (Statement statement = connection.createStatement();) 
        {
            // Create and execute a SELECT SQL statement.
        	String select = SQLDatabase.selectFromGame + "'" + gameName + "' AND username=" + "'" + username + "'";
            resultSet = statement.executeQuery(select);
            // Print results from select statement
            while (resultSet.next()) {
                stats.timePlayed = resultSet.getInt("time_played");
                stats.gamesWon = resultSet.getInt("games_won");
                stats.gamesLost = resultSet.getInt("games_lost");
                stats.largestWin = resultSet.getInt("largest_win");
                stats.longestStreak = resultSet.getInt("longest_streak");
            }
            return stats;
        
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        	return stats;
        }
	}
	
	public Transaction[] SelectFromTransaction(String tableName, String username) // FOR TRANSACTIONS HISTORY
	{
		ResultSet resultSet = null;
        ResultSet countSet = null;
        int total = 0;
        Transaction[] t = null;
        try (Statement statement = connection.createStatement();) 
        {   
            String select, count;
            // Create and execute a SELECT SQL statement.
            if(tableName.equals(deposit))
            {
                select = selectFromDeposit + "'" + username + "'";
                count = selectCountDeposit + "'" + username + "'";
            }
            else if(tableName.equals(withdraw))
            {
                select = selectFromWithdraw + "'" + username + "'";
                count = selectCountWithdraw + "'" + username + "'";
            }
            else
            {
                throw new WrongArgumentException();
            }
            countSet = statement.executeQuery(count);
            while(countSet.next())
            {
                total = countSet.getInt("COUNT(*)");
            }

            if(total==0)
            {
                return t;
            }

            resultSet = statement.executeQuery(select);
            // Print results from select statement
            int i=0;
            t = new Transaction[total];
            while (resultSet.next()) {
                Transaction transaction = new Transaction(resultSet.getInt("amount"), resultSet.getInt("id"), resultSet.getDate("date"));
                t[i] = transaction;
                i++;
            }
            return t;
        
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        	return t;
        }
        catch(WrongArgumentException e)
        {
            System.err.println(tableName + " is not a valid table name. Try changing the String argument.");
            return t;
        }
	}
	
	public void InsertIntoAccount(AccountInfo info) // FOR SIGN UP
	{
    	String insert = SQLDatabase.insertIntoAccount + " VALUES('" + info.username + "','" + info.password + "',"
    			+ 0 + "," + info.ccn + ",'" + info.firstName + "','" + info.lastName + "'," + info.phone + "," 
    			+ info.age + ");";
		
        try (PreparedStatement statement = connection.prepareStatement(insert);) 
        {
            // Create and execute a SELECT SQL statement.
            statement.execute();
            // Print results from select statement
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
	}
	
	public void InsertIntoGame(GameStats stats, String username) // FOR GAME STATS
	{
    	String insert = SQLDatabase.insertIntoGame + " VALUES('" + stats.gameName + "','" + username + "',"
    			+ stats.timePlayed + "," + stats.gamesWon + ",'" + stats.gamesLost + "','" + stats.largestWin
    			+ "'," + stats.longestStreak + ");";
		
        try (PreparedStatement statement = connection.prepareStatement(insert);) 
        {
            // Create and execute a SELECT SQL statement.
            statement.execute();
            // Print results from select statement
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
	}
	
	public void InsertIntoTransaction(String tableName, String username, Transaction t) // FOR DEPOSIT & WITHDRAW
	{
        String insert;
        
        if(tableName.equals(deposit))
        {
            insert = insertIntoDeposit + " VALUES(" + "'" + t.date + "'" + "," + t.amount + ","
            + "'" + username + "'" + ")";
        }
        else if(tableName.equals(withdraw))
        {
            insert = insertIntoWithdraw + " VALUES(" + "'" + t.date + "'" + "," + t.amount + ","
            + "'" + username + "'" + ")";
        }
        else
        {
            throw new WrongArgumentException();
        }
    	
        try (PreparedStatement statement = connection.prepareStatement(insert);) 
        {
            // Create and execute a SELECT SQL statement.
            statement.execute();
            // Print results from select statement
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
        catch(WrongArgumentException e)
        {
            System.err.println(tableName + " is not a valid table name. Try changing the String argument.");
        }
	}

	public void UpdateAccount(int balance, String username)
	{
		/*UPDATE table
		SET column1 = value1, column2=value2, �
		WHERE username = " "  ;*/
		
    	String update = "UPDATE account SET balance=" + balance + " WHERE username=" + "'" + username + "'"; 		
        
    	try (PreparedStatement statement = connection.prepareStatement(update);) 
        {
            // Create and execute a SELECT SQL statement.
            statement.execute();
            // Print results from select statement.
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
	}
	
	public void UpdateGame(GameStats stats, String username, boolean isGameWon)
	{
		/*UPDATE table
		SET column1 = value1, column2=value2, �
		WHERE username = " "  ;*/
		//game_name,username,time_played,games_won,games_lost,largest_win,longest_streak
		String update;
		if(isGameWon)
		{
			update = "UPDATE game SET games_won=games_won+1";
			GameStats old = SelectFromGame(stats.gameName, username);
			
			if(stats.largestWin > old.largestWin)
			{
				update = update + ", largest_win=" + stats.largestWin;
			}
			if(stats.longestStreak > old.longestStreak)
			{
				update = update + ", longest_streak=" + stats.longestStreak;
			}
			
			update = update + " WHERE username=" + "'" + username + "'" + " AND game_name=" 
			+ "'" + stats.gameName + "'";
			
		}
		else
		{
			update = "UPDATE game SET games_lost=games_lost+1 WHERE username=" + "'" + username + "'"
					+ " AND game_name=" + "'" + stats.gameName + "'";
		}		
        
    	try (PreparedStatement statement = connection.prepareStatement(update);) 
        {
            // Create and execute a SELECT SQL statement.
            statement.execute();
            // Print results from select statement.
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
	}

    public void UpdateTimePlayed(GameStats stats, String username) // UPDATE TIME PLAYED
    {
        		/*UPDATE table
		SET column1 = value1, column2=value2, �
		WHERE username = " "  ;*/
		
    	String update = "UPDATE game SET time_played=time_played+" + stats.timePlayed + " WHERE username=" + "'" + username + "'"
        + " AND game_name=" + "'" + stats.gameName + "'";	
        
    	try (PreparedStatement statement = connection.prepareStatement(update);) 
        {
            // Create and execute a SELECT SQL statement.
            statement.execute();
            // Print results from select statement.
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
    }
	
}
