import java.util.HashMap;

public class Perplexity {
	HashMap<Integer,Word> allofWords= new HashMap<Integer,Word>();
	HashMap<Integer,Word> testSet= new HashMap<Integer,Word>();
	PerplexityForUnigram perpUnigram;
	PerplexityForBigram  perpBigram;
	PerplexityForTrigram perpTrigram;
	public Perplexity(HashMap<Integer,Word> allofWords,HashMap<Integer,Word> testSet){
		this.allofWords=allofWords;
		this.testSet=testSet;
		getPerplexities();
	}
	public void getPerplexities(){
		System.out.println("----------Task 3----------");
		perpUnigram= new PerplexityForUnigram(allofWords,testSet);
		perpBigram = new PerplexityForBigram(allofWords,testSet,perpUnigram.getWordsWithCount(),perpUnigram.getVocabulary());
		perpTrigram = new PerplexityForTrigram(allofWords,testSet,perpUnigram.getWordsWithCount(),perpUnigram.getVocabulary(),perpBigram.getCountofTwoWords());
	}
	
}
