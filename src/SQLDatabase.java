import java.sql.*;

public class SQLDatabase {
	String database;
	String user;
	String password;
    String connectionURL;
    
    public SQLDatabase(String database, String user, String password)
    {
    	this.database = database;
    	this.user = user;
    	this.password = password;
    	connectionURL = "jdbc:mysql://localhost:3306/" + this.database;
    }
    
    
	public void ConnectToServer() throws SQLException, ClassNotFoundException
	{
    	Class.forName("com.mysql.cj.jdbc.Driver"); 
        Connection connection = DriverManager.getConnection(connectionURL, user, password);
        System.out.print(connection);
	}
}
