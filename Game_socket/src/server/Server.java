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
    
    // ���Ѳ�
    private String WrongAnswers= "";
	private int filled = 0;
	private int iWrongAns[] = new int[50];
	private int iRanNum = 7;
    
    // ĳġĳġ
  	private int point[];
  	
  	// ����
  	private Card[] boardInfoCombination;
  	private int[][] indexCombi = new int[9][3];
 
    // Ŭ���̾�Ʈ���� ��������
    private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
 
    public void setting() throws IOException {
        Collections.synchronizedMap(clientsMap); // ��������
        serverSocket = new ServerSocket(7777);
        while (true) {
            // Ŭ���̾�Ʈ ���� �ޱ�
            System.out.println("���� �����...");
            socket = serverSocket.accept(); // ���� ������ ������ ��� �ݺ��ؼ� ����ڸ� ����
            System.out.println(socket.getInetAddress() + "���� �����߽��ϴ�.");
            // ���⼭ ���ο� ����� ������ Ŭ���� �����ؼ� ���������� ����
            Receiver receiver = new Receiver(socket);
            receiver.start();
        }
    }
 
    public static void main(String[] args) throws IOException {
        Server serverBackground = new Server();
        serverBackground.setting();
    }
 
    // ���ǳ���(Ŭ���̾�Ʈ) ����� ����
    public void addClient(String nick, DataOutputStream out) throws IOException {
        sendMessage("<<<<<<< " + nick + "���� �����ϼ̽��ϴ�. >>>>>>>\n");
        clientsMap.put(nick, out);
    }
 
    public void removeClient(String nick) {
        sendMessage("<<<<<<< " + nick + "���� �����̽��ϴ�. >>>>>>>\n");
        clientsMap.remove(nick);
    }
 
    // �޽��� ���� ����
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
    
    // ----------------------------------���� ���� init�Լ� �������ֱ�---------------------------------
    
    // ----------------------------------���Ѳ�--------------------------------------------------
    public void init() {
    	
    	Random generator = new Random();
		iRanNum = generator.nextInt(50)+1; //1~50 �������� ����
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
		// ���� ä�ñ���
		sendMessage("[TRUE OR FALSE]" + WrongAnswers);
    	
    }
    
    private String result(int iRandNum, int iAnswer, String wrongStr) {

		int tmp,i,j;
		String str ="";
		
		if( iRandNum != iAnswer) {	
			iWrongAns[filled] = iAnswer; 
			filled++;
			
			for(i=filled-1; i>0; i--) { // Ʋ�� ����� ������������ �����ϴ� Bubble Sort
				for(j=0; j<i; j++) {
					if(iWrongAns[j] > iWrongAns[j+1] ) {
						tmp = iWrongAns[j];
						iWrongAns[j] = iWrongAns[j+1];
						iWrongAns[j+1] = tmp;
					}
				}
			}
			for(i=0; i<filled; i++) { // Label�� ǥ���ϱ����� ���� int�迭�� ���ڿ��� ����
				str += String.valueOf(iWrongAns[i]) + " ";
			}
		}
		else {
			str = "����!";
		}
		return str;

	}
    
    public int getRandNum() { return iRanNum; }
    
    // ----------------------------------�����뷡�ڶ�--------------------------------------------------
    public void singerIndex() {
    	int singerIndex = (int)(Math.random()*3);
    	sendMessage("[SINGERINDEX]" + singerIndex);
    }
    
    // -----------------------------------�ƹ�����--------------------------------------------------
    public void hunminIndex() {
    	int[] hunminIndex = new int[2];
    	hunminIndex[0] = (int)(Math.random()*14);
    	hunminIndex[1] = (int)(Math.random()*14);
    	sendMessage("[HUNMININDEX]" + hunminIndex[0] + "/" + hunminIndex[1]);
    }
    
    // -----------------------------------ĳġĳġ---------------------------------------------------
    public void initCatch() {
    	Random generator = new Random();
    	
    	point = new int[25]; // 0~4 : 2�� , 5~9 : 1�� , 10~14 : 0�� , 15~19 : -1��, 20~24 : -2��
		
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
    
    // -------------------------------------����-------------------------------------
    public void initCombination() {
    	boardInfoCombination = new Card[9];
    	
    	for (int i = 0; i < 9; i++) {
            Card tmpCombi = new Card();

            boolean duplicated = false;
            //�ߺ� ���� ī���� �����
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
				//System.out.println("���ҽ���");
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
 
        // ��Ʈ��ũ ó�� Ŭ���̾�Ʈ�κ� �а� ���� ������ ó���� �ְԲ�
        public Receiver(Socket socket) throws IOException {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            nick = in.readUTF();
            addClient(nick, out);
        }
 
        public void run() {
            try {// ��� ��⸸
                while (in != null) {
                    msg = in.readUTF();
                    
                    // ���Ӻ��� ���ǹ����� ����
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
                // ������������ ���⼭ ���� �߻� -> Ŭ���̾�Ʈ ���� -> ���⼭ ������ Ŭ���̾�Ʈ ó�� 
                removeClient(nick);
            }
        }
    }
}



