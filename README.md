# Vocabulary

영어 단어장 프로그램

JAVA로 구현한 영어 단어장 프로그램으로, 객관식 퀴즈, 주관식 퀴즈, 오답노트, 단어검색 등의 기능을 구현하였습니다.

quiz.txt라는 각각의 영단어와 그 뜻이 tab으로 구분되어 있는 파일로부터 읽어와 단어장을 생성합니다. 
단어장의 저장구조로는 HashMap 구조를 사용하였고, 각 기능들을 구현하는 부분에서 Set구조도 사용하였습니다.
주관식 퀴즈, 객관식 퀴즈는 각각의 문제가 중복되지 않게 10문제씩 출제하도록 하였고, 10문제를 모두 풀면 걸린 시간과 점수를 출력해 줍니다.
오답노트는 퀴즈에서 틀린 단어들을 출력해 주는데, Stream을 이용하여 오답 횟수가 많은 단어 순으로 출력하도록 하였습니다.

<주요 기능>

- 주관식 퀴즈

<img width="285" alt="image" src="https://user-images.githubusercontent.com/105623744/212483199-72b54e0c-e9a1-408f-9f9e-6e02b8619330.png">

- 객관식 퀴즈

<img width="296" alt="image" src="https://user-images.githubusercontent.com/105623744/212483386-ccdbdf33-5d58-47c7-a132-92d29734b6e7.png">

<img width="254" alt="image" src="https://user-images.githubusercontent.com/105623744/212483418-00b0551a-234c-41d0-90d2-963b9f64784f.png">

- 오답노트

한 번 이상 틀린 단어들에 대해서 오답 횟수가 많은 순으로 출력합니다.

<img width="309" alt="image" src="https://user-images.githubusercontent.com/105623744/212483450-f20dbe9f-f483-42ad-a7ba-2a3707f56a84.png">

- 단어검색

<img width="295" alt="image" src="https://user-images.githubusercontent.com/105623744/212483473-94533425-c6bb-4abe-b7d0-171ca28bea91.png">

<주요 소스코드>

- 단어장 생성

먼저, quiz.txt 파일은 아래와 같은 형식으로 이루어져 있습니다.

<img width="202" alt="image" src="https://user-images.githubusercontent.com/105623744/212482478-0bd0bc90-80c9-469f-94b0-6d54877aa934.png">

makeVoc(String fileName) 함수는 이 파일로부터 단어를 읽어 와 단어장을 생성합니다.
파일에서 한 줄씩 읽어 오면서 split("\t")로 영단어와 뜻을 구분하여 저장하고, addWord()함수를 이용해 단어를 단어장 voc에 추가합니다.
단어장 저장 구조는 HashMap인데, 키 값으로는 Word, 밸류 값으로는 단어의 출제 횟수를 나타내는 Integer를 사용하였습니다.

Word 클래스는 다음과 같습니다.

<img width="339" alt="image" src="https://user-images.githubusercontent.com/105623744/212482899-f6542a04-46f3-4a41-8379-d166ccd67b68.png">

addWord 함수와 makeVoc 함수입니다.

<img width="401" alt="image" src="https://user-images.githubusercontent.com/105623744/212482914-3f2c0d46-90a6-4b87-bfe8-151474876c1c.png">

- 주관식 퀴즈

먼저, 주관식 퀴즈에 출제할 10개의 단어를 중복되지 않도록 뽑아 temp 배열에 저장합니다.
영단어를 순차적으로 탐색하기 위해 HashMap에서 키 값들의 Set을 만들어 이용하였습니다.
10문제 선택은 단어장의 크기보다 작은 랜덤 자연수를 하나 얻어 단어장의 단어들을 순차 탐색하며 단어의 번호(l이라는 변수를 증가시키며 부여함)와 일치할 때 그 단어를 배열에 추가하였습니다.
이 때 중복을 제거하기 위해 새롭게 뽑은 단어가 기존 temp 배열의 단어들과 일치하는지 확인하고, 중복될 경우 카운트를 1 감소시켜 다시 단어를 뽑도록 합니다.

<img width="542" alt="image" src="https://user-images.githubusercontent.com/105623744/212484754-05bc490e-f7f5-4921-b05a-2227b30fc8b1.png">

출제시 시작할 때와 끝날 때 시간을 저장하여 마지막에 걸린 시간을 출력해 줍니다.
단어장에는 영단어는 다르지만 뜻이 같은 단어들이 여러개 있는데, 뜻이 같은 단어들을 모두 출제된 것으로 처리하여 출제 횟수를 증가시키고, 오답 시 오답 횟수도 증가시킵니다.

이를 위해 Iterator<Word> 객체를 이용하여 keySet에서 출제한 단어와 뜻이 같은 단어들을 모두 same[]이라는 배열에 저장하고 채점 시 same배열에 저장된 단어 전체에 대해 출제 횟수와 오답 횟수 등을 처리하였습니다.

<img width="699" alt="image" src="https://user-images.githubusercontent.com/105623744/212486200-52c3edc9-c0e8-4348-bd68-c4444696ef9c.png">

객관식 퀴즈는 주관식 퀴즈와 코드가 비슷하므로 설명을 생략하도록 하겠습니다.

- 오답노트

오답노트는 stream과 람다 표현식을 이용하여 구현하였습니다. 단어장의 단어들을 오답 횟수 내림차순으로 정렬한 뒤, 오답 횟수가 0보다 큰 단어들을 순차적으로 출력하였습니다.
틀린 문제가 없는 경우 따로 "틀린 문제가 없습니다."라는 메세지를 출력하도록 하였습니다.

<img width="670" alt="image" src="https://user-images.githubusercontent.com/105623744/212486404-83402a08-7525-41a1-88e4-8a8854fe5f34.png">

