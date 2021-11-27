import java.sql.*;
public class Transaction {

	int amount;
	int id;
	Date date;
	
	public Transaction()
	{
		amount = 0;
		id = 0;
		date = null;
	}
	
	public Transaction(int amount,int id, Date date)
	{
		this.amount = amount;
		this.id = id;
		this.date = date;
	}
	
	

}
