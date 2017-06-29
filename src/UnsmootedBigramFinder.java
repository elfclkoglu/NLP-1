import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

public class UnsmootedBigramFinder {
	HashMap<Integer,Word> allofWords=new HashMap<Integer,Word>();
	HashMap<Double, String> headsWithProbability=new HashMap<Double,String>();
	ArrayList<String> nextWords;
	HashMap<Double,String> nextWordsWithIndex;
	ArrayList<String> wordsofSentence;
	HashSet<String> controllerSet;
	
	private SortedSet<Double> keys ;
	private SortedSet<Double> keysofHeads ;
	private int count=0;
	Searcher searcher;
	SmoothedBigram smoothedBigram;
	UnsmootedBigramFinder(HashMap<Integer,Word> allofWords,Searcher searcher){
		this.allofWords=allofWords;
		
		this.searcher=searcher;
		headsWithProbability=searcher.getHeadsWithProbability();
		smoothedBigram= new SmoothedBigram(allofWords,searcher);
		
	}
	
	public void createSentence(){
		
		RandomGenerator random = new RandomGenerator();
		
		wordsofSentence = new ArrayList<String>();
		addFirstWord(random);
	    for(int i=0;i<19;i++){
	    	int wordIndex=wordsofSentence.size()-1;
	    	findProbabilities(findNexts(wordIndex));
	    	chooseNextWord(random);
	    }
	    printSentence();
		
		
		
	}
	public void findProbabilities(ArrayList<String> nextWords){
		Double probability=0.0;
		nextWordsWithIndex= new HashMap<Double,String>();
		controllerSet = new HashSet<String>();
		int freq=0;
		
		for(int i=0;i<nextWords.size();i++){
			String word=nextWords.get(i);
			freq=Collections.frequency(nextWords,word);
			
			if(!controllerSet.contains(word)){
				probability=probability+((double)freq/nextWords.size());
				nextWordsWithIndex.put(probability, word);
				controllerSet.add(word);
			}
		}
	}
	public void printSentence(){
		String sentence="";
		for(int i=0;i<wordsofSentence.size();i++){
			if(i==0){
				System.out.print(setUpperCase(wordsofSentence.get(i))+" ");
	
			}
			else{
				System.out.print(wordsofSentence.get(i)+" ");
			
			}
			if(wordsofSentence.get(i).matches("[A-Za-z0-9]+[.?!]")){
				sentence= sentence+wordsofSentence.get(i)+" ";
				break;
			}
			else{
				sentence= sentence+wordsofSentence.get(i)+" ";
			}
			
		}
		System.out.println();
	    findProbability(sentence);
		
	}
	
		
	
	public void findProbability(String sentence){
		System.out.print("---->Probability of this sentence: ");
		smoothedBigram.parse(sentence);
		System.out.println();
	}
	public ArrayList<String> findNexts(int wordIndex){
		nextWords=new ArrayList<String>();
		String nextWord=null;
		String previousWord= wordsofSentence.get(wordIndex);
		for(int i=0;i<allofWords.size();i++){
			if(i+1!=allofWords.size())
				nextWord=allofWords.get(i+1).getWord();
			if(allofWords.get(i).getWord().equals(previousWord)){
					nextWords.add(nextWord);
			}
		}	
		return nextWords;
	}
	public void chooseNextWord(RandomGenerator random){
		keys = new TreeSet<Double>(nextWordsWithIndex.keySet());
		
		for(Double key:keys){
			double marker =random.getRandom();
			String word= nextWordsWithIndex.get(key);
			
			if(marker<key){
				wordsofSentence.add(word);
				break;
			}
		
		}
	}

	
	public void addFirstWord(RandomGenerator random){
		keysofHeads=new TreeSet<Double>(headsWithProbability.keySet());
		for(Double key:keysofHeads){
			double marker =random.getRandom();
			String word = headsWithProbability.get(key);
			if(marker<key){	
				wordsofSentence.add(word);
				break;
			}
			
		}
	}
	public String setUpperCase(String word){
		String upperWord=Character.toString(word.charAt(0)).toUpperCase(Locale.ENGLISH)+word.substring(1);
		return upperWord;
	}
	public String setLowerCase(String word){
		String lowerWord = word.toLowerCase();
		return lowerWord;
	}
	public ArrayList<String> getWordsofSentence(){
		return wordsofSentence;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
