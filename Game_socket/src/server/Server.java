package server;


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
  	private Card[] boardInfoCombination;
  	private int[][] indexCombi = new int[9][3];
 
    // 클라이언트들의 정보저장
    private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
 
    public void setting() throws IOException {
        Collections.synchronizedMap(clientsMap); // 교통정리
        serverSocket = new ServerSocket(7777);
        while (true) {
            // 클라이언트 접속 받기
            System.out.println("서버 대기중...");
            socket = serverSocket.accept(); // 먼저 서버가 할일은 계속 반복해서 사용자를 받음
            System.out.println(socket.getInetAddress() + "에서 접속했습니다.");
            // 여기서 새로운 사용자 쓰레드 클래스 생성해서 소켓정보를 넣음
            Receiver receiver = new Receiver(socket);
            receiver.start();
        }
    }
 
    public static void main(String[] args) throws IOException {
        Server serverBackground = new Server();
        serverBackground.setting();
    }
 
    // 맵의내용(클라이언트) 저장과 삭제
    public void addClient(String nick, DataOutputStream out) throws IOException {
        sendMessage("<<<<<<< " + nick + "님이 접속하셨습니다. >>>>>>>\n");
        clientsMap.put(nick, out);
    }
 
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
                clientsMap.get(key).writeUTF(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    // ----------------------------------게임 별로 init함수 구분해주기---------------------------------
    
    // ----------------------------------병뚜껑--------------------------------------------------
    public void init() {
    	
    	Random generator = new Random();
		iRanNum = generator.nextInt(50)+1; //1~50 랜덤숫자 생성
		WrongAnswers = "";
		int[] ar = new int[50];
		filled=0;
		iWrongAns= ar;
		System.out.println(iRanNum);
		sendMessage("[init]");
		sendMessage("[RandNum]" + iRanNum);
    }
    
    public void Answer(String ans) {
    	
    	int num = Integer.parseInt(ans.substring(8));
    	WrongAnswers = result(iRanNum, num, WrongAnswers);
		// 수정 채팅구현
		sendMessage("[TRUE OR FALSE]" + WrongAnswers);
    	
    }
    
    private String result(int iRandNum, int iAnswer, String wrongStr) {

		int tmp,i,j;
		String str ="";
		
		if( iRandNum != iAnswer) {	
			iWrongAns[filled] = iAnswer; 
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
		else {
			str = "정답!";
		}
		return str;

	}
    
    public int getRandNum() { return iRanNum; }
    
    // ----------------------------------전국노래자랑--------------------------------------------------
    public void singerIndex() {
    	int singerIndex = (int)(Math.random()*3);
    	sendMessage("[SINGERINDEX]" + singerIndex);
    }
    
    // -----------------------------------훈민정음--------------------------------------------------
    public void hunminIndex() {
    	int[] hunminIndex = new int[2];
    	hunminIndex[0] = (int)(Math.random()*14);
    	hunminIndex[1] = (int)(Math.random()*14);
    	sendMessage("[HUNMININDEX]" + hunminIndex[0] + "/" + hunminIndex[1]);
    }
    
    // -----------------------------------캐치캐치---------------------------------------------------
    public void initCatch() {
    	Random generator = new Random();
    	
    	point = new int[25]; // 0~4 : 2점 , 5~9 : 1점 , 10~14 : 0점 , 15~19 : -1점, 20~24 : -2점
		
		for(int i=0; i<25; i++) {
			point[i] = generator.nextInt(25);
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
		sendMessage("[POINTINDEX]" + concat);
    }
    
    public void rewardCatch(String r) {
    	System.out.println("rewardCatch-server");
    	int indexCatch = Integer.parseInt(r.substring(13));
    	System.out.println("rewardCatch-server" + indexCatch);
    	sendMessage("[BOXCATCH]" + indexCatch);
    }
    
    public int[] getRandPoint() { return point; }
    
    // -------------------------------------결합-------------------------------------
    public void initCombination() {
    	boardInfoCombination = new Card[9];
    	
    	for (int i = 0; i < 9; i++) {
            Card tmpCombi = new Card();

            boolean duplicated = false;
            //중복 없는 카드판 만들기
            for(int j = 0 ; j < i; j++){
                if(tmpCombi.getCardColor() == boardInfoCombination[j].getCardColor()){
                    if (tmpCombi.getShape() == boardInfoCombination[j].getShape()) {
                        if (tmpCombi.getBackgroundColor() == boardInfoCombination[j].getBackgroundColor()){
                            System.out.println("same~!");
                            duplicated = true;
                            break;
                        }
                    }
                }
            }
            if(!duplicated) {
            	boardInfoCombination[i] = tmpCombi;
            	indexCombi[i] = tmpCombi.getIndexCombi();
            	System.out.println(i+" : " + indexCombi[i][0]+ " , " + indexCombi[i][1]+" , " +indexCombi[i][2]);
            }
            else i--;
    	}
    	
    	String concat = "";
		for(int i=0; i<9; i++) {
			for(int j=0; j<3; j++) {
				concat += indexCombi[i][j];
				//System.out.println("한텀쉬고");
			}
			//System.out.println(i+" : " + indexCombi[i][0]+ " , " + indexCombi[i][1]+" , " +indexCombi[i][2]);
		}
    	sendMessage("[COMBINATIONINIT]" + concat);
    	System.out.println("[COMBINATIONINIT]" + concat);
    }
    
    public void geul() {
    	sendMessage("[GEUL]");
    }
    
    public void hap(String m) {
    	String str = m.substring(16);
    	sendMessage("[HAP]" + str);
    }
    
    
    // -----------------------------------------------------------------------------
    class Receiver extends Thread {
        private DataInputStream in;
        private DataOutputStream out;
        private String nick;
 
        // 네트워크 처리 클라이언트로부 읽고 서버 단위로 처리해 주게끔
        public Receiver(Socket socket) throws IOException {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            nick = in.readUTF();
            addClient(nick, out);
        }
 
        public void run() {
            try {// 계속 듣기만
                while (in != null) {
                    msg = in.readUTF();
                    
                    // 게임별로 조건문으로 제어
                    if (msg.startsWith("[initBottle]")) init();
                    else if (msg.startsWith("[Answer]")) Answer(msg);
                    else if (msg.startsWith("[SINGERINIT]")) singerIndex();
                    else if (msg.startsWith("[HUNMININIT]")) hunminIndex();
                    else if (msg.startsWith("[CATCHINIT]")) initCatch();
                    else if (msg.startsWith("[REWARDCATCH]")) rewardCatch(msg);
                    else if (msg.startsWith("[COMBINATIONINIT]")) initCombination();
                    else if (msg.startsWith("[GEULCOMBINATION]")) geul();
                    else if (msg.startsWith("[HAPCOMBINATION]")) hap(msg);
                    else {
                    	sendMessage(msg);
                    }
                    
                }
            } catch (IOException e) {
                // 사용접속종료시 여기서 에러 발생 -> 클라이언트 나감 -> 여기서 리무브 클라이언트 처리 
                removeClient(nick);
            }
        }
    }
}



