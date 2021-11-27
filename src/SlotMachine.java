
public class SlotMachine extends Game{
 
	private	SlotNumber sn;
	boolean win;
	boolean repeatInit;
	int firstNumber;
	int secondNumber;
	int thirdNumber;

public SlotMachine(Account player)
{
	super(player,10);
	sn = new SlotNumber();
	win = false;
	repeatInit = false;
	firstNumber = 0;
	secondNumber = 0;
	thirdNumber = 0;
	
	
}
public void TutorialChoice()
{
	TextIO.putln("[Would you like to go through a tutorial of Slot Machine?]\r\n[1- No, I know how Slot Machine works | 2- Yes]");
	int tutorialDecision;
	do
	{
		tutorialDecision=TextIO.getlnInt();
	}
	while(tutorialDecision<1 || tutorialDecision>2);
	if(tutorialDecision==1)
	{
		StartGame();
	}
	else
	{
		TextIO.putln("[Goal of the game:] Slot Machine is luck based. Roll combinations to win.\r\n"
				+ "[How it works:] You pick an amount to bet, then roll 3 numbers ranging\r\nfrom 0 to 9.\r\n"
				+ "[Combination 1:] All numbers are even: You win 2x bet.\r\n"
				+ "[Combination 2:] All numbers are odd: You win 3x bet.\r\n"
				+ "[Combination 3:] 1st number + 2nd number = 3rd number: You win 10x bet.\r\n"
				+ "[Special Combination 1:] Lucky strike 7 7 7 => You win 1000x bet.\r\n"
				+ "[In case of no combination:] You lose your bet.");
		TextIO.putln("[1- Start Slot Machine | 2- Back to Main Menu]");
		int input;
		do
		{
			input=TextIO.getlnInt();
		}
		while(input !=1 && input !=2);
		if(input == 1)
		{
			StartGame();
		}
	}
}

public void StartGame()

{	
	player.PlaySoundEffect(player.sm_introSE);
	TextIO.putln("Welcome to our Slot Department! We have the best machines in town!");
	boolean repeatGame=false;
	boolean changeBet=true;
	do
	{
	if(player.GetBalance()>super.GetMinBet())
	{
		 
		if(changeBet==true)
		{
		super.Betting();
		changeBet=false;
		}

	do {
		win=false;
		repeatInit=false;
		sn.CalculateProbability();
	    firstNumber = sn.GetRandomNumber();
	    secondNumber = sn.GetRandomNumber();
	    thirdNumber = sn.GetRandomNumber();
	if(( firstNumber + secondNumber== thirdNumber) &&(sn.GetProbability()>25))
	{
		repeatInit=true;
		
	}
	else if((firstNumber % 2 == 0) && (secondNumber % 2 == 0 )&& (thirdNumber %2 == 0)&&(sn.GetProbability()>75))
	{
		repeatInit=true;
	}
	else if((firstNumber % 2 != 0) && (secondNumber % 2 != 0 )&& (thirdNumber %2 != 0)&&(sn.GetProbability()>50))
	{
		repeatInit=true;
	}
	}while(repeatInit==true);
	
	TextIO.putln("    _______________");
	TextIO.putln("   |  ("+ firstNumber + ") ("  + secondNumber + ") (" + thirdNumber + ")  |");
	TextIO.putln("    ���������������");
	
	if((firstNumber==7)&&(secondNumber==7)&&(thirdNumber==7))
	{
		TextIO.putln("[LUCKY STRIKE! YOU WIN 1000x YOUR BET: " + super.GetPlayerBet()*1000 + " CHIPS!]");
		player.ChangeBalance(super.GetPlayerBet()*1000-super.GetPlayerBet());
		win=true;
	}
	else if ((firstNumber==4)&&(secondNumber==0)&&(thirdNumber==4))
	{
		TextIO.putln("[Bet not found.. I guess?]\r\n[You win your bet back: " + super.GetPlayerBet() + " chips!]");
		win=true;
	}	
	else
	{
if((firstNumber % 2 == 0) && (secondNumber % 2 == 0 )&& (thirdNumber %2 == 0))
{
	TextIO.putln("[ALL EVEN! You win 2x your bet: " + super.GetPlayerBet()*2 + " chips!]");
	player.ChangeBalance(super.GetPlayerBet()*2-super.GetPlayerBet());
	win=true;
}
if( firstNumber + secondNumber== thirdNumber)
{
	TextIO.putln("[SUM COMBO! You win 10x your bet: " + super.GetPlayerBet()*10 + " chips!]");
	player.ChangeBalance(super.GetPlayerBet()*10-super.GetPlayerBet());
	win=true;
}
if((firstNumber % 2 != 0) && (secondNumber % 2 != 0 )&& (thirdNumber %2 != 0))
{
	TextIO.putln("[ALL ODD! You win 3x your bet: " + super.GetPlayerBet()*3 + " chips!]");
	player.ChangeBalance(super.GetPlayerBet()*3-super.GetPlayerBet());
	win=true;
}
	}
	if(win==false)
	{	
		TextIO.putln("[Unlucky! No winning combination.. You lose your bet: " + super.GetPlayerBet() + " chips!]");
		player.ChangeBalance(-super.GetPlayerBet());
	}
	player.UpdateDB();
	player.PlaySoundEffect(player.game_roundSE);
	TextIO.putln("So.. Another round? [Your current balance: " + player.GetBalance() + " chips]\r\n[1- Yes, keep the same bet | 2- Yes, but change the bet | 3- No]");
    int repeatDecision;
	boolean mistake = false;
		do
		{
			if(mistake==true)
			{
				player.PlaySoundEffect(player.intro_mistakeSE);
				TextIO.putln("I apologize.. I don't think I can help with that.\r\nAny other requests? [1- Yes, keep the same bet | 2- Yes, but change the bet | 3- No]");
			}
			repeatDecision = TextIO.getlnInt();
			if(repeatDecision<1 && repeatDecision>3)
			{
				mistake=true;
			}
		}
		while(repeatDecision<1 && repeatDecision>3);
		
		if(repeatDecision==1 || repeatDecision==2)
		{
			if(player.GetBalance()>=super.GetMinBet())
			{
				repeatGame=true; 
				if(repeatDecision==2)
            {
            	changeBet=true;
            }
            else if(repeatDecision==1 && super.GetPlayerBet()>player.GetBalance())
            {
            	TextIO.putln("[Not enough chips to keep the same bet. Please change it ahead]");
            	changeBet=true;
            }
			}
			else
			{
				int depositDecision;
				player.PlaySoundEffect(player.game_nochipsSE);
				TextIO.putln("It seems like you don't have enough chips anymore.\r\n[Current balance: "
				+ player.GetBalance() + "chips]\r\n[Minimum bet is: "
				+ super.GetMinBet() + "chips]\r\nWould you like to purchase more right now? [1- Yes | 2- No]");
				boolean mistake2 = false;
		   		do
		   		{
		   			if(mistake2==true)
		   			{
		   				player.PlaySoundEffect(player.intro_mistakeSE);
		   				TextIO.putln("I apologize.. I don't think I can help with that.\r\nAny other requests? [1- Yes | 2- No]");
		   			}
		   			depositDecision = TextIO.getlnInt();
		   			if(depositDecision!=1 && depositDecision!=2)
		   			{
		   				mistake2=true;
		   			}
		   		}
		   		while(depositDecision!=1 && depositDecision!=2);
		   		if(depositDecision==1)
		   		{
		   			player.Deposit();
		   			repeatGame=true;
		   			changeBet=true;

		   		}
		   		else
		   		{
		   			repeatGame=false;
		   		}
			}
		}
		else if(repeatDecision == 3)
		{
			repeatGame=false;
		}
		
   }
	
	else
	{
		player.PlaySoundEffect(player.game_nochipsSE);
		TextIO.putln("It seems like you don't have enough chips anymore.\r\n[Current balance: "
		+ player.GetBalance() + "chips]\r\n[Minimum bet is: "
		+ super.GetMinBet() + "chips]\r\nWould you like to purchase more right now? [1- Yes | 2- No]");
		boolean mistake = false;
		int depositDecision;
   		do
   		{
   			if(mistake==true)
   			{
   				player.PlaySoundEffect(player.intro_mistakeSE);
   				TextIO.putln("I apologize.. I don't think I can help with that.\r\nAny other requests? [1- Yes | 2- No]");
   			}
   			depositDecision = TextIO.getlnInt();
   			if(depositDecision!=1 && depositDecision!=2)
   			{
   				mistake=true;
   			}
   		}
   		while(depositDecision!=1 && depositDecision!=2);
   		if(depositDecision==1)
   		{
   			player.Deposit();
   			repeatGame=true;
   			changeBet=true;

   		}
   		else
   		{
   			repeatGame=false;
   		}
	}
	
	}while(repeatGame==true);

}


}

