import java.sql.*;
public class Transaction {

	int amount;
	int id;
	Date date; // YYYY-MM-DD
	
	public Transaction()
	{
		amount = 0;
		id = 0;
		date = GetDate();
	}

	public Transaction(int amount)
	{
		this.amount = amount;
		this.id = 0;
		this.date = GetDate(); 

	}
	
	public Transaction(int amount,int id, Date date)
	{
		this.amount = amount;
		this.id = id;
		this.date = date;
	}

	private Date GetDate()
	{
		long millis = System.currentTimeMillis();  
        return new Date(millis); 
	}
	
	public void Print()
	{
		System.out.println("Transaction:\r\nID= " + id + "\r\nDATE= " + date + "\r\nAMOUNT= " + amount);
	}
	

}
