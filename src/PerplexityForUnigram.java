import java.util.HashMap;
import java.util.HashSet;

import javax.swing.text.html.HTMLDocument.Iterator;

public class PerplexityForUnigram {
	HashMap<Integer,Word> allofWords= new HashMap<Integer,Word>();
	HashMap<String,Integer> wordsWithCount = new HashMap<String,Integer>();
	HashMap<Integer,Word> testSet= new HashMap<Integer,Word>();
	HashSet<Double> probabilities = new HashSet<Double>();
	private HashSet<String> vocabulary = new HashSet<String>();
	public PerplexityForUnigram(HashMap<Integer,Word> allofWords,HashMap<Integer,Word> testSet){
		this.allofWords=allofWords;
		this.testSet=testSet;
		countofWords();
		searchForUnique();
		getCountofWord();
	}
	public void getCountofWord(){
		int count=0;
		for(int i=0;i<testSet.size();i++){
			String word= testSet.get(i).getWord();
			if(wordsWithCount.containsKey(word)){
				count= wordsWithCount.get(word);
				addProbability(count);
			}
			else{
				
				doSmoothing();
			}
			
			
		}
		calculateProbability();
	}
	public void searchForUnique(){
		for(int i=0;i<allofWords.size();i++){
			String word=allofWords.get(i).getWord();
			if(!vocabulary.contains(word)){
				vocabulary.add(word);
			}
		}
	}
	public void doSmoothing(){
		int count =1;
		double probability=(double)count/(allofWords.size()+vocabulary.size());
		probabilities.add(Math.log(probability)/Math.log(2));
	}
	public void addProbability(int count){
		double probability=(double)count/allofWords.size();
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
		double pow= probability/probabilities.size();
		System.out.println("Perplexity of Unigram: "+Math.pow(2, -pow));
	}
	public void countofWords(){
		for(int i=0;i<allofWords.size();i++){
			String word= allofWords.get(i).getWord();
			if(!wordsWithCount.containsKey(word)){
				wordsWithCount.put(word,1);
				
			}
			else{
				wordsWithCount.put(word,wordsWithCount.get(word)+1);
			}
		}
	}
	public HashMap<String,Integer> getWordsWithCount(){
		return wordsWithCount;
	}
	public HashSet<String> getVocabulary(){
		return vocabulary;
	}
}
