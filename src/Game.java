
public abstract class Game{
	
    protected Account player;
	private int playerBet;
    private int minBet;
    private int betDecision;
    
    public Game(Account player, int minBet)
    {
    	this.player = player;
    	playerBet = -1;
    	this.minBet = minBet;
    	betDecision = -1;
    }
    
	public void SetPlayerBet(int playerBet) {
		this.playerBet = playerBet;
	}

	public void SetBetDecision(int betDecision) {
		this.betDecision = betDecision;
	}
	
	public int GetPlayerBet() {
		return playerBet;
	}

	public int GetBetDecision() {
		return betDecision;
	}

	public int GetMinBet()
	{
		return minBet;
	}
	
	public void Betting()
	{
		while(player.soundEffectClip.isRunning())
		{
			//Wait for last sound effect to end
		}
		player.PlaySoundEffect(player.game_betSE);
    	TextIO.putln("How much would you like to bet? [1- Pick an amount | 2- All-in]");
		
		boolean mistake = false;
		do
		{
			if(mistake==true)
			{
				player.PlaySoundEffect(player.intro_mistakeSE);
				TextIO.putln("I apologize.. I don't think I can help with that.\r\nAny other requests? [1- Pick an amount | 2- All-in]");
			}
			betDecision = TextIO.getlnInt();
			if(betDecision!=1 && betDecision!=2)
			{
				mistake=true;
			}
		}
		while(betDecision!=1 && betDecision!=2);
		if(betDecision==1)
		{
			TextIO.putln("Please type an amount to bet:\r\nYou currently have " + player.GetChips() + " chips:\r\nMinimum bet is " + minBet + " chips:");
			do
			{
				playerBet = TextIO.getlnInt();
			}
			while(playerBet<minBet || playerBet>player.GetChips());
		}
		else if(betDecision==2)
		{
			playerBet = player.GetChips();
		}
	}

	public abstract void TutorialChoice();
	public abstract void StartGame();
}
