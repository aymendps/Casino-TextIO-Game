
public class AccountInfo {
	
	String username;
	String password;
	String firstName;
	String lastName;
	int balance;
	int ccn;
	int phone;
	int age;
	
	public AccountInfo()
	{
		username = "admin";
		password = "admin";
		firstName = "admin";
		lastName = "admin";
		balance = 0;
		ccn = 12345678;
		phone = 12345678;
		age = 21;
				
	}
	public AccountInfo(String username, String password, String firstName, String lastName, int balance, int ccn, int phone, int age)
	{
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.balance = balance;
		this.ccn = ccn;
		this.phone = phone;
		this.age = age;
	}
	
	
}
