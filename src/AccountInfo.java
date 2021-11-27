
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
		ccn = 1234567890;
		phone = 1234567890;
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

	public void Print()
	{
		System.out.println(username + "\r\n" + password + "\r\n" + firstName + "\r\n" + lastName + "\r\n" + balance + "\r\n"
		+ ccn + "\r\n" + phone + "\r\n" + age + "\r\n");
	}
	
	
}
