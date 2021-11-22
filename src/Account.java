import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Account implements Music {
	
	private String username;
	private String tempuser; // Temp username used for sign-in
	private String password;
	private String temppass; // Temp password used for sign-in
	private int chips;
	
	private int DBtotalindex; // Number of databases created containing Username/Password/Chips
	private int DBcurrentindex; // Index of currently used database 
	
	private boolean SignUp; // True for sign-up , False for sign-in
	private boolean SignInSuccess; // Checks if sign-in was successful or not
	private boolean SignUpSuccess; // Checks if sign-up was successful or not
	
	
	private AudioInputStream soundEffectStream; // Using the same AudioInputStream and the same Clip
    public Clip soundEffectClip;          // in order to have only 1 Sound Effect playing at a time
	
    public File musicFile; // Background Music
	public File introSE, intro_mistakeSE, intro_signupSE, intro_signinSE, intro_endSE,
	intro_mistake_signinSE, intro_securitySE, deposit_chipsSE, withdraw_chipsSE,
	bank_cardSE, transactionSE, blackjack_shufflingSE, game_betSE, game_roundSE,
	game_nochipsSE, blackjack_introSE, sm_introSE, exitSE; // Sound Effects 
	

	
    public Account()
    {
    	username="";
    	password="";
    	chips=0;
    	FetchDBTI();
    	FetchFiles();
    }
    
	public Account(String username, String password, int chips)
    {
        this.username=username;
        this.password=password;
        this.chips=chips;
        FetchDBTI();
        FetchFiles();
    }
	
	public int GetChips()
	{
		return chips;
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
	private void FetchDBCI()
	{
		if(SignUp == true)
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
				if(uniqueUser.equals(username)==true && uniquePass.equals(password)==true)
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
		else if(SignUp == false)
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
	
	public void UpdateDB()
	{
		String fileName = "db\\DB" + DBcurrentindex + ".casino";
		TextIO.writeFile(fileName);
		TextIO.putln(username);
		TextIO.putln(password);
		TextIO.putln(chips);
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
		username=TextIO.getln();
	    password=TextIO.getln();
	    chips=TextIO.getlnInt();
	    TextIO.readStandardInput();
	}
	
	public void UserChoice()
	{
		PlayMusic();
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
		
    public void ChangeChips(int x) // x should be: Positive if + / Negative if -
    {
    	chips = chips + x;
    }
	public void Deposit()
	{
		long bankNumber;
		int deposit;
		PlaySoundEffect(deposit_chipsSE);
		TextIO.putln("How many chips would you like to purchase?");
	    do
	    {
		   TextIO.putln("Please type the number of chips:\r\nMinimum purchase: 10 chips\r\nMaximum purchase: 5000 chips");
		   deposit=TextIO.getlnInt();
	    }
		while(deposit<10 || deposit>5000);
	    
	    PlaySoundEffect(bank_cardSE);
	    TextIO.putln("I just need you to insert your card here please.");
	    do
	    {
	    TextIO.putln("Please type your Bank Account / Card number:");
		bankNumber=TextIO.getlnLong();
		}
		while(bankNumber<10000000);
		
	    ChangeChips(deposit);
	    UpdateDB();
	    PlaySoundEffect(transactionSE);
	    TextIO.putln("Transaction complete!");
	    
	}
	public void Withdraw()
	{
		if(chips>0)
		{
		long bankNumber;
		int withdraw;
		PlaySoundEffect(withdraw_chipsSE);
	    TextIO.putln("Congratulations on your winnings! How many chips would you like to turn-in?");
	    do
	    {
        TextIO.putln("Please enter the number of chips:");
		withdraw=TextIO.getlnInt();
	    }
	    while(withdraw>chips);
	    PlaySoundEffect(bank_cardSE);
	    TextIO.putln("I just need you to insert your card here please.");
		do
		{
	    TextIO.putln("Please enter your Bank Account / Card number:");
		bankNumber=TextIO.getlnLong();
		}
		while(bankNumber<10000000);
		

	    ChangeChips(-withdraw);
	    UpdateDB();
	    PlaySoundEffect(transactionSE);
	    TextIO.putln("Transaction complete!");
		}
		else
		{
			TextIO.putln("[Your balance is 0 chips. You don't have any chips to withdraw.]");
		}
	}
	private void SignUp()
	{
	       int age;
	       TextIO.putln("Please type your age:");
	       age = TextIO.getlnInt();
           
	       if(age<18)
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
			   TextIO.putln("Please type your Username:\r\nMust be between 4 and 10 of length:");
	           username=TextIO.getlnString();
	       } 
	       while(username.length()<4 ||  username.length()>10);

	       do
	       {
	           TextIO.putln("Please type your Password:\r\nMust be between 8 and 16 of length:");
	           password=TextIO.getlnString();
	       }
	       while(password.length()<8 ||  password.length()>16);
	        
	       SignUp = true;
	       FetchDBCI();
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
        TextIO.putln("Please type your Username:\r\nMust be between 4 and 10 of length:");
		tempuser = TextIO.getlnString();
        }
        while(tempuser.length()<4 || tempuser.length()>10);
        do
        {
        TextIO.putln("Please type your Password:\r\nMust be between 8 and 16 of length:");
		temppass = TextIO.getlnString();
        }
        while(temppass.length()<8 || temppass.length()>16);
        SignUp = false;
        FetchDBCI();
		}
		while(SignInSuccess==false);
	}
}
