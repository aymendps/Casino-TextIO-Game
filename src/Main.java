import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		String password = "";
		try{
			File file = new File("password.casino");
			Scanner sc = new Scanner(file);
			sc.useDelimiter("\\Z");
			password = sc.next();
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("File: 'password.casino' is missing. Please create the file and store your MySQL password in it."
			+ "\r\nIf your project contains a .gitignore, make sure to include 'password.casino' to it.\r\n");
		} catch (NoSuchElementException e){
			password = "";
		}

		SQLDatabase sql = new SQLDatabase("casino_app", "root", password);

		Stopwatch stopwatch = new Stopwatch();

		System.out.print("Established MYSQL connection: " + sql.connection.toString());
		System.out.print("\r\nStarting Application..");

		Account player = new Account(sql);
		
		player.UserChoice();
		int temp; boolean mistake = false;
		do 
		{
		do
	    {
		if(mistake==true)
		{
			player.PlaySoundEffect(player.intro_mistakeSE);
			TextIO.putln("I apologize.. I don't think I can help with that.\r\nAny other requests?");
			TextIO.putln("[Main Menu:] Welcome " + player.GetUsername() + "! You currently have " + player.GetBalance() +
			" chips:\r\n1-Purchase chips\r\n2-Convert chips into money\r\n3-Play Blackjack\r\n4-Play Slot Machine\r\n"
			+ "5-View my game stats\r\n6-View my transaction history\r\n7-Exit");		
		}
		else
		{
	    TextIO.putln("[Main Menu:] Welcome " + player.GetUsername() + "! You currently have " + player.GetBalance() +
		" chips:\r\n1-Purchase chips\r\n2-Convert chips into money\r\n3-Play Blackjack\r\n4-Play Slot Machine\r\n"
		+ "5-View my game stats\r\n6-View my transaction history\r\n7-Exit");
		}
		temp=TextIO.getlnInt();
		if(temp<1 || temp>7)
		{
			mistake = true;
		}
		else
		{
			mistake = false;
		}
		}
		while(temp<1 || temp>7);
		
		if(temp==1)
		{
			player.Deposit();
		}
		else if(temp==2)
		{
			player.Withdraw();
		}
		else if(temp==3)
		{
			// Blackjack
			stopwatch.Start();
			Blackjack blackjack = new Blackjack(player);
			blackjack.TutorialChoice();
			player.blackJackStats.timePlayed = stopwatch.Stop();
			player.SQLdb.UpdateTimePlayed(player.blackJackStats, player.GetUsername());
		}
		else if(temp==4)
		{
			// Slot Machine
			stopwatch.Start();
			SlotMachine sm = new SlotMachine(player);
			sm.TutorialChoice();
			player.slotMachineStats.timePlayed = stopwatch.Stop();
			player.SQLdb.UpdateTimePlayed(player.slotMachineStats, player.GetUsername());
		}
		else if(temp==5)
		{
			// Game Stats
			player.ViewGameStats();
		}
		else if(temp==6)
		{
			// Transaction History
			player.ViewTransactionHistory();
		}
		else if(temp==7)
		{
			player.PlaySoundEffect(player.exitSE);
			TextIO.putln("Thanks for coming and come back soon!");
			while(player.soundEffectClip.isRunning())
			{
				// Wait for last sound effect to end
			}
			sql.CloseConnection();
			System.exit(0);
		}
		}
		while(temp!=7);
	}

}
