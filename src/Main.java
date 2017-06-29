import java.io.File;
import java.io.IOException;

public class Main {
	static Searcher searcher;
	static ReaderandParser reader = new ReaderandParser();
	static TestSetParser testSetParser = new TestSetParser();
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		readComedies(reader);
		readHistorical(testSetParser);
		searcher=new Searcher(reader.getAllofWords());
		findProbability(reader,searcher);
		System.out.println("--------Task 2--------");
		System.out.println("Unigram Model-------");
		UnigramFinder unigramCreater = new UnigramFinder(reader.getAllofWords(),searcher);
		createSentencesViaUnigram(unigramCreater);
		System.out.println("Bigram Model-------");
		UnsmootedBigramFinder bigram = new UnsmootedBigramFinder(reader.getAllofWords(),searcher);
		createSentencesViaBigram(bigram);
		TrigramFinder trigram = new TrigramFinder(reader.getAllofWords(),searcher);
		System.out.println("Trigram Model-------");
		createSentencesViaTrigram(trigram);
		Perplexity perplexities = new Perplexity(reader.getAllofWords(),testSetParser.getWordsofTestSet());
		
		
		
	
	}
	private static void readHistorical(TestSetParser testSetParser) throws IOException {
		File testFile = new File("./historical");
		File[] testFiles = testFile.listFiles();
		
		for(File file :testFiles){
			if(file.isFile()){
				testSetParser.readFile(file);
			}
		}
		
	}
	private static void readComedies(ReaderandParser reader) throws IOException {
		File files = new File("./comedies");
		File[] listOfFiles = files.listFiles();
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	reader.readFile(file);
		    	
		        //System.out.println(file.getName());
		    }
		}
	}
	public static void findProbability(ReaderandParser reader,Searcher searcher){
		SmoothedBigram smoothedBigram=new SmoothedBigram(reader.getAllofWords(),searcher);
		System.out.println("--------Task1--------");
		smoothedBigram.parseSentence("To work or not to work, that is the problem!");
		smoothedBigram.parseSentence("Shall sleep more, Theodore shall sleep more.");
		smoothedBigram.parseSentence("It does not matter how slowly you go so long as you do not stop.");
		smoothedBigram.parseSentence("Imagination is more important than knowledge…");
		smoothedBigram.parseSentence("Thou seest, the heavens, as troubled with man's act");
	}
	
	public static void createSentencesViaUnigram(UnigramFinder unigramCreater){
		for(int i=0;i<10;i++){
			unigramCreater.chooseWordsForSentence();
		}
	}
	public static void createSentencesViaBigram(UnsmootedBigramFinder bigram){
		for(int i=0;i<10;i++){
			bigram.createSentence();
		}
		
	}
	public static void createSentencesViaTrigram(TrigramFinder trigram){
		for(int i=0;i<10;i++){
			trigram.createSentence();
			
		}
		
	}

}
