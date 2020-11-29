package socket;


import frame_panel.Test;
import game.hunmingame.HunMinGame;
import game.singer.Singer;
import game.combination.Game;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import game.bottlecap.BottleCapPanel;
import game.catchCatch.CatchCatchPanel;
 
public class ClientGame {
 
	// 네트워크 처리를 위한 변수들 생성
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    
    // 각 게임별 gui연결을 위한 객체 선언
    private Test gui;
    private HunMinGame guiHunmin;
    private Singer guiSing;
    private BottleCapPanel guiBottle;
    private CatchCatchPanel guiCatch;
    private Game guiCombination;
    
    private String msg;
    private String nickName;
    
    // game.bottlecap
    private int iRandNum;
    // singcontest
    private int singerIndex;
    // hunmin
    private int[] hunminIndex = new int[2];
    // catchcatch
    private int[] Point = new int[25];
    // game.combination
    private int[][] indexCombi = {{1,1,0}, {1,2,0}, {2,1,2}, {2,2,2}, {0,1,1}, {0,2,2}, {0,2,1}, {1,0,2}, {1,2,1}};   
    
    public void setGui(Test gui) {
        this.gui = gui; // 초기 게임 실행 연결하는 gui
    }
    
    public void setGuiHunmin(HunMinGame gui) {
        this.guiHunmin = gui; // 훈민정음 gui
    }
    
    public void setGuiSing(Singer gui) {
        this.guiSing = gui; // 전국노래자랑 gui
    }
    
    public void setGuiBottle(BottleCapPanel gui) {
        this.guiBottle = gui; // 보틀캡 gui
    }
    
    public void setGuiCatch(CatchCatchPanel gui) {
        this.guiCatch = gui; // 캐치캐치 gui
    }
    
    public void setGuiCombination(Game gui) {  	   	    
        this.guiCombination = gui; // 결합 gui
    }
 
    public void connet() {
        try {
            socket = new Socket("127.0.0.1", 7777); // 서버 소켓이 생성된 컴퓨터의 서버 ip와 port 번호를 통해 클라이언트 객체 생성(여기서는 하나의 컴퓨터로 생성 및 실행하므로 127.0.0.1을 사용)
            System.out.println("서버 연결완료."); // 서버 연결 시 콘솔 창에 메세지 출력
 
            out = new DataOutputStream(socket.getOutputStream()); // OutputStream을 처리하는 변수
            in = new DataInputStream(socket.getInputStream()); // InputStream을 처리하는 변수        
            
            out.writeUTF(nickName); //닉네임 서버로 전달
            while (in != null) {
                msg = (String)in.readUTF(); // 서버에서 보낸 메세지 읽어들이기
              
     // ----------------------------------------게임별 데이터 처리-----------------------------------
     // ---------------------------------------------------------------------------------------      
                
                // ---------병뚜껑 flow 처리--------------
                if ( msg.startsWith("[TRUE]") && guiBottle!=null) { // 정답 시
                	guiBottle.initTurn();               	// 플레이어 순서 초기화

                	guiBottle.exit(); // 종료창 띄우기
                	guiBottle.appendWrongAns(msg); // 오답 판에 정답 메세지 출력
                	
                } 
                else if ( msg.startsWith("[FALSE]") && guiBottle!=null) { // 오답 시
                	guiBottle.initTurn();  // 플레이어 순서 초기화
                	
                	guiBottle.audio1(); // 오답 오디오 실행
                	guiBottle.appendWrongAns(msg); // 오답 판에 정답 메세지 출력

                }
                else if ( msg.startsWith("[init]") && guiBottle!=null) guiBottle.init(); // 보틀캡 게임 초기화
                else if ( msg.startsWith("[RandNum]") && guiBottle!=null) { // 병뚜껑 숫자 전달
                	System.out.println(msg);
                	iRandNum = Integer.parseInt(msg.substring(9));
                	System.out.println(iRandNum);
                }
                else if (guiBottle!=null) guiBottle.appendMsg(msg); // 채팅 내용 채팅창에 출력
                // ----------------훈민정음-----------------------------------------------------
                if (msg.startsWith("[HUNMININDEX]") && guiHunmin!=null) { // 훈민정음 게임을 위한 인덱스 전달 및 초기화
                	int nSplit = msg.indexOf("/"); 
                	hunminIndex[0] = Integer.parseInt(msg.substring(13,nSplit)); 
                	hunminIndex[1] = Integer.parseInt(msg.substring(nSplit+1)); 
                	System.out.println(hunminIndex[0] + "@@" + hunminIndex[1]);
                	guiHunmin.init();
                }
                else if (guiHunmin!=null) guiHunmin.appendMsg(msg); //채팅 내용 채팅창에 출력, 여기서 오류 났었음 ==> 해당 고객들이 게임 실행전 들어오면 gui 는 null이기에
                // --------------전국노래자랑-----------------------------------------------------
                if (msg.startsWith("[SINGERINDEX]") && guiSing!=null) { // 전국노래자랑 게임을 위한 인덱스 전달 및 초기화
                	singerIndex = Integer.parseInt(msg.substring(13)); 
                	guiSing.init();
                }
                else if (guiSing!=null) guiSing.appendMsg(msg); // 채팅 내용 채팅창에 출력
                // ----------------캐치캐치------------------------------------------------------
                if (msg.startsWith("[POINTINDEX]") && guiCatch!=null) { // 캐치캐치 게임 초기화
                	int start = 12;
                	int nSplit = msg.indexOf("/"); 
                	for(int i=0; i<25; i++) {
                		Point[i] = Integer.parseInt(msg.substring(start, nSplit));
                		start = nSplit + 1;
                		nSplit = msg.indexOf("/", start);
                	}
                	guiCatch.initGame();
                }
                else if (msg.startsWith("[BOXCATCH]") && guiCatch!=null) {  // 캐치캐치 박스 클릭에 따른 보상 값 적용
                	int catchIndex = Integer.parseInt(msg.substring(10)); 
                	guiCatch.BoxOff(catchIndex);
                }
                else if (guiCatch!=null) guiCatch.appendMsg(msg); // 채팅 내용 채팅창에 출력
                // -----------------결합------------------------------------------------------------
                if (msg.startsWith("[COMBINATIONINIT]") && guiCombination!=null) { //결합 카드게임을 위한 인덱스 정보 전달
                	int cnt = 0;
                	for(int i=0; i<9; i++) {
            			for(int j=0; j<3; j++) {
            				indexCombi[i][j] = Integer.parseInt(msg.substring(17+cnt, 17+cnt+1));
            				cnt++;				
            			}
            		}
                	
                	guiCombination.init();
                }
                else if (msg.startsWith("[GEUL]") && guiCombination!=null) { // 결을 누른 경우 정답 또는 오답에 따른 플레이어의 점수 갱신
                	guiCombination.geulScore(Integer.parseInt(msg.substring(6)));
                }
                else if (msg.startsWith("[HAP]") && guiCombination!=null) { // 합을 누른경우 경우 정답 또는 오답에 따른 플레이어의 점수 갱신
                	guiCombination.hapScore(msg.substring(6), Integer.parseInt(msg.substring(5,6)));
                }
                else if (guiCombination!=null) ;//guiCombination.appendMsg(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    // -----------------------------------채팅구현----------------------------------
    public void sendMessage(String msg2) {
        try {
            out.writeUTF(msg2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
 
    public String getNickname() {
       return nickName;
    }
    
    public void setNickname(String nickName) {
        this.nickName = nickName;
    }
 
    // ------------------------------------병뚜껑------------------------------------
    public int getRandNum() { return iRandNum; }
    
    // --------------------------------------노래자랑---------------------------------
    public int getSingerIndex() { return singerIndex; }
    
    // ------------------------------------훈민정음-------------------------------------
    public int[] getHunminIndex() { return hunminIndex; }
  
    // ----------------------------------캐치캐치--------------------------------------
    public int[] getCatchPoint() { return Point; }
    
    // --------------------------------------결합----------------------------------
    public int[][] getCombiIndex() { return indexCombi; }
    
}




