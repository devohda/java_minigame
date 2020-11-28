package socket;


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
            System.out.println("?ÑúÎ≤? ?ó∞Í≤∞ÏôÑÎ£?.");
 
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());         
            
            out.writeUTF(nickName);
            while (in != null) {
            	System.out.println("11");
                msg = (String)in.readUTF();
                System.out.println("12");
              
     // ----------------------------------------Í≤åÏûÑÎ≥? ?ç∞?ù¥?Ñ∞ Ï≤òÎ¶¨-----------------------------------
                // ---------Î≥ëÎöúÍª? flow Ï≤òÎ¶¨--------------
                if ( msg.startsWith("[TRUE OR FALSE]") && guiBottle!=null) {
                	guiBottle.initTurn();

                	if(msg.substring(15) == "?†ï?ãµ!") {
                		// Ï¢ÖÎ£åÏ∞? ?ùÑ?ö∞Í∏?
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
                // ----------------?õàÎØºÏ†ï?ùå-----------------------------------------------------
                if (msg.startsWith("[HUNMININDEX]") && guiHunmin!=null) {
                	int nSplit = msg.indexOf("/"); 
                	hunminIndex[0] = Integer.parseInt(msg.substring(13,nSplit)); 
                	hunminIndex[1] = Integer.parseInt(msg.substring(nSplit+1)); 
                	System.out.println(hunminIndex[0] + "@@" + hunminIndex[1]);
                	guiHunmin.init();
                }
                else if (guiHunmin!=null) guiHunmin.appendMsg(msg); // ?ó¨Í∏∞ÏÑú ?ò§Î•? ?Ç¨?óà?ùå ==> ?ï¥?ãπ Í≥†Í∞ù?ì§?ù¥ Í≤åÏûÑ ?ã§?ñâ?†Ñ ?ì§?ñ¥?ò§Î©? gui ?äî null?ù¥Í∏∞Ïóê
                // --------------?†ÑÍµ??Ö∏?ûò?ûê?ûë-----------------------------------------------------
                if (msg.startsWith("[SINGERINDEX]") && guiSing!=null) {
                	singerIndex = Integer.parseInt(msg.substring(13)); 
                	guiSing.init();
                }
                else if (guiSing!=null) guiSing.appendMsg(msg);
                // ----------------Ï∫êÏπòÏ∫êÏπò------------------------------------------------------
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
                // -----------------Í≤∞Ìï©------------------------------------------------------------
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
 
    // -----------------------------------Ï±ÑÌåÖÍµ¨ÌòÑ----------------------------------
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
 
    // ------------------------------------Î≥ëÎöúÍª?------------------------------------
    public int getRandNum() { return iRandNum; }
    
    // --------------------------------------?Ö∏?ûò?ûê?ûë---------------------------------
    public int getSingerIndex() { return singerIndex; }
    
    // ------------------------------------?õàÎØºÏ†ï?ùå-------------------------------------
    public int[] getHunminIndex() { return hunminIndex; }
  
    // ----------------------------------Ï∫êÏπòÏ∫êÏπò--------------------------------------
    public int[] getCatchPoint() { return Point; }
    
    // --------------------------------------Í≤∞Ìï©----------------------------------
    public int[][] getCombiIndex() { return indexCombi; }
    
}




