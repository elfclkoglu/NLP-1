import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;


public class TestSetParser {
	
	private BufferedReader bufReader;
	private HashMap<Integer,Word> wordsofTestSet = new HashMap<Integer,Word>();
	private int index=0;
	
	
	public  TestSetParser(){
		
	}
    public void readFile(File file) throws IOException{
		
		FileReader fileReader = new FileReader(file);
		bufReader = new BufferedReader(fileReader);
		String line = bufReader.readLine();
		while (line != null) {
			line = bufReader.readLine();
			parseLine(line);
			
			
		}	
		setHeadofLine();
		
    }
    public void setHeadofLine(){
    	for(int i=0;i<wordsofTestSet.size();i++){
    		if(wordsofTestSet.get(i).getWord().matches("[A-Za-z0-9]+[.?!]")){
    			if(i+1<wordsofTestSet.size())
    			wordsofTestSet.get(i+1).setHeadofLine("yes");
    		}
    	}
    }
	public void parseLine(String line){
		String words[] = null;
		if((line!=null)&&!(line.contains("<")||line.contains(">"))){
			    line=line.trim();
				words=line.replaceAll("[^a-zA-Z0-9'.?!]", " ").split("\\s+");
				words=cleanApostrophe(words);
				for(int i =0;i<words.length;i++){
					if(words[i]!=null&&!words[i].equals("")&&words[i]!=" "){
						if(wordsofTestSet.size()==0){
							Word word = new Word(words[i].toLowerCase(Locale.ENGLISH));
							
							wordsofTestSet.put(i+index, word);
							
						}
						else{
							Word word = new Word(words[i].toLowerCase(Locale.ENGLISH));
							wordsofTestSet.put(i+index, word);
						}
					}		
				}
				if(words[0]!=null&&!words[0].equals(""))
					index=wordsofTestSet.size();
		}
		
		
		
		
	}
	public String [] cleanApostrophe(String words[]){
		char apostrophe='\'';
		
		for(int i=0;i<words.length;i++){
			if(words[i].contains("'")){
				if(!(words[i].length()==1))
				for(int j=0;j<words[i].length();j++){
					if((words[i].charAt(0)==apostrophe)){
						words[i]=words[i].replace("'", "");
					}
					else if(j!=0&&words[i].charAt(j)==apostrophe){
						words[i]=words[i].substring(0, j);
					}
					
				}
				else{
						words[i]=words[i].replace("'", " ");
					
				}
			}
			
		}
		return words;
		
		
	}
	public void setWordsofTestSet(HashMap<Integer,Word> wordsofTestSet){
		this.wordsofTestSet=wordsofTestSet;
	}
	public HashMap<Integer,Word> getWordsofTestSet(){
		return wordsofTestSet;
	}
	
	
	
}
