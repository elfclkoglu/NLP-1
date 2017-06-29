import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
public class Searcher {
	private HashMap<Integer,Word> allofWords = new HashMap<Integer,Word>();
	private HashSet<String> vocabulary = new HashSet<String>();
	private ArrayList<String> headsArray = new ArrayList<String>();
	private HashSet<String> headsofLines = new HashSet<String>();
	private HashMap<Double,String> headsWithProbability = new HashMap<Double,String>();
	private HashMap<String,Double> vocabularyWithCounts=new HashMap<String,Double>();
	private SortedSet<Double> keys;
	private double index=0;
	private double temp=0;
	private double indexofHead=0;
	
	public Searcher(HashMap<Integer,Word> allofWords){
		this.allofWords=allofWords;
		
		searchForUnique();
		addHeadofLine();
		
		setProbabilityofHead();
		setCountofVocabulary();
	}

	public void searchForUnique(){
		for(int i=0;i<allofWords.size();i++){
			String word=allofWords.get(i).getWord();
			if(!vocabulary.contains(word)){
				vocabulary.add(word);
			}
		}
	}
	public void addHeadofLine(){
		for(int i=0;i<allofWords.size();i++){
			Word word=allofWords.get(i);
				if(word.getHeadofLine().equals("yes")){
					headsArray.add(word.getWord());
					if(!headsofLines.contains(word.getWord())){
						headsofLines.add(word.getWord());
					}
					
				}
			
			
		}
	}
	
	public void setCountofVocabulary(){
		for(int i=0;i<allofWords.size();i++){
			String word = allofWords.get(i).getWord();
			if(vocabularyWithCounts.containsKey(word)){
				vocabularyWithCounts.put(word,vocabularyWithCounts.get(word)+1);
			}
			else{
				vocabularyWithCounts.put(word,1.0);			
			}
		}
	}
	public int getFrequencyofHeads(String word){
		int freq= Collections.frequency(headsArray,word);
		return freq;
	}

	public void setProbabilityofHead(){
		for(int i=0;i<headsArray.size();i++){
			String word = headsArray.get(i);
			if(headsofLines.contains(word)){
				int freq=getFrequencyofHeads(word);
				
				indexofHead=indexofHead+(double)freq/headsArray.size();
				
				headsWithProbability.put(indexofHead, word);
				headsofLines.remove(word);
				
			}
		}
	}

	public boolean isWordinVocabulary(String word){
		if(vocabulary.contains(word)){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public HashSet<String> getVocabulary(){
		return vocabulary;
	}
	public HashMap<Double,String> getHeadsWithProbability(){
		return headsWithProbability;
	}
	
	public HashMap<String,Double> getVocabularyWithCounts(){
		return vocabularyWithCounts;
	}
}
