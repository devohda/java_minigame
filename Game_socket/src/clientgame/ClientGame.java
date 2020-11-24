package clientgame;


import frame_panel.test;
import hunmingame.HunMinGame;
import singer.Singer;
import jun_chang.BottleCapPanel;
import jun_chang.CatchCatchPanel;
import combination.Game;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
 
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
    
    // 蹂묐슌猿�
    private int iRandNum;
    // �끂�옒�옄�옉
    private int singerIndex;
    // �썕誘쇱젙�쓬
    private int[] hunminIndex = new int[2];
    // 罹먯튂罹먯튂
    private int[] Point = new int[25];
    // 寃고빀
    private int[][] indexCombi = {{1,1,0}, {1,2,0}, {2,1,2}, {2,2,2}, {0,1,1}, {0,2,2}, {0,2,1}, {1,0,2}, {1,2,1}};
 
    // �옖�뜡寃뚯엫
    // private int randomGame = 4; 
    
    
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
            System.out.println("�꽌踰� �뿰寃곕맖.");
 
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
 
            // �젒�냽�븯�옄留덉옄 �땳�꽕�엫 �쟾�넚
            System.out.println(nickName);
            
            out.writeUTF(nickName);
            System.out.println("�겢�씪�씠�뼵�듃 : 硫붿떆吏� �쟾�넚�셿猷�");
            while (in != null) {
            	System.out.println("11");
                msg = (String)in.readUTF();
                System.out.println("12");
              
     // ----------------------------------------寃뚯엫蹂� �뜲�씠�꽣 泥섎━-----------------------------------
                // ---------蹂묐슌猿� flow 泥섎━--------------
                if ( msg.startsWith("[TRUE OR FALSE]") && guiBottle!=null) {
                	guiBottle.initTurn();

                	if(msg.substring(15) == "정답!") {
                		 System.out.println("�굹媛��옄 �젣�씪");
                		// 醫낅즺李� �쓣�슦湲�
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
                // ----------------�썕誘쇱젙�쓬-----------------------------------------------------
                if (msg.startsWith("[HUNMININDEX]") && guiHunmin!=null) {
                	int nSplit = msg.indexOf("/"); 
                	hunminIndex[0] = Integer.parseInt(msg.substring(13,nSplit)); 
                	hunminIndex[1] = Integer.parseInt(msg.substring(nSplit+1)); 
                	System.out.println(hunminIndex[0] + "@@" + hunminIndex[1]);
                	guiHunmin.init();
                }
                else if (guiHunmin!=null) guiHunmin.appendMsg(msg); // �뿬湲곗꽌 �삤瑜� �궗�뿀�쓬 ==> �빐�떦 怨좉컼�뱾�씠 寃뚯엫 �떎�뻾�쟾 �뱾�뼱�삤硫� gui �뒗 null�씠湲곗뿉
                // --------------�쟾援��끂�옒�옄�옉-----------------------------------------------------
                if (msg.startsWith("[SINGERINDEX]") && guiSing!=null) {
                	singerIndex = Integer.parseInt(msg.substring(13)); 
                	guiSing.init();
                }
                else if (guiSing!=null) guiSing.appendMsg(msg);
                // ----------------罹먯튂罹먯튂------------------------------------------------------
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
                // -----------------寃고빀------------------------------------------------------------
                if (msg.startsWith("[COMBINATIONINIT]") && guiCombination!=null) {
                	//card = msg.substring(17);
                	int cnt = 0;
                	for(int i=0; i<9; i++) {
            			for(int j=0; j<3; j++) {
            				indexCombi[i][j] = Integer.parseInt(msg.substring(17+cnt, 17+cnt+1));
            				cnt++;
            				//System.out.println(indexCombi[i][j]);      				
            			}
            		}
                	
                	guiCombination.init();
                }
                else if (msg.startsWith("[GEUL]") && guiCombination!=null) {
                	guiCombination.geulScore();
                }
                else if (msg.startsWith("[HAP]") && guiCombination!=null) {
                	guiCombination.hapScore(msg.substring(5));
                }
                else if (guiCombination!=null) ;//guiCombination.appendMsg(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    // -----------------------------------梨꾪똿援ы쁽----------------------------------
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
 
    // ------------------------------------蹂묐슌猿�------------------------------------
    public int getRandNum() { return iRandNum; }
    
    // --------------------------------------�끂�옒�옄�옉---------------------------------
    public int getSingerIndex() { return singerIndex; }
    
    // ------------------------------------�썕誘쇱젙�쓬-------------------------------------
    public int[] getHunminIndex() { return hunminIndex; }
  
    // ----------------------------------罹먯튂罹먯튂--------------------------------------
    public int[] getCatchPoint() { return Point; }
    
    // --------------------------------------寃고빀----------------------------------
    public int[][] getCombiIndex() { return indexCombi; }
    
}




