package Vocabulary.jylim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public class VocManager {	
	private String userName;
	private Map<Word,Integer> voc = new HashMap<>(); //Integer는 출제 횟수
	
	static Scanner scan = new Scanner(System.in);
	Random r = new Random();
	
	VocManager(String userName){
		this.userName = userName;
	}
	
	void addWord(Word word) {
		voc.put(word,0);
	}
	
	void makeVoc(String fileName) {
		
		try(Scanner scan = new Scanner(new File(fileName))){
			while(scan.hasNextLine()) {
				String str = scan.nextLine();
				String[] temp = str.split("\t");
				this.addWord(new Word(temp[0].trim(),temp[1].trim()));
			}
			System.out.println(userName+"의 단어장이 생성되었습니다.");
			this.menu();
			
		}catch(FileNotFoundException e) {
			System.out.println(userName+"의 단어장이 생성되지 않았습니다.");
		}
	}
	
	void menu() {
		int choice = 0;
		
		while(choice != 5) {
			try {
				System.out.println("202212128 임지예");
				System.out.println("\n------ "+userName+"의 단어장 ------");
				System.out.println("1) 주관식 퀴즈 2) 객관식 퀴즈 3) 오답노트 4) 단어검색 5) 종료");
				System.out.print("메뉴를 선택하세요 : ");
				choice = scan.nextInt();
				scan.nextLine();
				System.out.println();
			
				switch(choice) {
				case 1:
					shortAnswerQuiz();
					break;
				case 2:
					multipleChoice();
					break;
				case 3:
					wrongAnsNotes();
					break;
				case 4:
					searchVoc();
					break;
				case 5:
					System.out.println(userName+"의 단어장 프로그램을 종료합니다.");
					break;
				default:
					System.out.println("다시 입력하세요.");	
				}
				
			}catch(InputMismatchException e) {
				System.out.println("\n정수를 입력하세요.");
				scan.nextLine();
			}
			
		}
	}

	public void shortAnswerQuiz() {
		final int NUM=10;
		int correct=0;
		Word[] tmp = new Word[NUM];
		Set<Word> keySet = voc.keySet();
		//Iterator<Word> keyIterator = keySet.iterator();
		
		//10문제 뽑기
		int size = keySet.size();
		for(int i=0;i<NUM;i++) {
			int k=i;
			int l=0;
			int rand = r.nextInt(size);
			Word key=null;
			for(Word word : keySet) {
				if(l==rand) {
					key=word;
					break;
				}
				l++;
			}
			for(int j=0;j<i;j++) {
				if(key.kor.equals(tmp[j].kor)) {
					i--;
					break;
				}
			}
			if(k==i)
				tmp[i]=key;
		}
		
		long starttime = System.currentTimeMillis();
		
		//출제
		for(int i=0;i<NUM;i++) {
			System.out.println("------ 주관식 퀴즈 "+(i+1)+"번 ------");
			System.out.println("\""+tmp[i].kor+"\"의 뜻을 가진 영어 단어는 무엇일까요?");
			System.out.print("답을 입력하세요 : ");
			String answer = scan.nextLine();
			boolean T=false; //정답 여부
			
			//뜻이 중복된 단어 찾기
			Word[] same = new Word[10];
			int j=0;
			Iterator<Word> keyIterator2 = keySet.iterator();
			while(keyIterator2.hasNext()) {
				Word key = keyIterator2.next();
				if(key.kor.equals(tmp[i].kor)) {
					same[j++]=key;
					if(key.eng.equals(answer.trim())) {
						T=true;
					}
				}
			}
			//정답 확인
			if(T==true) {
				System.out.println("정답입니다.");
				correct++;
				for(int k=0;k<j;k++) {
					voc.put(same[k],++same[k].count);
				}
			}else {
				System.out.println("틀렸습니다. 정답은 "+tmp[i].eng+"입니다.");
				for(int k=0;k<j;k++) {
					same[k].wrong++;
					voc.put(same[k],++same[k].count);
				}
			}
		}
		
		long endtime = System.currentTimeMillis();
		System.out.println();
		System.out.println(this.userName+"님 "+NUM+"문제 중 "+correct+"개 맞추셨고, 총 "+(endtime-starttime)/1000+"초 소요되었습니다.");
		System.out.println();
		
	}
	
	public void multipleChoice() {
		final int NUM=10;
		final int QUIZ=4;
		int correct=0;
		Word[] tmp = new Word[QUIZ];
		Set<Word> keySet = voc.keySet();
		//Iterator<Word> keyIterator = keySet.iterator();
		
		long starttime = System.currentTimeMillis();
		
		for(int l=0;l<NUM;l++) {
			
			//단어 4개 뽑기
			int size = keySet.size();
			for(int i=0;i<QUIZ;i++) {
				int k=i;
				int n=0;
				int rand=r.nextInt(size);
				Word key = null;
				for(Word word : keySet) {
					if(n==rand) {
						key=word;
						break;
					}
					n++;
				}
				for(int j=0;j<i;j++) {
					if(key.kor.equals(tmp[j].kor)) {
						i--;
						break;
					}
				}
				if(k==i)
					tmp[i]=key;
			}
			
			//출제 횟수 증가시키기
			for(int i=0;i<QUIZ;i++) {
				Iterator<Word> keyIterator = keySet.iterator();
				while(keyIterator.hasNext()) {
					Word key = keyIterator.next();
					if(key.kor.equals(tmp[i].kor)) {
						voc.put(key,++key.count);
						
					}
				}
			}
			
			int answer=r.nextInt(4)+1;
			int user;
			
			//출제
			System.out.println("------ 객관식 퀴즈 "+(l+1)+"번 ------");
			System.out.println("\""+tmp[answer-1].eng+"\"의 뜻은 무엇일까요?");
			for(int i=0;i<QUIZ;i++) {
				System.out.println((i+1)+") "+tmp[i].kor);
			}
			System.out.print("답을 입력하세요 : ");
			try {
				user = scan.nextInt();
			}catch(InputMismatchException e) {
				user=0;
				scan.nextLine();
			}
			
			if(answer==user) {
				System.out.println("정답입니다.");
				correct++;
				
			}else {
				System.out.println("틀렸습니다. 정답은 "+answer+"번입니다.");
				tmp[answer-1].wrong++;
			}
		}
		
		long endtime = System.currentTimeMillis();
		System.out.println();
		System.out.println(this.userName+"님 "+NUM+"문제 중 "+correct+"개 맞추셨고, 총 "+(endtime-starttime)/1000+"초 소요되었습니다.");
		System.out.println();
		
	}
	
	public void wrongAnsNotes() {
		Set<Word> keySet = voc.keySet();
		keySet.stream().sorted((o1,o2)->(o1.wrong-o2.wrong)*-1).filter(w->w.wrong>0).forEach(System.out::println);
		
		if(keySet.stream().filter(w->w.wrong>0).count()==0)
			System.out.println("틀린 문제가 없습니다.");
	}

	public void searchVoc() {
		Set<Word> keySet = voc.keySet();
		Iterator<Word> keyIterator = keySet.iterator();
		
		System.out.println("------ 단어 검색 ------");
		System.out.print("검색할 단어를 입력하세요 (영단어) : ");
		String sWord = scan.nextLine();
		sWord = sWord.trim();
		
		Word tmp = null;
		while(keyIterator.hasNext()) {
			Word key = keyIterator.next();
			if(key.eng.equals(sWord)) {
				System.out.println(key);
				tmp=key;
				break;
			}
		}
		if(tmp==null) {
			System.out.println("\n단어장에 등록된 단어가 아닙니다.");
		}
		
	}
	
	
}
