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

    // ��Ʈ��ũ ó���� ���� ������ ����
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    // �� ���Ӻ� gui������ ���� ��ü ����
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
        this.gui = gui; // �ʱ� ���� ���� �����ϴ� gui
    }

    public void setGuiHunmin(HunMinGame gui) {
        this.guiHunmin = gui; // �ƹ����� gui
    }

    public void setGuiSing(Singer gui) {
        this.guiSing = gui; // �����뷡�ڶ� gui
    }

    public void setGuiBottle(BottleCapPanel gui) {
        this.guiBottle = gui; // ��Ʋĸ gui
    }

    public void setGuiCatch(CatchCatchPanel gui) {
        this.guiCatch = gui; // ĳġĳġ gui
    }

    public void setGuiCombination(Game gui) {
        this.guiCombination = gui; // ���� gui
    }

    public void connet() {
        try {
            socket = new Socket("127.0.0.1", 7777); // ���� ������ ������ ��ǻ���� ���� ip�� port ��ȣ�� ���� Ŭ���̾�Ʈ ��ü ����(���⼭�� �ϳ��� ��ǻ�ͷ� ���� �� �����ϹǷ� 127.0.0.1�� ���)
            System.out.println("���� ���� �Ϸ�"); // ���� ���� �� �ܼ� â�� �޼��� ���

            out = new DataOutputStream(socket.getOutputStream()); // OutputStream�� ó���ϴ� ����
            in = new DataInputStream(socket.getInputStream()); // InputStream�� ó���ϴ� ����

            out.writeUTF(nickName); //�г��� ������ ����
            while (in != null) {
                msg = (String)in.readUTF(); // �������� ���� �޼��� �о���̱�

                // ----------------------------------------���Ӻ� ������ ó��-----------------------------------
                // ---------------------------------------------------------------------------------------

                // ---------���Ѳ� flow ó��--------------
                if ( msg.startsWith("[TRUE]") && guiBottle!=null) { // ���� ��
                    guiBottle.initTurn();               	// �÷��̾� ���� �ʱ�ȭ

                    guiBottle.exit(); // ����â ����
                    guiBottle.appendWrongAns(msg); // ���� �ǿ� ���� �޼��� ���

                }
                else if ( msg.startsWith("[FALSE]") && guiBottle!=null) { // ���� ��
                    guiBottle.initTurn();  // �÷��̾� ���� �ʱ�ȭ

                    guiBottle.audio1(); // ���� ����� ����
                    guiBottle.appendWrongAns(msg); // ���� �ǿ� ���� �޼��� ���

                }
                else if ( msg.startsWith("[init]") && guiBottle!=null) guiBottle.init(); // ��Ʋĸ ���� �ʱ�ȭ
                else if ( msg.startsWith("[RandNum]") && guiBottle!=null) { // ���Ѳ� ���� ����
                    System.out.println(msg);
                    iRandNum = Integer.parseInt(msg.substring(9));
                    System.out.println(iRandNum);
                }
                else if (guiBottle!=null) guiBottle.appendMsg(msg); // ä�� ���� ä��â�� ���
                // ----------------�ƹ�����-----------------------------------------------------
                if (msg.startsWith("[HUNMININDEX]") && guiHunmin!=null) { // �ƹ����� ������ ���� �ε��� ���� �� �ʱ�ȭ
                    int nSplit = msg.indexOf("/");
                    hunminIndex[0] = Integer.parseInt(msg.substring(13,nSplit));
                    hunminIndex[1] = Integer.parseInt(msg.substring(nSplit+1));
                    System.out.println(hunminIndex[0] + "@@" + hunminIndex[1]);
                    guiHunmin.init();
                }
                else if (guiHunmin!=null) guiHunmin.appendMsg(msg); //ä�� ���� ä��â�� ���, ���⼭ ���� ������ ==> �ش� ������ ���� ������ ������ gui �� null�̱⿡
                // --------------�����뷡�ڶ�-----------------------------------------------------
                if (msg.startsWith("[SINGERINDEX]") && guiSing!=null) { // �����뷡�ڶ� ������ ���� �ε��� ���� �� �ʱ�ȭ
                    singerIndex = Integer.parseInt(msg.substring(13));
                    guiSing.init();
                }
                else if (guiSing!=null) guiSing.appendMsg(msg); // ä�� ���� ä��â�� ���
                // ----------------ĳġĳġ------------------------------------------------------
                if (msg.startsWith("[POINTINDEX]") && guiCatch!=null) { // ĳġĳġ ���� �ʱ�ȭ
                    int start = 12;
                    int nSplit = msg.indexOf("/");
                    for(int i=0; i<25; i++) {
                        Point[i] = Integer.parseInt(msg.substring(start, nSplit));
                        start = nSplit + 1;
                        nSplit = msg.indexOf("/", start);
                    }
                    guiCatch.initGame();
                }
                else if (msg.startsWith("[BOXCATCH]") && guiCatch!=null) {  // ĳġĳġ �ڽ� Ŭ���� ���� ���� �� ����
                    int catchIndex = Integer.parseInt(msg.substring(10));
                    guiCatch.BoxOff(catchIndex);
                }
                else if (guiCatch!=null) guiCatch.appendMsg(msg); // ä�� ���� ä��â�� ���
                // -----------------����------------------------------------------------------------
                if (msg.startsWith("[COMBINATIONINIT]") && guiCombination!=null) { //���� ī������� ���� �ε��� ���� ����
                    int cnt = 0;
                    for(int i=0; i<9; i++) {
                        for(int j=0; j<3; j++) {
                            indexCombi[i][j] = Integer.parseInt(msg.substring(17+cnt, 17+cnt+1));
                            cnt++;
                        }
                    }

                    guiCombination.init();
                }
                else if (msg.startsWith("[GEUL]") && guiCombination!=null) { // ���� ���� ��� ���� �Ǵ� ���信 ���� �÷��̾��� ���� ����
                    guiCombination.geulScore(Integer.parseInt(msg.substring(6)));
                }
                else if (msg.startsWith("[HAP]") && guiCombination!=null) { // ���� ������� ��� ���� �Ǵ� ���信 ���� �÷��̾��� ���� ����
                    guiCombination.hapScore(msg.substring(6), Integer.parseInt(msg.substring(5,6)));
                }
                else if (guiCombination!=null) ;//guiCombination.appendMsg(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // -----------------------------------ä�ñ���----------------------------------
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

    // ------------------------------------���Ѳ�------------------------------------
    public int getRandNum() { return iRandNum; }

    // --------------------------------------�뷡�ڶ�---------------------------------
    public int getSingerIndex() { return singerIndex; }

    // ------------------------------------�ƹ�����-------------------------------------
    public int[] getHunminIndex() { return hunminIndex; }

    // ----------------------------------ĳġĳġ--------------------------------------
    public int[] getCatchPoint() { return Point; }

    // --------------------------------------����----------------------------------
    public int[][] getCombiIndex() { return indexCombi; }

}




