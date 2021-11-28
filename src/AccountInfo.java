
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
		username = "none";
		password = "none";
		firstName = "none";
		lastName = "none";
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
		System.out.println("\r\nCurrent user:\r\nUsername: " + username + "\r\nPassword: " + password + "\r\nFirst Name: " + firstName
		+ "\r\nLast Name: " + lastName + "\r\nBalance: " + balance + "\r\nCredit Card Number: "
		+ ccn + "\r\nPhone Number: " + phone + "\r\nAge: " + age);
	}
	
	
}
