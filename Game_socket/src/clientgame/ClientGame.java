package clientgame;


import frame_panel.test;
import hunmingame.HunMinGame;
import singer.Singer;
import combination.Game;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import bottlecap.BottleCapPanel;
import catchCatch.CatchCatchPanel;
 
public class ClientGame {
 
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    // gui
    private test gui;
    private HunMinGame guiHunmin;
    private Singer guiSing;
    private BottleCapPanel guiBottle;
    private CatchCatchPanel guiCatch;
    private Game guiCombination;
    
    private String msg;
    private String nickName;
    
    // bottlecap
    private int iRandNum;
    // singcontest
    private int singerIndex;
    // hunmin
    private int[] hunminIndex = new int[2];
    // catchcatch
    private int[] Point = new int[25];
    // combination
    private int[][] indexCombi = {{1,1,0}, {1,2,0}, {2,1,2}, {2,2,2}, {0,1,1}, {0,2,2}, {0,2,1}, {1,0,2}, {1,2,1}};   
    
    public void setGui(test gui) {
        this.gui = gui;
    }
    
    public void setGuiHunmin(HunMinGame gui) {
        this.guiHunmin = gui;
    }
    
    public void setGuiSing(Singer gui) {
        this.guiSing = gui;
    }
    
    public void setGuiBottle(BottleCapPanel gui) {
        this.guiBottle = gui;
    }
    
    public void setGuiCatch(CatchCatchPanel gui) {
        this.guiCatch = gui;
    }
    
    public void setGuiCombination(Game gui) {  	   	    
        this.guiCombination = gui;
    }
 
    public void connet() {
        try {
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("서버 연결완료.");
 
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());         
            
            out.writeUTF(nickName);
            while (in != null) {
            	System.out.println("11");
                msg = (String)in.readUTF();
                System.out.println("12");
              
     // ----------------------------------------게임별 데이터 처리-----------------------------------
                // ---------병뚜껑 flow 처리--------------
                if ( msg.startsWith("[TRUE OR FALSE]") && guiBottle!=null) {
                	guiBottle.initTurn();

                	if(msg.substring(15) == "정답!") {
                		// 종료창 띄우기
                		guiBottle.exit();
                	}
                	else {
                		guiBottle.audio1();
                	}
                	
                	guiBottle.appendWrongAns(msg);
                	
                }
                else if ( msg.startsWith("[init]") && guiBottle!=null) guiBottle.init();
                else if ( msg.startsWith("[RandNum]") && guiBottle!=null) {
                	System.out.println(msg);
                	iRandNum = Integer.parseInt(msg.substring(9));
                	System.out.println(iRandNum);
                }
                else if (guiBottle!=null) guiBottle.appendMsg(msg);
                // ----------------훈민정음-----------------------------------------------------
                if (msg.startsWith("[HUNMININDEX]") && guiHunmin!=null) {
                	int nSplit = msg.indexOf("/"); 
                	hunminIndex[0] = Integer.parseInt(msg.substring(13,nSplit)); 
                	hunminIndex[1] = Integer.parseInt(msg.substring(nSplit+1)); 
                	System.out.println(hunminIndex[0] + "@@" + hunminIndex[1]);
                	guiHunmin.init();
                }
                else if (guiHunmin!=null) guiHunmin.appendMsg(msg); // 여기서 오류 났었음 ==> 해당 고객들이 게임 실행전 들어오면 gui 는 null이기에
                // --------------전국노래자랑-----------------------------------------------------
                if (msg.startsWith("[SINGERINDEX]") && guiSing!=null) {
                	singerIndex = Integer.parseInt(msg.substring(13)); 
                	guiSing.init();
                }
                else if (guiSing!=null) guiSing.appendMsg(msg);
                // ----------------캐치캐치------------------------------------------------------
                if (msg.startsWith("[POINTINDEX]") && guiCatch!=null) {
                	int start = 12;
                	int nSplit = msg.indexOf("/"); 
                	for(int i=0; i<25; i++) {
                		Point[i] = Integer.parseInt(msg.substring(start, nSplit));
                		start = nSplit + 1;
                		nSplit = msg.indexOf("/", start);
                	}
                	for(int i=0; i<25; i++) System.out.println(Point[i]);
                	guiCatch.initGame();
                }
                else if (msg.startsWith("[BOXCATCH]") && guiCatch!=null) {
                	System.out.println("rewardCatch-client");
                	int catchIndex = Integer.parseInt(msg.substring(10)); 
                	System.out.println("rewardCatch-client" + catchIndex);
                	guiCatch.BoxOff(catchIndex);
                }
                else if (guiCatch!=null) guiCatch.appendMsg(msg);
                // -----------------결합------------------------------------------------------------
                if (msg.startsWith("[COMBINATIONINIT]") && guiCombination!=null) {
                	int cnt = 0;
                	for(int i=0; i<9; i++) {
            			for(int j=0; j<3; j++) {
            				indexCombi[i][j] = Integer.parseInt(msg.substring(17+cnt, 17+cnt+1));
            				cnt++;				
            			}
            		}
                	
                	guiCombination.init();
                }
                else if (msg.startsWith("[GEUL]") && guiCombination!=null) {
                	guiCombination.geulScore(Integer.parseInt(msg.substring(6)));
                }
                else if (msg.startsWith("[HAP]") && guiCombination!=null) {
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




