public class WinAchievements {

    private int winnings;
	private int streak;
    private GameStats stats;

    public WinAchievements(GameStats stats)
    {
        winnings = 0;
        streak = 0;
        this.stats = stats;
    }

    public void CheckWinAchievements(boolean hasWon, int bet)
	{
		if(hasWon)
		{
			winnings = bet;
			streak++;
		}
		else
		{
			winnings = 0;
			streak = 0;
		}

		if(winnings > stats.largestWin)
		{
			stats.largestWin = winnings;
		}

		if(streak >stats.longestStreak)
		{
			stats.longestStreak = streak;
		} 
	}
}
