import java.util.HashMap;
import java.util.HashSet;

public class PerplexityForBigram {
	HashMap<String,Integer> wordsWithCount= new HashMap<String,Integer>();
	HashSet<String> vocabulary = new HashSet<String>();
	HashMap<Integer,Word> allofWords= new HashMap<Integer,Word>();
	HashMap<String,Integer> countofTwoWords = new HashMap<String,Integer>();
	HashMap<Integer,String> countofTest = new HashMap<Integer,String>();
	HashSet<Double> probabilities = new HashSet<Double>();
	HashMap<Integer,Word> testSet= new HashMap<Integer,Word>();
	public PerplexityForBigram(HashMap<Integer,Word> allofWords,HashMap<Integer,Word> testSet,HashMap<String,Integer> wordsWithCount,HashSet<String> vocabulary){
		this.wordsWithCount= wordsWithCount;
		this.vocabulary=vocabulary;
		this.allofWords=allofWords;
		this.testSet=testSet;
		addTwoWordForTrain();
		
	   
		searchinTrain();
		
	}
	public void addTwoWordForTrain(){
		for(int i=0;i<allofWords.size();i++){
			if(i+1<allofWords.size()){
				String word1= allofWords.get(i).getWord();
				String word2= allofWords.get(i+1).getWord();
				String key = word1+" "+word2;
				if(!countofTwoWords.containsKey(key)){
					countofTwoWords.put(key, 1);
				}
				else{
					countofTwoWords.put(key,countofTwoWords.get(key)+1);
				}
			}
			
			
			
		}
	}

	public int getCountofoneWord(int index){
		int count=0;
	    String word= testSet.get(index).getWord();
		if(wordsWithCount.containsKey(word)){
		
			count= wordsWithCount.get(word);
		
		
		}
		return count;
		
	}
	public void searchinTrain(){
		for(int i=0;i<testSet.size()-2;i++){
			if(i+1<testSet.size()){
				
				String words= testSet.get(i).getWord()+" "+testSet.get(i+1).getWord();
			
				if(countofTwoWords.containsKey(words)){
					
					findProbability(countofTwoWords.get(words),i);
				}
				else{
				
					doSmoothing(i);
				}
			}
			
		}
		calculateProbability();
	}
	public void doSmoothing(int firstIndex){
		double probability=(double)1/(getCountofoneWord(firstIndex)+vocabulary.size());
		probabilities.add(Math.log(probability)/Math.log(2));
		
	}
	public void findProbability(int countForTwo,int i){
		double probability= (double)countForTwo/getCountofoneWord(i);
		probabilities.add(Math.log(probability)/Math.log(2));
	}
	public void calculateProbability(){
		double probability=0;
		java.util.Iterator<Double> itr = probabilities.iterator();
		while(itr.hasNext()){
			probability=probability+itr.next();
		}
		printPerplexity(probability);
	}
	public void printPerplexity(double probability){
		double pow= (double)probability/probabilities.size();
		System.out.println("Perplexity of Bigram: "+Math.pow(2, -pow));
	}
	public HashMap<String,Integer> getCountofTwoWords(){
		return countofTwoWords;
	}
}
