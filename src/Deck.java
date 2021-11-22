
public class Deck extends RandomGenerator {
	
	private int playerCount;
	private int bankCount;
	private boolean playerGotAce;
	private boolean bankGotAce;
	private boolean playerBlackJack;
	private boolean bankBlackJack;
	private int[] weights;
	
	public Deck()
	{
		super(1,13);
		weights = new int[13];
		FillWeights();
		playerGotAce=false;
		bankGotAce=false;
	}
	
	public void SetPlayerGotAce(boolean playerGotAce) {
		this.playerGotAce = playerGotAce;
	}

	public boolean PlayerGotAce() {
		return playerGotAce;
	}
	
	public void SetBankGotAce(boolean bankGotAce) {
		this.bankGotAce = bankGotAce;
	}

	public boolean BankGotAce() {
		return bankGotAce;
	}

	public void FillWeights()
	{
		for(int i=0; i<13; i++)
		{
			weights[i] = 4;  // There are 52 cards, 4 of each 1,2,3,4,5,6,7,8,9,10,Q,J,K (with Q,J,K having value of 10)
		}
	}
	
	public int GetPlayerCount() {
		return playerCount;
	}

	public void SetPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public int GetBankCount() {
		return bankCount;
	}

	public void SetBankCount(int bankCount) {
		this.bankCount = bankCount;
	}
	
	public boolean IsEmpty()
	{
		if((weights[0]+weights[1]+weights[2]+weights[3]+weights[4]+weights[5]+weights[6]+weights[7]+
				weights[8]+weights[9]+weights[10]+weights[11]+weights[12])==0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int GetRandomCard()
	{
		int randomCard;
		boolean repeat;
		do
		{
			repeat = false;
			randomCard = super.GetRandomNumber();
			if(weights[randomCard-1]==0)
			{
				repeat = true;
			}
		}
		while(repeat == true);
		weights[randomCard-1] = weights[randomCard-1] - 1;
        if(randomCard==11 || randomCard==12 || randomCard==13) // if card is Q , J or K then it has value of 10
        {
        	randomCard=10;
        }
		return randomCard;
	}
	
	public void CalculatePlayerCount(int randomCard)
	{
		if(randomCard == 1)
		{
			
			if(playerCount+11>21)
			{
				playerCount++;
			}
			else
			{
				playerCount+=11;
				playerGotAce=true;
			}
			
		}
		else
		{
			if(playerGotAce==true && playerCount+randomCard>21)
			{
			playerCount+=randomCard;
			playerCount-=10;
			playerGotAce=false;
			}
			else
			{
				playerCount+=randomCard;
			}
		}
		
			
	}
	
	public void CalculateBankCount(int randomCard)
	{
		if(randomCard == 1)
		{
			
			if(bankCount+11>21)
			{
				bankCount++;
			}
			else
			{
				bankCount+=11;
				bankGotAce=true;
			}
			
		}
		else
		{
			if(bankGotAce==true && bankCount+randomCard>21)
			{
			bankCount+=randomCard;
			bankCount-=10;
			bankGotAce=false;
			}
			else
			{
				bankCount+=randomCard;
			}
		}
			
	}
	
	public void CheckBlackJack(int playerCard1, int playerCard2, int bankCard1, int bankCard2)
	{
		if((playerCard1 == 1 && playerCard2 == 10) || (playerCard1 == 10 && playerCard2 == 1))
		{
		    playerBlackJack = true;
		}
		else
		{
			playerBlackJack = false;
		}
		if((bankCard1 == 1 && bankCard2 == 10) || (bankCard1 == 10 && bankCard2 == 1))
		{
		    bankBlackJack = true;
		}
		else
		{
			bankBlackJack = false;
		}
	}
	
	public boolean GetPlayerBackJack()
	{
		return playerBlackJack;
	}
	
	public boolean GetBankBackJack()
	{
		return bankBlackJack;
	}
}

