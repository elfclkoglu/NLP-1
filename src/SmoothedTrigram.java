import java.util.ArrayList;
import java.util.HashMap;

public class SmoothedTrigram {
	private HashMap<Integer, Word> allofWords = new HashMap<Integer, Word>();
	private ArrayList<Double> probabilities;
	private int sizeofSentence=0;
	private int countofWord=0;
	private Searcher searcher;
    private int vocabularySize;
    String words[];
	public SmoothedTrigram(HashMap<Integer,Word> allofWords,Searcher searcher){
		this.allofWords=allofWords;
		this.searcher=searcher;
		vocabularySize = searcher.getVocabulary().size();
	}
	public void parseSentence(String sentence){
		probabilities=new ArrayList<Double>();
		System.out.print("---->Probability of this sentence: ");
		words=sentence.replaceAll("[^a-zA-Z0-9]", " ").split("\\s+");
		
		sizeofSentence=words.length;
		for(int i=0;i<words.length;i++){
			if(i==0){
				findProbabilityofFirstWord(getCountofOneWord(words[0]));
			}
			else if(i==1){
				
				findProbabilityofFirstTwoWords(getCountofTwoWords(words[0],words[1]),words[0]);
				
			}
			else if(i>1){
				
				findProbabilityofTogether(getCountofThreeWords(words[i-2],words[i-1],words[i]),words,i);
			}
			
			
		}
		
	}
	public int getCountofOneWord(String word){
	
		for(int i=0;i<allofWords.size();i++){
			if(allofWords.get(i).getWord().equals(word)){
				countofWord++;
			}
		}
		return countofWord;
	}
	public int getCountofTwoWords(String firstWord,String secondWord){
		for(int i=0;i<allofWords.size();i++){
			if(i+1<allofWords.size())
				if(allofWords.get(i).getWord().equals(firstWord)&&allofWords.get(i+1).getWord().equals(secondWord)){
					countofWord++;
			}
		}
		
		return countofWord;
	}
	public int getCountofThreeWords(String firstWord,String secondWord,String thirdWord){
		for(int i=0;i<allofWords.size();i++){
			if(i+2<allofWords.size())
				if(allofWords.get(i).getWord().equals(firstWord)&&allofWords.get(i+1).getWord().equals(secondWord)&&allofWords.get(i+2).getWord().equals(thirdWord)){
					countofWord++;
			}
		}
		
		return countofWord;
	}
	
	public void findProbabilityofFirstWord(int count){
		int denominator=allofWords.size();
		if(count==0){
			doSmoothing(denominator);
		}
		else{
			double probability = (double)count/denominator;
			probabilities.add(probability);
		}
        control();
		
		countofWord=0;
	}
	public void findProbabilityofFirstTwoWords(int count,String previousWord){
		int denominator=getCountofOneWord(previousWord);
		if(count==0){
			doSmoothing(denominator);
		}
		else{
			double probability = (double)count/denominator;
			probabilities.add(probability);
			
		}
		control();
		countofWord=0;
	
	}
	public void control(){
		if(probabilities.size()==words.length){
			
			calculateProbability();
		}
	}

	public void findProbabilityofTogether(int count,String []words,int index){
		int denominator=getCountofTwoWords(words[index-2],words[index-1]);
		if(count==0){
			doSmoothing(denominator);
		}
		else{
			double probability=(double)count/denominator;
			probabilities.add(probability);
		}
		control();
		countofWord=0;
		
	}
	public void doSmoothing(int denominator){
		int numerator =1;
		double probability;
		probability=(double)numerator/(denominator+vocabularySize);
		probabilities.add(probability);
	}
	
	public void calculateProbability(){
		double probability=1;
		for(int i=0;i<probabilities.size();i++){
			probability=probability*probabilities.get(i);
		}
		System.out.println(probability);
		System.out.println();
	}
	

}
