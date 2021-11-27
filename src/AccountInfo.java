
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
		username = "aziz";
		password = "aziz";
		firstName = "mohamed aziz";
		lastName = "maazouz";
		balance = 200;
		ccn = 15015015;
		phone = 26262626;
		age = 66;
				
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
