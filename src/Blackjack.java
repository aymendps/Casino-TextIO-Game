
public class Blackjack extends Game{
   
	private Deck deck;
	private boolean betComplete;
    private boolean doThisOnce;
    private boolean roundDone;
    private boolean playerState;
	private int random1p;
	private int random2p;
	private int random1b;
	private int random2b;
	private WinAchievements winAchievements;

    public Blackjack(Account player)
    {
    	super(player,50);
    	deck = new Deck();
    	betComplete = false;
    	doThisOnce = false;
    	roundDone = false;
    	playerState = true;
		winAchievements = new WinAchievements(player.blackJackStats);
    }

    private int PlayerDrawCard()
    {
    	if(deck.IsEmpty()==true)
    	{
    		player.PlaySoundEffect(player.blackjack_shufflingSE);
    		TextIO.putln("Seems like we are out of cards.. Let me use another deck.");
    		TextIO.putln("[Shuffling and changing decks...]");
    		deck.FillWeights();
    	}
    	int drawCard = deck.GetRandomCard();
    	deck.CalculatePlayerCount(drawCard);
    	return drawCard;
    }
    
    private int BankDrawCard()
    {
    	if(deck.IsEmpty()==true)
    	{
    		player.PlaySoundEffect(player.blackjack_shufflingSE);
    		TextIO.putln("Seems like we are out of cards.. Let me use another deck.");
    		TextIO.putln("[Shuffling and changing decks...]");
    		deck.FillWeights();
    	}
    	int drawCard = deck.GetRandomCard();
    	deck.CalculateBankCount(drawCard);
    	return drawCard;
    }
    
    private void ResetRound()
    {
        roundDone=false;
   		playerState=true;
   		doThisOnce=false;
   		deck.SetPlayerGotAce(false);
   		deck.SetBankGotAce(false);
   		deck.SetPlayerCount(0);
   		deck.SetBankCount(0);
   		random1p = PlayerDrawCard();
   		random2p = PlayerDrawCard();
   		random1b = BankDrawCard();
   		random2b = BankDrawCard();
   		deck.CheckBlackJack(random1p, random2p, random1b, random2b);
    }
    
    public void TutorialChoice()
    {
    	TextIO.putln("[Would you like to go through a tutorial of Blackjack?]\r\n[1- No, I know how to play Blackjack | 2- Yes, I am new to Blackjack]");
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
    		TextIO.putln("[Goal of the game:] Beat the dealer's hand without going over 21.\r\n"
    				+ "[Rule 0:] Played with a deck of 52 cards, 4 of each: 1,2,3,4,5,6,7,8,9,10,Q,J,K\r\n"
    				+ "[Rule 1:] Face cards are worth 10. Aces are worth 1 or 11, whichever makes a\r\nbetter hand.\r\n"
    				+ "[Rule 2:] Player starts with 2 cards, one of the dealer's cards is hidden until the end.\r\n"
    				+ "[Rule 3:] To 'Hit' is to ask for a card. To 'Stand' is to hold your total and\r\nend turn.\r\n"
    				+ "[Rule 4:] If you go over 21 you lose, and the dealer wins regardless.\r\n"
    				+ "[Rule 5:] If you are dealt 21 from the start (Ace & 10), you got a Blackjack.\r\n"
    				+ "[When you get a Blackjack:] You win 1.5 the amount of your bet.\r\n"
    				+ "[Rule 6:] Dealer will hit until his/her cards total 17 or higher.\r\n"
    				+ "[Rule 7:] 'Double' is like a 'Hit', only the bet is doubled and you only get one more card.");
    		TextIO.putln("[1- Start Blackjack | 2- Back to Main Menu]");
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
	    player.PlaySoundEffect(player.blackjack_introSE);
		TextIO.putln("Welcome to Blackjack! Please have a seat.");
	   int playerDecision=0;
	   int repeatDecision;
	   boolean repeat=false;
	   boolean changeBet=true;
       do
       {
    	   if(player.GetBalance()>super.GetMinBet())
    	   {   
	   do
         {
       	
               if(betComplete==false)
               {
      
            	ResetRound();
            	if(changeBet==true)
            	{
               	super.Betting();
               	changeBet=false;
            	}
               	
               	TextIO.putln("[The dealer draws you the first card:] " + random1p);
           		TextIO.putln("[The dealer draws you the second card:] " + random2p);
           		TextIO.putln("[Total count of your cards is:] " + deck.GetPlayerCount());
           		TextIO.putln("[The dealer draws two cards, one is hidden the other is shown:] " + random2b);
           		
           		betComplete=true;
               }
       	    
       	    if(playerState==true)
       	{
           	if(deck.GetPlayerBackJack()==true && deck.GetBankBackJack()==false)
           	{
           		roundDone=true;
           		TextIO.putln("[Lucky! You got a Blackjack! ROUND WON]");
				winAchievements.CheckWinAchievements(true, super.GetPlayerBet()+ super.GetPlayerBet()/2);
           		player.ChangeBalance(super.GetPlayerBet()+ super.GetPlayerBet()/2);
				player.SQLdb.UpdateAccount(player.GetBalance(), player.GetUsername());
				player.SQLdb.UpdateGame(player.blackJackStats, player.GetUsername(), true);
           		betComplete=false;
           	}
           	else if(deck.GetPlayerBackJack()==true && deck.GetBankBackJack()==true)
           	{
           		roundDone=true;
               	TextIO.putln("[The dealer reveals the hidden card:] " + random1b);
             	TextIO.putln("[Total count of opposition cards is:] " + deck.GetBankCount());
           		TextIO.putln("[You got a Blackjack! But sadly the dealer matches it. ROUND TIED]");
           		betComplete=false;
           	}
           	else
           	{
           	if(player.GetBalance()>=super.GetPlayerBet()*2)
           	{
           		TextIO.putln("[The dealer is waiting for your decision: 1: Hit | 2: Stand | 3: Double]");
           		do
                 	{
                 		playerDecision=TextIO.getlnInt();
                 	}
                 	while(playerDecision<1 || playerDecision>3);
           	}
           	else
           	{
           		TextIO.putln("[The dealer is waiting for your decision: 1: Hit | 2: Stand]");
           		do
                 	{
                 		playerDecision=TextIO.getlnInt();
                 	}
                 	while(playerDecision<1 || playerDecision>2);
           	}
           	
             	if(playerDecision==1)
             	{
           	int newPlayerCard = PlayerDrawCard();
       		TextIO.putln("[The dealer draws you a card:] " + newPlayerCard + "\r\n[Total count of your cards is:] " + deck.GetPlayerCount());
           	if(deck.GetPlayerCount()>21)
           	{
           		roundDone=true;
           		TextIO.putln("[Total count of your cards exceeds 21: ROUND LOST]");
				winAchievements.CheckWinAchievements(false, 0);
           		player.ChangeBalance(-super.GetPlayerBet());
				player.SQLdb.UpdateAccount(player.GetBalance(), player.GetUsername());
				player.SQLdb.UpdateGame(player.blackJackStats, player.GetUsername(), false);
           		betComplete=false;
           	}
             	}
             	else if(playerDecision==2)
             	{
             		playerState=false;
             	}
             	else if(playerDecision==3)
             	{
             		int newPlayerCard = PlayerDrawCard();
           		    super.SetPlayerBet(super.GetPlayerBet()*2);
           		    
           		    TextIO.putln("[You double your bet for this round. It becomes:] " + super.GetPlayerBet());
           		    TextIO.putln("[You draw the decisive card:] " + newPlayerCard + "\r\n[Total count of your cards is:] " + deck.GetPlayerCount() );
             		playerState=false;
             		
               	if(deck.GetPlayerCount()>21)
               	{
               		roundDone=true;
               		TextIO.putln("[Total count of your cards exceeds 21: ROUND LOST]");
					winAchievements.CheckWinAchievements(false, 0);
               		player.ChangeBalance(-super.GetPlayerBet());
					player.SQLdb.UpdateAccount(player.GetBalance(), player.GetUsername());
					player.SQLdb.UpdateGame(player.blackJackStats, player.GetUsername(), false);
           		
               		betComplete=false;
               	}
             	}
           	}
             	

       		

       	}
       	else if(roundDone==false)
       	{
       		
         	if(doThisOnce==false)
         	{
           	TextIO.putln("[The dealer reveals the hidden card:] " + random1b);
         	TextIO.putln("[Total count of opposition cards is:] " + deck.GetBankCount());
         	doThisOnce=true;
         	}
       		if(deck.GetBankCount()>21)
           	{
           		roundDone=true;
           		TextIO.putln("[Total count of opposition cards exceeds 21: ROUND WON]");
				winAchievements.CheckWinAchievements(true, super.GetPlayerBet());
           		player.ChangeBalance(super.GetPlayerBet());
				player.SQLdb.UpdateAccount(player.GetBalance(), player.GetUsername());
				player.SQLdb.UpdateGame(player.blackJackStats, player.GetUsername(), true);
           		betComplete=false;
           	}
           	else if((deck.GetBankCount()>deck.GetPlayerCount()) && deck.GetBankCount()>=17)
           	{
           		roundDone=true;
           		TextIO.putln("[Total count of opposition cards exceeds total count of your cards: ROUND LOST]");
				winAchievements.CheckWinAchievements(false, 0);
           		player.ChangeBalance(-super.GetPlayerBet());
				player.SQLdb.UpdateAccount(player.GetBalance(), player.GetUsername());
				player.SQLdb.UpdateGame(player.blackJackStats, player.GetUsername(), false);
           		betComplete=false;
           	}
           	else if((deck.GetBankCount()==deck.GetPlayerCount()) && deck.GetBankCount()>=17)
           	{
           		roundDone=true;
           		TextIO.putln("[Total count of opposition cards = Total count of your cards: ROUND TIED]");
           		betComplete=false;
           	}
           	if(roundDone==false)
           	{
       		int newBankCard = BankDrawCard();
       		TextIO.putln("[The dealer draws a card: " + newBankCard);
       		TextIO.putln("[Total count of opposition cards is:] " + deck.GetBankCount() );
           	}
           	
       	}    	
         }
       while(roundDone==false);
       
	    //player.UpdateDB();
        player.PlaySoundEffect(player.game_roundSE);
    	TextIO.putln("So.. Another round? [Your current balance: " + player.GetBalance() + " chips]\r\n[1- Yes, keep the same bet | 2- Yes, but change the bet | 3- No]");
    	
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
   				repeat=true; 
                if(repeatDecision==1 && playerDecision==3)
                {
                	super.SetPlayerBet(super.GetPlayerBet()/2);
                }
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
   		   			repeat=true;
   		   			changeBet=true;

   		   		}
   		   		else
   		   		{
   		   			repeat=false;
   		   		}
   			}
   		}
   		else if(repeatDecision == 3)
   		{
   			repeat=false;
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
    	   			repeat=true;
    	   			changeBet=true;

    	   		}
    	   		else
    	   		{
    	   			repeat=false;
    	   		}
    	   }
    	   
    	   
       }
       while(repeat==true);
   
	}
      
	
	
	
	
}
