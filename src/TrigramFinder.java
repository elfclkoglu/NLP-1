import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;
public class TrigramFinder {
	private ArrayList<String> wordsofSentence;
	private ArrayList<String> nextWords;
	private ArrayList<String> nextsofNextWord;
	private HashMap<Double, String> headsWithProbability=new HashMap<Double,String>();
	private Searcher searcher;
	private SortedSet<Double> keys ;
	private SortedSet<Double> keysofHeads ;
	private HashMap<Integer,Word> allofWords=new HashMap<Integer,Word>();
	private HashMap<Double,String> secondWordsWithIndex;
	private HashMap<Double,String> thirdWordsWithIndex;
	private HashSet<String> controllerSet;
	SmoothedTrigram smoothedTrigram;
	
	public TrigramFinder(HashMap<Integer,Word> allofWords,Searcher searcher){
		this.allofWords=allofWords;
		this.searcher=searcher;
		headsWithProbability=searcher.getHeadsWithProbability();
		smoothedTrigram = new SmoothedTrigram(allofWords,searcher);
	}
	public void createSentence(){
		RandomGenerator random = new RandomGenerator();
		wordsofSentence = new ArrayList<String>();
		addFirstWord(random);
		for(int i=0;i<19;i++){
			if(wordsofSentence.size()<3){
				int firstWordIndex=wordsofSentence.size()-1;
		    	findProbabilitiesForSecond(findNexts(firstWordIndex));
		    	chooseSecond(random);
		    	int secondWordIndex=wordsofSentence.size()-1;
		    	findProbabilitiesForThird(findNextsofNext(secondWordIndex));
		    	chooseThird(random);
			}
			else if(wordsofSentence.size()>=3&&wordsofSentence.size()<=19){
				int secondWordIndex=wordsofSentence.size()-1;
				findProbabilitiesForThird(findNextsofNext(secondWordIndex));
				chooseThird(random);
			}
	    	
	    }
		
	    printSentence();
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
	public ArrayList<String> findNextsofNext(int wordIndex){
		
		nextsofNextWord=new ArrayList<String>();
		String thirdWord=null;
		String secondWord= wordsofSentence.get(wordIndex);
		String firstWord=wordsofSentence.get(wordIndex-1);
		for(int i=1;i<allofWords.size();i++){
			if(i+1!=allofWords.size())
				thirdWord=allofWords.get(i+1).getWord();
			if(allofWords.get(i-1).getWord().equals(firstWord)&&allofWords.get(i).getWord().equals(secondWord)){
					nextsofNextWord.add(thirdWord);
			}
		}	
		return nextsofNextWord;
	}
	public void findProbabilitiesForSecond(ArrayList<String> nextWords){
		Double probability=0.0;
		secondWordsWithIndex= new HashMap<Double,String>();
		controllerSet = new HashSet<String>();
		int freq=0;
		
		for(int i=0;i<nextWords.size();i++){
			String word=nextWords.get(i);
			freq=Collections.frequency(nextWords,word);
			
			if(!controllerSet.contains(word)){
				probability=probability+((double)freq/nextWords.size());
				secondWordsWithIndex.put(probability, word);
				controllerSet.add(word);
			}
		}
	}
	public void findProbabilitiesForThird(ArrayList<String> nextsofNextWord){
		Double probability=0.0;
		thirdWordsWithIndex= new HashMap<Double,String>();
		controllerSet = new HashSet<String>();
		int freq=0;
		for(int i=0;i<nextsofNextWord.size();i++){
			String word=nextsofNextWord.get(i);
			freq=Collections.frequency(nextsofNextWord,word);
			if(!controllerSet.contains(word)){
				probability=probability+((double)freq/nextsofNextWord.size());
				thirdWordsWithIndex.put(probability, word);
				controllerSet.add(word);
			}
		}
	}
	public void chooseSecond(RandomGenerator random){
        keys = new TreeSet<Double>(secondWordsWithIndex.keySet());
		for(Double key:keys){
			double marker =random.getRandom();
			String word= secondWordsWithIndex.get(key);
			if(marker<key){
				wordsofSentence.add(word);
                //System.out.println("second "+wordsofSentence.size());
				break;
			}
		
		}
	}
	public void chooseThird(RandomGenerator random){
		keys = new TreeSet<Double>(thirdWordsWithIndex.keySet());
		for(Double key:keys){
			double marker =random.getRandom();
			String word= thirdWordsWithIndex.get(key);
			if(marker<key){
				wordsofSentence.add(word);
				break;
			}
		}
	}
	public void printSentence(){
		String sentence="";
		for(int i=0;i<wordsofSentence.size();i++){
			if(i==0)
				System.out.print(setUpperCase(wordsofSentence.get(i))+" ");
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
		findProbabilityofSentence(sentence);
		System.out.println();
	}
	public void findProbabilityofSentence(String sentence){
		smoothedTrigram.parseSentence(sentence);	
	}
	public String setUpperCase(String word){
		String upperWord=Character.toString(word.charAt(0)).toUpperCase(Locale.ENGLISH)+word.substring(1);
		return upperWord;
	}
}

