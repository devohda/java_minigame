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
    
    // 癰귣쵎�뒏�뙼占�
    private int iRandNum;
    // 占쎈걗占쎌삋占쎌쁽占쎌삂
    private int singerIndex;
    // 占쎌뜒沃섏눘�젟占쎌벉
    private int[] hunminIndex = new int[2];
    // 筌�癒��뒄筌�癒��뒄
    private int[] Point = new int[25];
    // 野껉퀬鍮�
    private int[][] indexCombi = {{1,1,0}, {1,2,0}, {2,1,2}, {2,2,2}, {0,1,1}, {0,2,2}, {0,2,1}, {1,0,2}, {1,2,1}};
 
    // 占쎌삏占쎈쑁野껊슣�뿫
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
            System.out.println("占쎄퐣甕곤옙 占쎈염野껉퀡留�.");
 
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
 
            // 占쎌젔占쎈꺗占쎈릭占쎌쁽筌띾뜆�쁽 占쎈빏占쎄퐬占쎌뿫 占쎌읈占쎈꽊
            System.out.println(nickName);
            
            out.writeUTF(nickName);
            System.out.println("占쎄깻占쎌뵬占쎌뵠占쎈섧占쎈뱜 : 筌롫뗄�뻻筌욑옙 占쎌읈占쎈꽊占쎌끏�뙴占�");
            while (in != null) {
            	System.out.println("11");
                msg = (String)in.readUTF();
                System.out.println("12");
              
     // ----------------------------------------野껊슣�뿫癰귨옙 占쎈쑓占쎌뵠占쎄숲 筌ｌ꼶�봺-----------------------------------
                // ---------癰귣쵎�뒏�뙼占� flow 筌ｌ꼶�봺--------------
                if ( msg.startsWith("[TRUE OR FALSE]") && guiBottle!=null) {
                	guiBottle.initTurn();

                	if(msg.substring(15) == "�젙�떟!") {
                		 System.out.println("占쎄돌揶쏉옙占쎌쁽 占쎌젫占쎌뵬");
                		// �넫�굝利븝㎕占� 占쎌뱽占쎌뒭疫뀐옙
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
                // ----------------占쎌뜒沃섏눘�젟占쎌벉-----------------------------------------------------
                if (msg.startsWith("[HUNMININDEX]") && guiHunmin!=null) {
                	int nSplit = msg.indexOf("/"); 
                	hunminIndex[0] = Integer.parseInt(msg.substring(13,nSplit)); 
                	hunminIndex[1] = Integer.parseInt(msg.substring(nSplit+1)); 
                	System.out.println(hunminIndex[0] + "@@" + hunminIndex[1]);
                	guiHunmin.init();
                }
                else if (guiHunmin!=null) guiHunmin.appendMsg(msg); // 占쎈연疫꿸퀣苑� 占쎌궎�몴占� 占쎄텢占쎈�占쎌벉 ==> 占쎈퉸占쎈뼣 �⑥쥒而쇽옙諭억옙�뵠 野껊슣�뿫 占쎈뼄占쎈뻬占쎌읈 占쎈굶占쎈선占쎌궎筌롳옙 gui 占쎈뮉 null占쎌뵠疫꿸퀣肉�
                // --------------占쎌읈�뤃占쏙옙�걗占쎌삋占쎌쁽占쎌삂-----------------------------------------------------
                if (msg.startsWith("[SINGERINDEX]") && guiSing!=null) {
                	singerIndex = Integer.parseInt(msg.substring(13)); 
                	guiSing.init();
                }
                else if (guiSing!=null) guiSing.appendMsg(msg);
                // ----------------筌�癒��뒄筌�癒��뒄------------------------------------------------------
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
                // -----------------野껉퀬鍮�------------------------------------------------------------
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
 
    // -----------------------------------筌�袁る샒�뤃�뗭겱----------------------------------
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
 
    // ------------------------------------癰귣쵎�뒏�뙼占�------------------------------------
    public int getRandNum() { return iRandNum; }
    
    // --------------------------------------占쎈걗占쎌삋占쎌쁽占쎌삂---------------------------------
    public int getSingerIndex() { return singerIndex; }
    
    // ------------------------------------占쎌뜒沃섏눘�젟占쎌벉-------------------------------------
    public int[] getHunminIndex() { return hunminIndex; }
  
    // ----------------------------------筌�癒��뒄筌�癒��뒄--------------------------------------
    public int[] getCatchPoint() { return Point; }
    
    // --------------------------------------野껉퀬鍮�----------------------------------
    public int[][] getCombiIndex() { return indexCombi; }
    
}




