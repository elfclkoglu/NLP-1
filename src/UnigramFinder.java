import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;
public class UnigramFinder {
	private ArrayList<String> wordsofSentence;
	HashMap<Integer,Word> allofWords= new HashMap<Integer,Word>();
	HashSet<String> vocabulary= new HashSet<String>();
	HashMap<String,Double> vocabularyWithCounts = new HashMap<String,Double>();
	HashMap<Double,Word> vocabularyWithIndex = new HashMap<Double,Word>();
    SortedSet<Double> keys;
	Searcher searcher;
	RandomGenerator random= new RandomGenerator();
	double index=0.0;
	UnigramCalculater unigramFinder;
	public UnigramFinder(HashMap<Integer,Word> allofWords,Searcher searcher){
		this.searcher=searcher;
		this.allofWords=allofWords;
		this.vocabulary=searcher.getVocabulary();
		this.vocabularyWithCounts=searcher.getVocabularyWithCounts();
		findProbabilitiesofWords();
	}
	public void findProbabilitiesofWords(){
		Iterator<String> iterator = vocabulary.iterator();
		double probability=0;
		while(iterator.hasNext()){
			String word= iterator.next();
			Word object = new Word(word);
			
			if(vocabularyWithCounts.containsKey(word)){
				probability=vocabularyWithCounts.get(word)/allofWords.size();
				
				index=index+probability;
					
				
				
				vocabularyWithIndex.put(index, object);
			}
		}
		
		
	}
	public void chooseWordsForSentence(){
		wordsofSentence = new ArrayList<String>();
		keys = new TreeSet<Double>(vocabularyWithIndex.keySet());
		for(int i=0;i<20;i++){		
			double marker=random.getRandom();
		
			for (Double key : keys) { 
				
				   String word = vocabularyWithIndex.get(key).getWord();
				   if(marker<key){
					   if(i==0){
						   wordsofSentence.add(setUpperCase(word));
						   
					   }
					   else{
						   wordsofSentence.add(word);
					   }
					  
					   break;
				   }
				   
			}
	    }
		printSentence(wordsofSentence);
	}
	public void printSentence(ArrayList<String> wordsofSentence){
		String sentence="";
		for(int i=0;i<wordsofSentence.size();i++){	
			if(wordsofSentence.get(i).matches("[A-Za-z0-9]+[.?!]")){
				sentence= sentence+wordsofSentence.get(i)+" ";
				break;
			}
			else{
				sentence= sentence+wordsofSentence.get(i)+" ";
			}
		}
		System.out.println(sentence);
		unigramFinder= new UnigramCalculater(wordsofSentence,vocabularyWithCounts,allofWords);
	}
	
	public String setUpperCase(String word){
		String upperWord=Character.toString(word.charAt(0)).toUpperCase(Locale.ENGLISH)+word.substring(1);
		return upperWord;
	}
}

