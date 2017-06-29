import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

public class ReaderandParser{
	private BufferedReader bufReader;
	private HashMap<Integer,Word> allofWords = new HashMap<Integer,Word>();
	private HashMap<Integer,Word> onlyWords = new HashMap<Integer,Word>();
	private int index=0;
	
	public ReaderandParser(){
		
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
	public void parseLine(String line){
		String words[] = null;
		if((line!=null)&&!(line.contains("<")||line.contains(">"))){
			    line=line.trim();
				words=line.replaceAll("[^a-zA-Z0-9'.?!]", " ").split("\\s+");
				words=cleanApostrophe(words);
				for(int i =0;i<words.length;i++){
					if(words[i]!=null&&!words[i].equals("")&&words[i]!=" "){
						Word onlyWord = new Word(words[i].toLowerCase(Locale.ENGLISH));
						onlyWords.put(i+index, onlyWord);
						if(allofWords.size()==0){
							Word word = new Word("yes",words[i].toLowerCase(Locale.ENGLISH));
							
							allofWords.put(i+index, word);
							
						}
						else{
							Word word = new Word("no",words[i].toLowerCase(Locale.ENGLISH));
							allofWords.put(i+index, word);
						}
					}		
				}
				if(words[0]!=null&&!words[0].equals(""))
					index=allofWords.size();
		}
		
		
		
		
		
	}
	public void setHeadofLine(){
	   for(int i=0;i<allofWords.size();i++){
		   if(allofWords.get(i).getWord().matches("[A-Za-z0-9]+[.?!]")){
	    		if(i+1<allofWords.size())
	    			allofWords.get(i+1).setHeadofLine("yes");
	    		}
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
	public void setAllofWords(HashMap<Integer,Word> allofWords){
		this.allofWords=allofWords;
	}
	public HashMap<Integer,Word> getAllofWords(){
		return allofWords;
	}
	public HashMap<Integer,Word> getOnlyWords(){
		return onlyWords;
	}
	
	
}
