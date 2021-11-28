import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Account implements Music {
	private AccountInfo accountInfo;
	public SQLDatabase SQLdb;
	private boolean useSQL = true;
	//private String username;
	private String tempuser; // Temp username used for sign-in
	//private String password;
	private String temppass; // Temp password used for sign-in
	//private int chips;
	
	private int DBtotalindex; // Number of databases created containing Username/Password/Chips
	private int DBcurrentindex; // Index of currently used database 
	
	private boolean SignUp; // True for sign-up , False for sign-in
	private boolean SignInSuccess; // Checks if sign-in was successful or not
	private boolean SignUpSuccess; // Checks if sign-up was successful or not
	
	public GameStats blackJackStats;
	public GameStats slotMachineStats;
	
	
	private AudioInputStream soundEffectStream; // Using the same AudioInputStream and the same Clip
    public Clip soundEffectClip;          // in order to have only 1 Sound Effect playing at a time
	
    public File musicFile; // Background Music
	public File introSE, intro_mistakeSE, intro_signupSE, intro_signinSE, intro_endSE,
	intro_mistake_signinSE, intro_securitySE, deposit_chipsSE, withdraw_chipsSE,
	bank_cardSE, transactionSE, blackjack_shufflingSE, game_betSE, game_roundSE,
	game_nochipsSE, blackjack_introSE, sm_introSE, exitSE; // Sound Effects 
	

	
    public Account(SQLDatabase SQLdb)
    {
    	accountInfo = new AccountInfo();
    	blackJackStats = new GameStats(SQLDatabase.blackjack);
    	slotMachineStats = new GameStats(SQLDatabase.slotMachine);
    	this.SQLdb = SQLdb;
    	FetchDBTI();
    	FetchFiles();

    }
	
	public int GetBalance()
	{
		return accountInfo.balance;
	}

	public String GetUsername()
	{
		return accountInfo.username;
	}
	
	private void FetchFiles()
	{
		musicFile = new File("assets\\Music.wav");
		introSE = new File("assets\\introSE.wav");
		intro_mistakeSE = new File("assets\\intro_mistakeSE.wav");
		intro_signupSE = new File("assets\\intro_signupSE.wav");
		intro_signinSE = new File("assets\\intro_signinSE.wav");
		intro_endSE = new File("assets\\intro_endSE.wav");
		intro_mistake_signinSE = new File("assets\\intro_mistake_signinSE.wav");
		intro_securitySE = new File("assets\\intro_securitySE.wav");
		deposit_chipsSE = new File("assets\\deposit_chipsSE.wav");
		withdraw_chipsSE = new File("assets\\withdraw_chipsSE.wav");
		bank_cardSE = new File("assets\\bank_cardSE.wav");
		transactionSE = new File("assets\\transactionSE.wav"); 
		blackjack_shufflingSE = new File("assets\\blackjack_shufflingSE.wav");
		game_betSE = new File("assets\\game_betSE.wav");
		game_roundSE = new File("assets\\game_roundSE.wav");
		game_nochipsSE = new File("assets\\game_nochipsSE.wav");
		blackjack_introSE = new File("assets\\blackjack_introSE.wav");
		sm_introSE = new File("assets\\sm_introSE.wav");// here
		exitSE = new File("assets\\exitSE.wav");
	}
	
	public void PlayMusic()
	{
		
		try {
			
			AudioInputStream stream;
		    Clip musicClip;
		    stream = AudioSystem.getAudioInputStream(musicFile);
            musicClip = AudioSystem.getClip();
		    musicClip.open(stream);
		    musicClip.loop(Clip.LOOP_CONTINUOUSLY);
		    //musicClip.setFramePosition(0);
		    //musicClip.start();
		}
	    catch (UnsupportedAudioFileException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (LineUnavailableException e) {
	        e.printStackTrace();
	    }
	}
	public void PlaySoundEffect(File soundEffectFile)
	{
		try {
			
            if(soundEffectClip != null)
            {
            	if(soundEffectClip.isRunning())
            	{
            		soundEffectClip.stop();
            	}
            	//else
            	//{
            	//soundEffectClip.close();
            	//}
            }
		    soundEffectStream = AudioSystem.getAudioInputStream(soundEffectFile);
            soundEffectClip = AudioSystem.getClip();
		    soundEffectClip.open(soundEffectStream);
		    soundEffectClip.setFramePosition(0);
		    soundEffectClip.start();
		}
	    catch (UnsupportedAudioFileException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (LineUnavailableException e) {
	        e.printStackTrace();
	    }
	}
	
	private void FetchDBTI()
	{
		TextIO.readFile("db\\dbindex.casino");
		DBtotalindex = TextIO.getInt();
		TextIO.readStandardInput();
	}	
	private void FetchAccount()
	{
		if(SignUp == true)
		{
			if(useSQL)
			{
				if(SQLdb.UsernameExists(accountInfo.username))
				{
					SignUpSuccess=false;
					TextIO.putln("[This username is already taken. Please try again:]");
				}
				else if(SQLdb.CCNExists(accountInfo.ccn))
				{
					SignUpSuccess=false;
					TextIO.putln("[This credit card number is already taken. Please try again:]");
				}
				else if(SQLdb.PhoneNumberExists(accountInfo.phone))
				{
					SignUpSuccess=false;
					TextIO.putln("[This phone number is already taken. Please try again:]");
				}
				else
				{
					SignUpSuccess=true;
					SQLdb.InsertIntoAccount(accountInfo);
					SQLdb.InsertIntoGame(blackJackStats, accountInfo.username);
					SQLdb.InsertIntoGame(slotMachineStats, accountInfo.username);
					System.out.print("\r\nMethod: Sign up");
					accountInfo.Print();
				}
			}
			else
			{
				String uniqueUser,uniquePass;
				boolean uniqueTest=true;
				for(int i=0; i<DBtotalindex;i++)
				{
					String fileName = "db\\DB" + i + ".casino";
					TextIO.readFile(fileName);
					uniqueUser = TextIO.getln();
					uniquePass = TextIO.getln();
					TextIO.readStandardInput();
					if(uniqueUser.equals(accountInfo.username)==true && 
							uniquePass.equals(accountInfo.password)==true)
					{
						uniqueTest=false;
					}
				}
				if(uniqueTest==false)
				{
					SignUpSuccess=false;
					TextIO.putln("[This combination of username & password is already taken. Please try again:]");
				}
				else
				{
					SignUpSuccess=true;
					DBcurrentindex=DBtotalindex;
					UpdateDB();
				}
			}

		}
		else if(SignUp == false)
		{
			if(useSQL)
			{
				AccountInfo i = SQLdb.SelectFromAccount(tempuser, temppass);
				if(!i.username.equals("none"))
				{
					SignInSuccess=true;
					accountInfo = i;
					blackJackStats = SQLdb.SelectFromGame(SQLDatabase.blackjack, accountInfo.username);
					slotMachineStats = SQLdb.SelectFromGame(SQLDatabase.slotMachine, accountInfo.username);
					PlaySoundEffect(intro_endSE);
					TextIO.putln("And..Perfect! I'll handle the rest of the paperwork now.\r\n"
					+ "Well, enjoy your evening, and remember, spend your money \"wisely\".");
					System.out.print("\r\nMethod: Sign in");
					accountInfo.Print();
				}
				else
				{
					SignInSuccess=false;
					PlaySoundEffect(intro_mistake_signinSE);
					TextIO.putln("It doesnt match..There must be a mistake somewhere. Try again..");
				}
			}
			else
			{
				String user,pass;
				boolean test=false;
				int indexWanted=0;
				for(int i=0; i<DBtotalindex;i++)
				{
					String fileName = "db\\DB" + i + ".casino";
					TextIO.readFile(fileName);
					user = TextIO.getln();
					pass = TextIO.getln();
					TextIO.readStandardInput();
					if(user.equals(tempuser)==true && pass.equals(temppass)==true)
					{
						test=true;
						indexWanted = i; 
					}
				}
				
				if(test==true)
				{
					DBcurrentindex = indexWanted;
					SignInSuccess=true;
					GetDB();
					PlaySoundEffect(intro_endSE);
					TextIO.putln("And..Perfect! I'll handle the rest of the paperwork now.\r\nWell, enjoy your evening, and remember, spend your money \"wisely\".");
				}
				else
				{
					SignInSuccess=false;
					PlaySoundEffect(intro_mistake_signinSE);
					TextIO.putln("It doesnt match..There must be a mistake somewhere. Try again..");
				}
			}
			
		}
	}
	
	public void UpdateDB()
	{
		String fileName = "db\\DB" + DBcurrentindex + ".casino";
		TextIO.writeFile(fileName);
		TextIO.putln(accountInfo.username);
		TextIO.putln(accountInfo.password);
		TextIO.putln(accountInfo.balance);
		if(SignUp==true)
		{
		DBtotalindex++;
		TextIO.writeFile("db\\dbindex.casino");
		TextIO.putln(DBtotalindex);
		}
		TextIO.writeStandardOutput();
	}
	
	private void GetDB()
	{
		String fileName = "db\\DB" + DBcurrentindex + ".casino";
		TextIO.readFile(fileName);
		accountInfo.username=TextIO.getln();
	    accountInfo.password=TextIO.getln();
	    accountInfo.balance=TextIO.getlnInt();
	    TextIO.readStandardInput();
	}
	
	public void UserChoice()
	{
		PlayMusic();
		System.out.print("\r\nStarted Application");
		int temp;
		TextIO.putln("        _______                                           . .   ' '  ,  ,\r\n"
				+ "       /\\ o o o\\      ____    _    ____ ___ _   _  ___       _________\r\n"
				+ "      /o \\ o o o\\    / ___|  / \\  / ___| _ | \\ | |/ _ \\   _ /_|_____|_\\ _\r\n"
				+ "     < o  >------>  | |     / _ \\ \\___ \\| ||  \\| | | | |    '. \\   / .'\r\n"
				+ "      \\o /  o   /   | |___ / ___ \\ ___) | || |\\  | |_| |      '.\\ /.'\r\n"
				+ "       \\/______/     \\____/_/   \\_|____|___|_| \\_|\\___/         '.'\r\n\r\n"
				+ "                         - Press 'Enter' to start -\r\n");
		
		while(TextIO.console.appStarted==false)
        {
        try {
        	TextIO.console.setForeground(Color.BLACK);
            Thread.sleep(400);
        	if(TextIO.console.appStarted==true)
        	{
        		//Thread.interrupted();
        		break;
        	}
            TextIO.console.setForeground(Color.WHITE);
            Thread.sleep(400);
        	if(TextIO.console.appStarted==true)
        	{
        		//Thread.interrupted();
        		TextIO.console.setForeground(Color.BLACK);
        		break;
        	}
            TextIO.console.setForeground(Color.RED);
            Thread.sleep(400);
        	if(TextIO.console.appStarted==true)
        	{
        		//Thread.interrupted();
        		break;
        	}
            TextIO.console.setForeground(Color.WHITE);
            Thread.sleep(400);
        	if(TextIO.console.appStarted==true)
        	{
        		//Thread.interrupted();
        		TextIO.console.setForeground(Color.RED);
        		break;
        	}
            TextIO.console.setForeground(Color.BLUE);
            Thread.sleep(400);
        	if(TextIO.console.appStarted==true)
        	{
        		//Thread.interrupted();
        		break;
        	}
            TextIO.console.setForeground(Color.WHITE);
            Thread.sleep(400);
        	if(TextIO.console.appStarted==true)
        	{
        		//Thread.interrupted();
        		TextIO.console.setForeground(Color.BLUE);
        		break;
        	}
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        }
		PlaySoundEffect(introSE);
		TextIO.putln("Welcome to our unique casino experience.");
		TextIO.putln("How may I help you today? [1- Sign-up | 2- Sign-in]");
		
		boolean mistake = false;
		do
		{
			if(mistake==true)
			{
				PlaySoundEffect(intro_mistakeSE);
				TextIO.putln("I apologize.. I don't think I can help with that.\r\nAny other requests? [1- Sign-up | 2- Sign-in]");
			}
			temp = TextIO.getlnInt();
			if(temp!=1 && temp!=2)
			{
				mistake=true;
			}
		}
		while(temp!=1 && temp!=2);
		if(temp == 1)
		{
			PlaySoundEffect(intro_signupSE);
			TextIO.putln("I see.. New in town? Please take your time to fill this form for me.");
			SignUp();
		}
		else if(temp == 2)
		{
			PlaySoundEffect(intro_signinSE);
			TextIO.putln("Welcome back! You are just a few lines away from your usual seat.");
			SignIn();
		}
		else
		{
			System.err.println("ERROR: Value of the integer temp different of 1&2");
			TextIO.putln("It seems we are having technical problems right now. Come again later..");
		}
		//while(soundEffectClip.isRunning())
		//{
			//Wait for last sound effect to end
		//}
	}
		
    public void ChangeBalance(int x) // x should be: Positive if + / Negative if -
    {
    	accountInfo.balance = accountInfo.balance + x;
    }
	public void Deposit()
	{
		int confirmation;
		int deposit;
		PlaySoundEffect(deposit_chipsSE);
		TextIO.putln("How many chips would you like to purchase? [1 chip: 1 usd]");
	    do
	    {
		   TextIO.putln("Please type the number of chips:\r\nMinimum purchase: 10 chips\r\nMaximum purchase: 50000 chips");
		   deposit=TextIO.getlnInt();
	    }
		while(deposit<10 || deposit>50000);
	    
	    PlaySoundEffect(bank_cardSE);
	    TextIO.putln("I just need you to insert your card here please.");
	    do
	    {
	    TextIO.putln("Credit Card " + accountInfo.ccn + " is going to be charged " + deposit
		+ " usd.\r\nPlease confirm the transaction. [1- Confirm | 2- Abort]");
		confirmation=TextIO.getlnInt();
		}
		while(confirmation!=1 && confirmation!=2);

		if(confirmation==1)
		{
			ChangeBalance(deposit);
			SQLdb.UpdateAccount(accountInfo.balance, accountInfo.username);
			Transaction t = new Transaction(deposit);
			SQLdb.InsertIntoTransaction(SQLDatabase.insertIntoDeposit, accountInfo.username, t);
			PlaySoundEffect(transactionSE);
			TextIO.putln("Transaction complete!");
		}
		else
		{
			TextIO.putln("Transaction aborted.");
		}

	    
	}
	public void Withdraw()
	{
		if(accountInfo.balance>0)
		{
		int confirmation;
		int withdraw;
		PlaySoundEffect(withdraw_chipsSE);
	    TextIO.putln("Congratulations on your winnings! How many chips would you like to turn-in?");
	    do
	    {
        TextIO.putln("Please enter the number of chips:");
		withdraw=TextIO.getlnInt();
	    }
	    while(withdraw>accountInfo.balance);
	    PlaySoundEffect(bank_cardSE);
	    TextIO.putln("I just need you to insert your card here please.");
	    do
	    {
	    TextIO.putln("Withdrawing " + withdraw + " usd to Credit Card " +  accountInfo.ccn
		+"\r\nPlease confirm the transaction. [1- Confirm | 2- Abort]");
		confirmation=TextIO.getlnInt();
		}
		while(confirmation!=1 && confirmation!=2);
		
		if(confirmation==1)
		{
			ChangeBalance(-withdraw);
			SQLdb.UpdateAccount(accountInfo.balance, accountInfo.username);
			Transaction t = new Transaction(withdraw);
			SQLdb.InsertIntoTransaction(SQLDatabase.insertIntoWithdraw, accountInfo.username, t);
			PlaySoundEffect(transactionSE);
			TextIO.putln("Transaction complete!");
		}
		else
		{
			TextIO.putln("Transaction aborted.");
		}

		}
		else
		{
			TextIO.putln("[Your balance is 0 chips. You don't have any chips to withdraw.]");
		}
	}
	
	public void ViewGameStats()
	{
		int inputCheck=0;
		TextIO.putln(accountInfo.username.toUpperCase()+"'s game stats:");
		blackJackStats.View();
		slotMachineStats.View();
		TextIO.putln();
		do
		{
			TextIO.putln("[1- View transaction history | 2- Return to main menu]");
			inputCheck = TextIO.getlnInt();
		}
		while (inputCheck !=1 && inputCheck!=2);

	}
	private void SignUp()
	{
	       TextIO.putln("Please type your age:");
	       accountInfo.age = TextIO.getlnInt();
           
	       if(accountInfo.age<18)
	       {
	    	   PlaySoundEffect(intro_securitySE);
	    	   TextIO.putln("YOU MUST BE OVER 18 YEARS OLD TO SIGN UP!");
	    	   do
	    	   {
	    		   // Waiting for sound effect to finish
	    	   }
	    	   while(soundEffectClip.isRunning());
	    	   System.exit(0);
	       }
	       else
	       {
		   do
		   {
	       do
	       {
			   TextIO.putln("Please type your Username:\r\nMust be between 6 and 16 of length:");
	           accountInfo.username=TextIO.getlnString();
	       } 
	       while(accountInfo.username.length()<6 ||  accountInfo.username.length()>16);

	       do
	       {
	           TextIO.putln("Please type your Password:\r\nMust be between 6 and 16 of length:");
	           accountInfo.password=TextIO.getlnString();
	       }
	       while(accountInfo.password.length()<6 ||  accountInfo.password.length()>16);
	        
		   do
		   {
				TextIO.putln("Please type your Credit Card Number:");
				accountInfo.ccn=TextIO.getlnInt();
		   }
		   while(accountInfo.ccn<10000000);

		   do
		   {
				TextIO.putln("Please type your Phone Number:");
				accountInfo.phone=TextIO.getlnInt();
		   }
		   while(accountInfo.phone<10000000);
		   
		   TextIO.putln("Please type your First Name:");
		   accountInfo.firstName=TextIO.getlnString();
		   TextIO.putln("Please type your Last Name:");
		   accountInfo.lastName=TextIO.getlnString();

	       SignUp = true;
	       FetchAccount();
		   }while(SignUpSuccess==false);
	       SignUp = false; // Sign-up complete, so behave like a signed-in account now
	       PlaySoundEffect(intro_endSE);
	       TextIO.putln("And..Perfect! I'll handle the rest of the paperwork now.\r\nWell, enjoy your evening, and remember, spend your money \"wisely\".");
	       }

	}
	private void SignIn()
	{
		do
		{
        do
        {
        TextIO.putln("Please type your Username:\r\nMust be between 6 and 16 of length:");
		tempuser = TextIO.getlnString();
        }
        while(tempuser.length()<6 || tempuser.length()>16);
        do
        {
        TextIO.putln("Please type your Password:\r\nMust be between 6 and 16 of length:");
		temppass = TextIO.getlnString();
        }
        while(temppass.length()<6 || temppass.length()>16);
        SignUp = false;
        FetchAccount();
		}
		while(SignInSuccess==false);
	}
}
