package Vocabulary.jylim;

public class Word {
	String eng;
	String kor;
	int count=0;
	int wrong=0;
	
	public Word(String eng, String kor) {
		super();
		this.eng = eng;
		this.kor = kor;
	}

	@Override
	public String toString() {
		String str=eng+" 뜻 : "+kor;
		str += "\n출제횟수 : "+count+"\t오답횟수 : "+wrong;
		str += "\n------------------------------";
		return str;
	}
	
}
