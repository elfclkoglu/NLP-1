import java.util.Random;

public class RandomGenerator {
	
	private double upper=1.0;
	private double lower=0.0;
	public RandomGenerator(){
		
	}
	public double getRandom(){
		Random r = new Random();
		double random =lower + (upper - lower) * r.nextDouble();
		return random;
	}
	
}
