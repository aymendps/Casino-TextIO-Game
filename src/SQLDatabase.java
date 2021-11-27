import java.sql.*;

public class SQLDatabase {

	static String blackjack = "blackjack";
	static String slotMachine = "slot machine";
	static String selectFromAccount = "SELECT balance FROM account WHERE username=";
	static String selectFromGame = "SELECT time_played, games_won, games_lost, largest_win, longest_streak "
			+ "FROM game WHERE game_name=";
	static String selectFromDeposit = "SELECT id, date, amount FROM deposit WHERE username=";
	static String selectFromWithdraw = "SELECT id, date, amount FROM withdraw WHERE username=";
	static String insertIntoAccount = "INSERT INTO "
			+ "account(username,password,balance,credit_card_number,first_name,last_name,phone_number,age)";
	static String insertIntoDeposit = "INSERT INTO deposit(date,amount,username)";
	static String insertIntoWithdraw = "INSERT INTO withdraw(date,amount,username)";
	/*
    		select games won 
    		from games 
    		where username ="aziz"
    		*/
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
        	String select = SQLDatabase.selectFromAccount + "'" + username + "'";
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

	public int[] SelectFromGame(String gameName, String username) // FOR GAME STATS
	{
		ResultSet resultSet = null;
		int[] r = new int[5];
        try (Statement statement = connection.createStatement();) 
        {
            // Create and execute a SELECT SQL statement.
        	String select = SQLDatabase.selectFromGame + "'" + gameName + "' AND username=" + "'" + username + "'";
            resultSet = statement.executeQuery(select);
            // Print results from select statement
            while (resultSet.next()) {
                r[0] = resultSet.getInt("time_played");
                r[1] = resultSet.getInt("games_won");
                r[2] = resultSet.getInt("games_lost");
                r[3] = resultSet.getInt("largest_win");
                r[4] = resultSet.getInt("longest_streak");
            }
            return r;
        
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        	return r;
        }
	}
	
	public Transaction SelectFromTransaction(String SQLCommand, String username) // FOR TRANSACTIONS HISTORY
	{
		ResultSet resultSet = null;
        Transaction t = new Transaction();
        try (Statement statement = connection.createStatement();) 
        {
            // Create and execute a SELECT SQL statement.
        	String select = SQLCommand + "'" + username + "'";
            resultSet = statement.executeQuery(select);
            // Print results from select statement
            while (resultSet.next()) {
                t.id = resultSet.getInt("id");
                t.date = resultSet.getDate("date");
                t.amount = resultSet.getInt("amount");
            }
            return t;
        
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        	return t;
        }
	}
	
	public void InsertIntoTransaction(String SQLCommand, String username, Transaction t) // FOR DEPOSIT & WITHDRAW
	{
    	String insert = SQLCommand + " VALUES(" + t.date + "," + t.amount + ","
    			+ username + ");";
    	
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

}
