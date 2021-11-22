
public class SlotNumber extends RandomGenerator {

	private int probability;
	public SlotNumber()
	{
		super(0,9);
		probability=0;
	}
	
	public void CalculateProbability()
	{
		    probability = super.GetRandomNumber(0, 100);
	}

	public int GetProbability() {
		return probability;
	}
	
	
}
