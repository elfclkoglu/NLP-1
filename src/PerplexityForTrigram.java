import java.util.HashMap;
import java.util.HashSet;

public class PerplexityForTrigram {
	HashMap<String,Integer> wordsWithCount= new HashMap<String,Integer>();
	HashSet<String> vocabulary = new HashSet<String>();
	HashMap<Integer,Word> allofWords= new HashMap<Integer,Word>();
	HashSet<Double> probabilities = new HashSet<Double>();
	HashMap<String,Integer> countofTrain = new HashMap<String,Integer>();
	HashMap<String,Integer> countofTwoWords = new HashMap<String,Integer>();
	HashMap<Integer,Word> testSet= new HashMap<Integer,Word>();
	public PerplexityForTrigram(HashMap<Integer,Word> allofWords,HashMap<Integer,Word> testSet,HashMap<String,Integer> wordsWithCount,HashSet<String> vocabulary,HashMap<String,Integer> countofTwoWords){
		this.wordsWithCount= wordsWithCount;
		this.vocabulary=vocabulary;
		this.allofWords=allofWords;
		this.testSet=testSet;
		this.countofTwoWords=countofTwoWords;
		addThreeWordForTrain();
		searchinTrain();
	}
	public void addThreeWordForTrain(){
		for(int i=0;i<allofWords.size();i++){
			if(i+2<allofWords.size()){
				String word1= allofWords.get(i).getWord();
				String word2= allofWords.get(i+1).getWord();
				String word3=allofWords.get(i+2).getWord();
				String key = word1+" "+word2+" "+word3;
				if(!countofTrain.containsKey(key)){
					countofTrain.put(key, 1);
				}
				else{
					countofTrain.put(key,countofTrain.get(key)+1);
				}
			}	
		}
	}
	public void searchinTrain(){
		for(int i=0;i<testSet.size()-2;i++){
			if(i+2<testSet.size()){
				String words= testSet.get(i).getWord()+" "+testSet.get(i+1).getWord()+" "+testSet.get(i+2).getWord();
		
			    if(countofTrain.containsKey(words)){
			    	findProbability(countofTrain.get(words),i);
			    }
			    else{
			
				    doSmoothing(i,i+1);
			    }
		    }
		
	    }
	    calculateProbability();
    }
    public void doSmoothing(int firstIndex,int secondIndex){
	    double probability=(double)1/(getCountofTwoWords(firstIndex,secondIndex)+vocabulary.size());
	    probabilities.add(Math.log(probability)/Math.log(2));
	
    }
    public void findProbability(int countForThree,int i){
	    double probability= (double)countForThree/getCountofTwoWords(i,i+1);
	    probabilities.add(Math.log(probability)/Math.log(2));
    }
    public int getCountofTwoWords(int first,int second){
		int count=0;
	    String word1= testSet.get(first).getWord();
	    String word2= testSet.get(second).getWord();
	    String word=word1+" "+word2;
		if(countofTwoWords.containsKey(word)){
		
			count= countofTwoWords.get(word);
		
		
		}
		return count;
		
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
	    System.out.println("Perplexity of Trigram: "+Math.pow(2, -pow));
    }
	
}
