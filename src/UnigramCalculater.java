import java.util.ArrayList;
import java.util.HashMap;

public class UnigramCalculater {
	HashMap<Integer,Word> allofWords = new HashMap<Integer,Word>();
	HashMap<String,Double> vocabularyWithCounts= new HashMap<String,Double>();
	ArrayList<String> wordsofSentence = new ArrayList<String>();
	ArrayList<Double> probabilities = new ArrayList<Double>();  
	public UnigramCalculater(ArrayList<String> wordsofSentence,HashMap<String,Double> vocabularyWithCounts,HashMap<Integer,Word> allofWords){
		this.allofWords=allofWords;
		this.vocabularyWithCounts=vocabularyWithCounts;
		this.wordsofSentence=wordsofSentence;
		findProbabilities();
		
	}
	public void findProbabilities(){
		double probability=0.0;
		for(int i=0;i<wordsofSentence.size();i++){
			String word = wordsofSentence.get(i);
			if(vocabularyWithCounts.containsKey(word)){
				probability= vocabularyWithCounts.get(word)/allofWords.size();
				probabilities.add(probability);
			}
		}
		calculateProbability();
	}
	public void calculateProbability(){
		double probability=1;
		for(int i=0;i<probabilities.size();i++){
			probability=probability*probabilities.get(i);
		}
		System.out.println("---->Probability of this sentence: "+probability);
		System.out.println();
	}
	
	
}
