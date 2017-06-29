public class Word {
	private String headofLine;
	private String word;
	public Word(String headofLine,String word){
		this.setHeadofLine(headofLine);
		this.setWord(word);
	}
	public Word(String word){
		this.word=word;
	}
	public String getHeadofLine() {
		return headofLine;
	}
	public void setHeadofLine(String headofLine) {
		this.headofLine = headofLine;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
}

