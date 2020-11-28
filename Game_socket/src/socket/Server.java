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
    
    // ���Ѳ�
    private String WrongAnswers= "";
	private int filled = 0;
	private int iWrongAns[] = new int[50];
	private int iRanNum = 7;
    
    // ĳġĳġ
  	private int point[];
  	
  	// ����
  	private Card[] boardInfoCombination; // ���ӱ����� ���� ī������ ��ü
  	private int[][] indexCombi = new int[9][3];
 
    // Ŭ���̾�Ʈ���� ��������
    private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
 
    public void setting() throws IOException {
        Collections.synchronizedMap(clientsMap); // Ŭ���̾�Ʈ ���� �� ���� ����
        serverSocket = new ServerSocket(7777); // �����ϴ� ��ǻ���� ip���� ��Ʈ��ȣ 7777������ ���� ���� ����
        while (true) {
            // Ŭ���̾�Ʈ ���� �ޱ�
            System.out.println("���� �����...");
            socket = serverSocket.accept(); // ������ ��� �ݺ��ؼ� Ŭ���̾�Ʈ�� ����
            System.out.println(socket.getInetAddress() + "���� �����߽��ϴ�."); // ������ Ŭ���̾�Ʈ�� ip�� ���
            
            // ���ο� ����� ������ Ŭ���� ���� �� ���������� ����
            Receiver receiver = new Receiver(socket);
            receiver.start();
        }
    }
 
    public static void main(String[] args) throws IOException {
        Server serverBackground = new Server(); // ��ü ����
        serverBackground.setting(); // setting �Լ� ȣ��
    }
 
    // Ŭ���̾�Ʈ ����
    public void addClient(String nick, DataOutputStream out) throws IOException {
        sendMessage("<<<<<<< " + nick + "���� �����ϼ̽��ϴ�. >>>>>>>\n");
        clientsMap.put(nick, out);
    }
    // Ŭ���̾�Ʈ ����
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
                clientsMap.get(key).writeUTF(msg); // �޼��� ��� Ŭ���̾�Ʈ�� ����
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    // ----------------------------------���� ���� �Լ� ����----------------------------------------- //
    // ---------------------------------------------------------------------------------------- //
    
    
    // ----------------------------------���Ѳ�--------------------------------------------------
    public void init() {
    	
    	Random generator = new Random();
		iRanNum = generator.nextInt(20)+1; //1~20 �������� ����
		WrongAnswers = ""; // ���� �Ǵ� ������� ������ ���� �ִ� ���ڿ�
		int[] ar = new int[50];
		filled=0;
		iWrongAns= ar; // �Է¹��� ������� ������ ����ϴ� �迭
		System.out.println(iRanNum);
		sendMessage("[init]"); // Ŭ���̾�Ʈ�� �ʱ�ȭ �޼��� ����
		sendMessage("[RandNum]" + iRanNum); // Ŭ���̾�Ʈ�� ���ο� ���� ����(���Ѳ� ����) �޼��� ����
    }
    
    public void Answer(String ans) {
    	
    	int num = Integer.parseInt(ans.substring(8)); // ans���ڿ� �ε��� 8������ ���ڿ� ���ڷ� ���� 
    	WrongAnswers = result(iRanNum, num, WrongAnswers); // ���� �� ������ ����� �����ϴ� result�Լ� ȣ��
    	
    	if (WrongAnswers == "correct!") sendMessage("[TRUE]" + WrongAnswers); // ���� �޼��� ����
    	else sendMessage("[FALSE]" + WrongAnswers); // ���� �޼��� ����
    }
    
    private String result(int iRandNum, int iAnswer, String wrongStr) {

		int tmp,i,j;
		String str ="";
		
		if( iRandNum != iAnswer) {	// ������ ���
			iWrongAns[filled] = iAnswer; // ���� �Է¹��� ���� ���
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
		else { // ���� �� ���
			str = "correct!"; // ��ȯ ���� ���乮�� ���� 
		}
		return str;

	}
    
    public int getRandNum() { return iRanNum; }
    
    // ----------------------------------�����뷡�ڶ�--------------------------------------------------
    public void singerIndex() {
    	int singerIndex = (int)(Math.random()*25); // 0~24������ �ε����� �������� ����
    	sendMessage("[SINGERINDEX]" + singerIndex); // �������� ������ �ε��� �޼��� ����
    }
    
    // -----------------------------------�ƹ�����--------------------------------------------------
    public void hunminIndex() {
    	int[] hunminIndex = new int[2];
    	hunminIndex[0] = (int)(Math.random()*14); // 0~13������ �ε����� �������� ����
    	hunminIndex[1] = (int)(Math.random()*14); // 0~13������ �ε����� �������� ����
    	sendMessage("[HUNMININDEX]" + hunminIndex[0] + "/" + hunminIndex[1]); // �������� ������ �� ���� �ε��� �޼��� ����
    }
    
    // -----------------------------------ĳġĳġ---------------------------------------------------
    public void initCatch() {
    	Random generator = new Random();
    	
    	// ĳġĳġ 5x5 ������ ������ ���� ���� ����
    	point = new int[25]; // 0~4 : 2�� , 5~9 : 1�� , 10~14 : 0�� , 15~19 : -1��, 20~24 : -2��
		
		for(int i=0; i<25; i++) {
			point[i] = generator.nextInt(25); // �������� 0~24�� ���� ���� �� ����
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
		sendMessage("[POINTINDEX]" + concat); // �������� ������ ���� ���� Ŭ���̾�Ʈ�鿡 ����
    }
    
    public void rewardCatch(String r) {
    	int indexCatch = Integer.parseInt(r.substring(13)); // ���ӿ��� Ŭ���� �ڽ� ��ü�� �ε����� �ǹ���
    	sendMessage("[BOXCATCH]" + indexCatch); // ��� Ŭ���̾�Ʈ�� Ŭ�� �� �ڽ� �ε��� ���� �޼��� ����
    }
    
    public int[] getRandPoint() { return point; }
    
    // -------------------------------------����-------------------------------------
    public void initCombination() {
    	boardInfoCombination = new Card[9]; // ���� ���� ������ ���� ī�� ��ü 9�� ����
    	
    	for (int i = 0; i < 9; i++) {
            Card tmpCombi = new Card();

            boolean duplicated = false;
            //�ߺ� ���� ī���� �����
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
            // �ߺ����� ���� ��� ��ü�� ����
            if(!duplicated) {
            	boardInfoCombination[i] = tmpCombi;
            	indexCombi[i] = tmpCombi.getIndexCombi();
            }
            else i--; // �ߺ��Ѵ� ��� �ٽ� ī�� ������ ��� ���� ����
    	}
    	
    	String concat = "";
		for(int i=0; i<9; i++) {
			for(int j=0; j<3; j++) {
				concat += indexCombi[i][j];
			}
		}
    	sendMessage("[COMBINATIONINIT]" + concat); // ��� Ŭ���̾�Ʈ�� �ʱ�ȭ �޼��� ����(ī�� ��ü ����)
    }
    
    // ���ӿ��� �÷��̾ ���� Ŭ���� ���
    public void geul(String m) {
    	String str = m.substring(17); // �÷��̾��� ���� ����
    	sendMessage("[GEUL]" + str); // ��� Ŭ���̾�Ʈ�� ���� �Էµƴٴ� �޼����� �Է��� �÷��̾��� ���� ����
    }
    
    // ���ӿ��� �÷��̾ ���� Ŭ���� ���
    public void hap(String m) {
    	String str1 = m.substring(16, 17); // �÷��̾��� ���� ����
    	String str2 = m.substring(17); // �Է��� ���� ����
    	sendMessage("[HAP]" + str1 + str2); // ��� Ŭ���̾�Ʈ�� ���� �Էµƴٴ� �޼����� �÷��̾��� ���� �� �Է��� ���� ����
    }
    
    
    // -----------------------------------------------------------------------------
    class Receiver extends Thread {
        private DataInputStream in;
        private DataOutputStream out;
        private String nick;
 
        // ��Ʈ��ũ ó�� Ŭ���̾�Ʈ�κ� �а� ���� ������ ó���� �ְԲ�
        public Receiver(Socket socket) throws IOException {
            out = new DataOutputStream(socket.getOutputStream()); // OutputStream ó���� ���� ����
            in = new DataInputStream(socket.getInputStream()); // InputStream ó���� ���� ����
            nick = in.readUTF(); // �÷��̾���� ������ �г��� �о���̱�
            addClient(nick, out); // �г����� �̿��� Ŭ���̾�Ʈ ���� map�� �߰�
        }
 
        public void run() {
            try {// ��� ��⸸
                while (in != null) {
                    msg = in.readUTF();
                    
                    // ���Ӻ��� ���ǹ����� ����
                    if (msg.startsWith("[initBottle]")) init(); // ��Ʋĸ ���� �ʱ�ȭ�� ���� �Լ� ȣ��
                    else if (msg.startsWith("[Answer]")) Answer(msg); // ��Ŭĸ ���� ���� �Ǻ����� �Լ� ȣ��
                    else if (msg.startsWith("[SINGERINIT]")) singerIndex(); // �����뷡�ڶ� �ʱ�ȭ�� ���� �Լ� ȣ��
                    else if (msg.startsWith("[HUNMININIT]")) hunminIndex(); // �ƹ����� �ʱ�ȭ�� ���� �Լ� ȣ��
                    else if (msg.startsWith("[CATCHINIT]")) initCatch(); // ĳġĳġ ���� �ʱ�ȭ�� ���� �Լ� ȣ��
                    else if (msg.startsWith("[REWARDCATCH]")) rewardCatch(msg); // ĳġĳġ Ŭ���� ���ڿ� ���� ���� ó���� ���� �Լ� ȣ��
                    else if (msg.startsWith("[COMBINATIONINIT]")) initCombination(); // ���� ���� �ʱ�ȭ�� ���� �Լ� ȣ��
                    else if (msg.startsWith("[GEULCOMBINATION]")) geul(msg); // ���հ��ӿ��� ���� ������� ó���ϴ� �Լ� ȣ��
                    else if (msg.startsWith("[HAPCOMBINATION]")) hap(msg); // ���հ��ӿ��� ���� ������� ó���ϴ� �Լ� ȣ��
                    else {
                    	sendMessage(msg); //�������� ���� �޼��� ����
                    }
                    
                }
            } catch (IOException e) {
                // ������������ ���⼭ ���� �߻� -> Ŭ���̾�Ʈ ���� -> ���⼭ ������ Ŭ���̾�Ʈ ó�� 
                removeClient(nick);
            }
        }
    }
}



