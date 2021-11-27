public class Main {
	
	public static void main(String[] args) {
		
		SQLDatabase sql = new SQLDatabase("casino_app", "root", "yourpassword");
		System.out.print("Established MYSQL connection: " + sql.connection.toString());
		System.out.print("\r\nStarting Application..");
		
		AccountInfo a = new AccountInfo();
		GameStats b = new GameStats(SQLDatabase.blackjack);
		GameStats s = new GameStats(SQLDatabase.slotMachine);
		sql.InsertIntoAccount(a);
		sql.InsertIntoGame(b, a.username);
		sql.InsertIntoGame(s, a.username);
		
		Account player = new Account();

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
			TextIO.putln("[Main Menu:] You currently have " + player.GetChips() + " chips:\r\n1-Purchase chips\r\n2-Convert chips into money\r\n3-Play Blackjack\r\n4-Play Slot Machine\r\n5-Exit");		
		}
		else
		{
	    TextIO.putln("[Main Menu:] You currently have " + player.GetChips() + " chips:\r\n1-Purchase chips\r\n2-Convert chips into money\r\n3-Play Blackjack\r\n4-Play Slot Machine\r\n5-Exit");	
		}
		temp=TextIO.getlnInt();
		if(temp<1 || temp>5)
		{
			mistake = true;
		}
		else
		{
			mistake = false;
		}
		}
		while(temp<1 || temp>5);
		
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
			Blackjack blackjack = new Blackjack(player);
			blackjack.TutorialChoice();
		}
		else if(temp==4)
		{
			// Slot Machine
			SlotMachine sm = new SlotMachine(player);
			sm.TutorialChoice();
		}
		else if(temp==5)
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
		while(temp!=5);
	}

}
