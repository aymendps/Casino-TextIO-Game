
public class GameStats {
		
	String gameName;
	int timePlayed; // In minutes.
	int gamesWon;
	int gamesLost;
	int largestWin;
	int longestStreak;
	
	public GameStats(String gameName, int timePlayed, int gamesWon, int gamesLost ,int largestwin, int longestStreak)
	{
		this.gameName = gameName; 
		this.timePlayed = timePlayed; 
		this.gamesWon = gamesWon; 
		this.gamesLost = gamesLost; 
		this.largestWin = largestwin; 
		this.longestStreak = longestStreak;
	}
		
	public GameStats(String gameName)
	{
		
		this.gameName = gameName; 
		this.timePlayed = 0; 
		this.gamesWon = 0; 
		this.gamesLost = 0; 
		this.largestWin = 0; 
		this.longestStreak = 0;
	}

}
