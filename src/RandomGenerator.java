import java.util.Random; 
public abstract class RandomGenerator {
  private Random random;  
  private int max, min;
	
	public RandomGenerator(int min, int max)
	{	
		random = new Random();
		this.min = min;
		this.max = max;
	}
	  
	public int GetRandomNumber()
	{     
		// Generate random integers in range from min to max inclusive
	    return random.nextInt((max - min) + 1) + min;
	}	
	
	public int GetRandomNumber(int externalMin, int externalMax)
	{
		return random.nextInt((externalMax - externalMin) + 1) + externalMin;
	}
	
	
	
}
