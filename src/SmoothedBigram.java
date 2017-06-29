import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
public class SmoothedBigram {
	private HashMap<Integer, Word> allofWords = new HashMap<Integer, Word>();
	private ArrayList<Double> probabilities;
	private int sizeofSentence=0;
	private int countofWord=0;
	private String previousWord=" ";
	Searcher searcher;
	private int vocabularySize;
	public SmoothedBigram(HashMap<Integer, Word> allofWords,Searcher searcher){
		this.allofWords=allofWords;
		this.searcher=searcher;
		vocabularySize=searcher.getVocabulary().size();
	}
	public void parse(String sentence){
		probabilities=new ArrayList<Double>();
	    String words[]=sentence.replaceAll("[^a-zA-Z0-9]", " ").split("\\s+");
	    sizeofSentence=words.length;
	    for(int i=0;i<words.length;i++){
			if(i==0){
				
				findProbabilityofFirstWord(getCountofOneWord(words[i]));	
			}
			else{
				
				getCountofTogether(words[i-1],words[i]);
			}
			
			
		}
	}
	public void parseSentence(String sentence){
		
		probabilities=new ArrayList<Double>();
		System.out.print("Probability of "+ sentence+": ");
		String words[]=sentence.replaceAll("[^a-zA-Z0-9']", " ").split("\\s+");
		
		sizeofSentence=words.length;
		for(int i=0;i<words.length;i++){
			if(words[i].contains("'")){
				words[i]=controlForApostrophe(words,i);
			}
			if(i==0){
				
				findProbabilityofFirstWord(getCountofOneWord(words[i]));	
			}
			else{
				
				getCountofTogether(words[i-1],words[i]);
			}
			
			
		}
	}
	public String controlForApostrophe(String []words,int i){
		String word = null;
        
        int index=words[i].indexOf('\'');
		word=words[i].substring(0, index);
		
        return word;
	}
	public void getCountofTogether(String firstWord,String secondWord ){
		
		for(int i=0;i<allofWords.size();i++) {
			 Word object=allofWords.get(i);
			 if(i+1<allofWords.size())
				 if(allofWords.get(i).getWord().equals(firstWord)&&allofWords.get(i+1).getWord().equals(secondWord)){
					 countofWord++;
				 } 
	    }
		if(countofWord==0)
			doSmoothing(firstWord);
		else{
			findProbability(countofWord,firstWord);
		}
		countofWord=0;
		
	}
	
	public int getCountofOneWord(String word){
		int temp=0;
		for(int i=0;i<allofWords.size();i++){
			Word object = (Word) allofWords.get(i);
			String wordinText = object.getWord();
			if(wordinText.equals(word.toLowerCase(Locale.ENGLISH))){
				
				temp++;
			}
			
		}
		
		
			
		return temp;	
	}
	public void doSmoothing(String previousWord){
		int counter=getCountofOneWord(previousWord);
		Double probability=(double)(countofWord+1)/(counter+vocabularySize);
		probabilities.add(probability);
		if(probabilities.size()==sizeofSentence){
			calculateProbability();
		}
		countofWord=0;
		
	}
    public void findProbability(int countofWord,String previousWord){
		int counter=getCountofOneWord(previousWord);
		Double probability=(double)countofWord/counter;
		probabilities.add(probability);
		if(probabilities.size()==sizeofSentence){
			calculateProbability();
		}
		countofWord=0;
	}
	public void findProbabilityofFirstWord(int count){
		double probability = (double)count/vocabularySize;
		probabilities.add(probability);
		countofWord=0;
		
	}
	public void calculateProbability(){
		double probability=1;
		for(int i=0;i<probabilities.size();i++){
			probability*=probabilities.get(i);
		}
		System.out.println(probability);
		System.out.println();
	}
	public String setUpperCase(String word){
		String upperWord=Character.toString(word.charAt(0)).toUpperCase()+word.substring(1);
		return upperWord;
	}
	public HashMap<Integer, Word> getAllofWords() {
		return allofWords;
	}
	public void setAllofWords(HashMap<Integer, Word> allofWords) {
		this.allofWords = allofWords;
	}
	public String getPreviousWord() {
		return previousWord;
	}
	public void setPreviousWord(String previousWord) {
		this.previousWord = previousWord;
	}
}
