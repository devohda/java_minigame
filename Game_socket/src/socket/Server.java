package socket;


import combination.Card;
import frame_panel.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JPanel;
 
public class Server {
 	
    private ServerSocket serverSocket;
    private Socket socket;
    private String msg;
    
    // 병뚜껑
    private String WrongAnswers= "";
	private int filled = 0;
	private int iWrongAns[] = new int[50];
	private int iRanNum = 7;
    
    // 캐치캐치
  	private int point[];
  	
  	// 결합
  	private Card[] boardInfoCombination; // 게임구현을 위한 카드정보 객체
  	private int[][] indexCombi = new int[9][3];
 
    // 클라이언트들의 정보저장
    private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
 
    public void setting() throws IOException {
        Collections.synchronizedMap(clientsMap); // 클라이언트 순서 및 정보 정리
        serverSocket = new ServerSocket(7777); // 실행하는 컴퓨터의 ip에서 포트번호 7777번으로 서버 소켓 생성
        while (true) {
            // 클라이언트 접속 받기
            System.out.println("서버 대기중...");
            socket = serverSocket.accept(); // 서버가 계속 반복해서 클라이언트를 받음
            System.out.println(socket.getInetAddress() + "에서 접속했습니다."); // 접속한 클라이언트의 ip를 출력
            
            // 새로운 사용자 쓰레드 클래스 생성 후 소켓정보를 넣음
            Receiver receiver = new Receiver(socket);
            receiver.start();
        }
    }
 
    public static void main(String[] args) throws IOException {
        Server serverBackground = new Server(); // 객체 생성
        serverBackground.setting(); // setting 함수 호출
    }
 
    // 클라이언트 저장
    public void addClient(String nick, DataOutputStream out) throws IOException {
        sendMessage("<<<<<<< " + nick + "님이 접속하셨습니다. >>>>>>>\n");
        clientsMap.put(nick, out);
    }
    // 클라이언트 삭제
    public void removeClient(String nick) {
        sendMessage("<<<<<<< " + nick + "님이 나가셨습니다. >>>>>>>\n");
        clientsMap.remove(nick);
    }
 
    // 메시지 내용 전파
    public void sendMessage(String msg) {
        Iterator<String> it = clientsMap.keySet().iterator();
        String key = "";
        while (it.hasNext()) {
            key = it.next();
            try {
                clientsMap.get(key).writeUTF(msg); // 메세지 모든 클라이언트에 전달
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    // ----------------------------------게임 별로 함수 구분----------------------------------------- //
    // ---------------------------------------------------------------------------------------- //
    
    
    // ----------------------------------병뚜껑--------------------------------------------------
    public void init() {
    	
    	Random generator = new Random();
		iRanNum = generator.nextInt(20)+1; //1~20 랜덤숫자 생성
		WrongAnswers = ""; // 정답 또는 오답들의 정보를 갖고 있는 문자열
		int[] ar = new int[50];
		filled=0;
		iWrongAns= ar; // 입력받은 오답들의 정보를 기록하는 배열
		System.out.println(iRanNum);
		sendMessage("[init]"); // 클라이언트에 초기화 메세지 전달
		sendMessage("[RandNum]" + iRanNum); // 클라이언트에 새로운 랜덤 변수(병뚜껑 숫자) 메세지 전달
    }
    
    public void Answer(String ans) {
    	
    	int num = Integer.parseInt(ans.substring(8)); // ans문자열 인덱스 8부터의 문자열 숫자로 변경 
    	WrongAnswers = result(iRanNum, num, WrongAnswers); // 정답 및 오답의 결과를 결정하는 result함수 호출
    	
    	if (WrongAnswers == "correct!") sendMessage("[TRUE]" + WrongAnswers); // 정답 메세지 전달
    	else sendMessage("[FALSE]" + WrongAnswers); // 오답 메세지 전달
    }
    
    private String result(int iRandNum, int iAnswer, String wrongStr) {

		int tmp,i,j;
		String str ="";
		
		if( iRandNum != iAnswer) {	// 오답일 경우
			iWrongAns[filled] = iAnswer; // 새로 입력받은 오답 기록
			filled++; 
			
			for(i=filled-1; i>0; i--) { // 틀린 오답들 오름차순으로 나열하는 Bubble Sort
				for(j=0; j<i; j++) {
					if(iWrongAns[j] > iWrongAns[j+1] ) {
						tmp = iWrongAns[j];
						iWrongAns[j] = iWrongAns[j+1];
						iWrongAns[j+1] = tmp;
					}
				}
			}
			
			for(i=0; i<filled; i++) { // Label에 표현하기위해 오답 int배열을 문자열로 변경
				str += String.valueOf(iWrongAns[i]) + " ";
			}
		}
		else { // 정답 일 경우
			str = "correct!"; // 반환 값에 정답문구 저장 
		}
		return str;

	}
    
    public int getRandNum() { return iRanNum; }
    
    // ----------------------------------전국노래자랑--------------------------------------------------
    public void singerIndex() {
    	int singerIndex = (int)(Math.random()*25); // 0~24까지의 인덱스를 랜덤으로 생성
    	sendMessage("[SINGERINDEX]" + singerIndex); // 랜덤으로 생성된 인덱스 메세지 전달
    }
    
    // -----------------------------------훈민정음--------------------------------------------------
    public void hunminIndex() {
    	int[] hunminIndex = new int[2];
    	hunminIndex[0] = (int)(Math.random()*14); // 0~13까지의 인덱스를 랜덤으로 생성
    	hunminIndex[1] = (int)(Math.random()*14); // 0~13까지의 인덱스를 랜덤으로 생성
    	sendMessage("[HUNMININDEX]" + hunminIndex[0] + "/" + hunminIndex[1]); // 랜덤으로 생성된 두 개의 인덱스 메세지 전달
    }
    
    // -----------------------------------캐치캐치---------------------------------------------------
    public void initCatch() {
    	Random generator = new Random();
    	
    	// 캐치캐치 5x5 게임판 구현을 위한 정보 생성
    	point = new int[25]; // 0~4 : 2점 , 5~9 : 1점 , 10~14 : 0점 , 15~19 : -1점, 20~24 : -2점
		
		for(int i=0; i<25; i++) {
			point[i] = generator.nextInt(25); // 랜덤으로 0~24의 숫자 생성 후 대입
			for(int j=0; j<i; j++) {
				if(point[i] == point[j]) {
					i--;
				}
			}
		}
		
		String concat = "";
		for(int i=0; i<25; i++) {
			concat += point[i];
			concat += "/";
		}
		sendMessage("[POINTINDEX]" + concat); // 랜덤으로 생성된 숫자 정보 클라이언트들에 전달
    }
    
    public void rewardCatch(String r) {
    	int indexCatch = Integer.parseInt(r.substring(13)); // 게임에서 클릭된 박스 객체의 인덱스를 의미함
    	sendMessage("[BOXCATCH]" + indexCatch); // 모든 클라이언트에 클릭 된 박스 인덱스 정보 메세지 전달
    }
    
    public int[] getRandPoint() { return point; }
    
    // -------------------------------------결합-------------------------------------
    public void initCombination() {
    	boardInfoCombination = new Card[9]; // 결합 게임 구현을 위한 카드 객체 9개 생성
    	
    	for (int i = 0; i < 9; i++) {
            Card tmpCombi = new Card();

            boolean duplicated = false;
            //중복 없는 카드판 만들기
            for(int j = 0 ; j < i; j++){
                if(tmpCombi.getCardColor() == boardInfoCombination[j].getCardColor()){
                    if (tmpCombi.getShape() == boardInfoCombination[j].getShape()) {
                        if (tmpCombi.getBackgroundColor() == boardInfoCombination[j].getBackgroundColor()){
                            duplicated = true;
                            break;
                        }
                    }
                }
            }
            // 중복하지 않은 경우 객체에 대입
            if(!duplicated) {
            	boardInfoCombination[i] = tmpCombi;
            	indexCombi[i] = tmpCombi.getIndexCombi();
            }
            else i--; // 중복한는 경우 다시 카드 정보를 얻는 과정 수행
    	}
    	
    	String concat = "";
		for(int i=0; i<9; i++) {
			for(int j=0; j<3; j++) {
				concat += indexCombi[i][j];
			}
		}
    	sendMessage("[COMBINATIONINIT]" + concat); // 모든 클라이언트에 초기화 메세지 전달(카드 객체 정보)
    }
    
    // 게임에서 플레이어가 결을 클릭한 경우
    public void geul(String m) {
    	String str = m.substring(17); // 플레이어의 순서 정보
    	sendMessage("[GEUL]" + str); // 모든 클라이언트에 결이 입력됐다는 메세지와 입력한 플레이어의 순서 전달
    }
    
    // 게임에서 플레이어가 합을 클릭한 경우
    public void hap(String m) {
    	String str1 = m.substring(16, 17); // 플레이어의 순서 정보
    	String str2 = m.substring(17); // 입력한 정답 정보
    	sendMessage("[HAP]" + str1 + str2); // 모든 클라이언트에 합이 입력됐다는 메세지와 플레이어의 순서 및 입력한 정답 전달
    }
    
    
    // -----------------------------------------------------------------------------
    class Receiver extends Thread {
        private DataInputStream in;
        private DataOutputStream out;
        private String nick;
 
        // 네트워크 처리 클라이언트로부 읽고 서버 단위로 처리해 주게끔
        public Receiver(Socket socket) throws IOException {
            out = new DataOutputStream(socket.getOutputStream()); // OutputStream 처리를 위한 변수
            in = new DataInputStream(socket.getInputStream()); // InputStream 처리를 위한 변수
            nick = in.readUTF(); // 플레이어들이 설정한 닉네임 읽어들이기
            addClient(nick, out); // 닉네임을 이용해 클라이언트 정보 map에 추가
        }
 
        public void run() {
            try {// 계속 듣기만
                while (in != null) {
                    msg = in.readUTF();
                    
                    // 게임별로 조건문으로 제어
                    if (msg.startsWith("[initBottle]")) init(); // 보틀캡 게임 초기화를 위한 함수 호출
                    else if (msg.startsWith("[Answer]")) Answer(msg); // 보클캡 게임 정답 판별위한 함수 호출
                    else if (msg.startsWith("[SINGERINIT]")) singerIndex(); // 전국노래자랑 초기화를 위한 함수 호출
                    else if (msg.startsWith("[HUNMININIT]")) hunminIndex(); // 훈민정음 초기화를 위한 함수 호출
                    else if (msg.startsWith("[CATCHINIT]")) initCatch(); // 캐치캐치 게임 초기화를 위한 함수 호출
                    else if (msg.startsWith("[REWARDCATCH]")) rewardCatch(msg); // 캐치캐치 클릭한 상자에 따른 보상값 처리를 위한 함수 호출
                    else if (msg.startsWith("[COMBINATIONINIT]")) initCombination(); // 결합 게임 초기화를 위한 함수 호출
                    else if (msg.startsWith("[GEULCOMBINATION]")) geul(msg); // 결합게임에서 결을 누른경우 처리하는 함수 호출
                    else if (msg.startsWith("[HAPCOMBINATION]")) hap(msg); // 결합게임에서 합을 누른경우 처리하는 함수 호출
                    else {
                    	sendMessage(msg); //서버에서 보는 메세지 전달
                    }
                    
                }
            } catch (IOException e) {
                // 사용접속종료시 여기서 에러 발생 -> 클라이언트 나감 -> 여기서 리무브 클라이언트 처리 
                removeClient(nick);
            }
        }
    }
}



