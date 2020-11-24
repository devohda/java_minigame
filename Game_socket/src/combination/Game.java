package combination;


import tool.Customfont;
import tool.ResizeImg;

import clientgame.ClientGame;
import combination.Game.renewScore;
import frame_panel.*;

import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

public class Game extends JPanel {

    private JButton btnHap;
    private JButton btnGeul;
    private JTextField userinput;
    private JLabel lblScore;
    private JLabel lblState;
    private int _score;
    private GameBoardPanel gameBoard;

    private renewScore update; //actionListener class

    //��� �̹��� �����
    private ImageIcon background;
    private JLabel back;
    private Image resizeimg;

    //��Ʈ ����
    private Customfont makeFnt;
    Font fnt;

    //player
    private Player[] players;
    private int _playerNum;
    
    //���� �� ���Ӽ���ȭ�� �������� �̿��� ��ü
    private MainPanel main;
    
    // ��Ʈ��ũ ���� ī�� ���� ����
    private int[][] indexCombi = new int[9][3];
    // ��Ʈ��ũ �������� ��ư
 	private JButton btnReset;
 	
 	// ä�ñ���
 	private JTextArea chatArea;
 	private JScrollPane scrollPane;
 	private JTextField chat;
 	private String nickName; 
 	    
 	private String name; 
 	private ClientGame cl;

    public Game(ClientGame c, GameSelector g, MainPanel m) {
    	
    	main = m; //���� �� ���Ӽ���ȭ�� �������� �̿��� ��ü
    	
        _playerNum = g.getPeopleNum();
        System.out.println(_playerNum);
    	
    	cl = c;
    	
    	setBounds(125, 130, 800, 520); // Game �г� ũ�� ����
        setLayout(null);

        _score = 0; // ���� �ʱ�ȭ
        lblScore = new JLabel("���� : 0"); // lblScore �ʱ�ȭ
        lblState = new JLabel("������ �����մϴ�."); // lblState �ʱ�ȭ
        lblState.setHorizontalAlignment(JLabel.CENTER); //���� ��� ����
        lblState.setForeground(Color.black);

        btnHap = new JButton("��");
        btnGeul = new JButton("��");
        userinput = new JTextField(5);

        //add action listener
        update = new renewScore();
        btnHap.addActionListener(update);
        btnGeul.addActionListener(update);
        userinput.addActionListener(update);

        /************* ��Ʈ ���� **************/
        makeFnt = new Customfont();
        fnt = makeFnt.getCustomFont("font/���ü.ttf", Font.BOLD, 24);
        lblState.setFont(fnt);
        fnt = makeFnt.getCustomFont("font/���ü.ttf", Font.PLAIN, 22);
        lblScore.setFont(fnt);

        // ��ġ ����
        lblState.setBounds(170, 35, 500, 50);
        lblScore.setBounds(490, 120, 200, 50);
        btnGeul.setBounds(480, 380, 70, 30);
        btnHap.setBounds(480, 420, 70, 30);
        userinput.setBounds(560, 420, 160, 30);

        // ��Ʈ��ũ �������� ��ư
     	btnReset = new JButton("�ʱ�ȭ");
     	btnReset.setBounds(650, 35, 70, 30);
     	add(btnReset);
     	btnReset.addActionListener(new ResetListener());
        
        // ä��â ����		
     	/*
     	chatArea = new JTextArea(); 
     	scrollPane = new JScrollPane(chatArea);
     	chatArea.setEditable(false); 
     	scrollPane.setBounds(500, 0, 150, 675);
     	add(scrollPane);		
     	chat = new JTextField();
     	chat.setBounds(500, 675, 150, 25);
     	add(chat);
     	chat.addActionListener(new sendMessage());*/
        
        add(lblScore);
        add(lblState);
        add(btnHap);
        add(btnGeul);
        add(userinput);
        
        // ������ ��� ����
        ResizeImg img = new ResizeImg("images/bg_combination.jpg", 800, 540);
        resizeimg = img.getResizeImage();
        background = new ImageIcon(resizeimg);
        back = new JLabel("", background, SwingConstants.CENTER);
        back.setBounds(0, 0, 800, 540);
        add(back); // ��� �̹��� �������� ��ġ -> ���� ���ʿ� ��ġ�ϱ� ����
        
        cl.setGuiCombination(this);
		name = cl.getNickname();
		
		init();
        
    }//constructor
    
    // ----------------------��Ʈ��ũ--------------------------------
    public void appendMsg(String msg) {
        chatArea.append(msg);
    }     
	
	private class sendMessage implements ActionListener {
		public void actionPerformed(ActionEvent e) {
	        String msg = name + " : " + chat.getText() + "\n";
	        cl.sendMessage(msg);
	        chat.setText("");
	    }
	}
	
	private class ResetListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			cl.sendMessage("[COMBINATIONINIT]");
			//init();
	    }
	}
	// ----------------------��Ʈ��ũ--------------------------------

    private Card[] boardInfo;
    private ArrayList<HashSet> hapset;
    private ArrayList <HashSet> deleteHapset;

    public void init(){

        _score = 0;
        lblScore.setText("���� : " + _score);
        lblState.setText("������ �����մϴ�.");
        lblState.setForeground(Color.black);

        hapset = new ArrayList<>();
        deleteHapset = new ArrayList<>();

        boardInfo = new Card[9];
        System.out.println("1��");
        indexCombi = cl.getCombiIndex();
        System.out.println("2��");
        setGameBoardInfo(indexCombi); //���� �ʱ�ȭ --> �̰��� �������� �����ؼ� Ŭ���̾�Ʈ�� �����ٷ������� �ε����� �������� Ŭ���̾�Ʈ�� �ִ°ɷ�...
        System.out.println("4��");
        
        System.out.println("5��");
        
        gameBoard = new GameBoardPanel(boardInfo);
        gameBoard.setBounds(50, 120, 500, 500);
        add(gameBoard);
        repaint();

        System.out.println("6��");
        calculateHap();
        System.out.println("7��");
    }

    /*******************************/
    /******** ���� �� ����� *********/
    /*******************************/


    public void setGameBoardInfo(int[][] index){
    	System.out.println("3��");
        for (int i = 0; i < 9; i++) {
            Card tmp = new Card(index[i]);

            boolean duplicated = false;
            //�ߺ� ���� ī���� �����
            for(int j = 0 ; j < i; j++){
                if(tmp.getCardColor() == boardInfo[j].getCardColor()){
                    if (tmp.getShape() == boardInfo[j].getShape()) {
                        if (tmp.getBackgroundColor() == boardInfo[j].getBackgroundColor()){
                            System.out.println("11same~!");
                            //duplicated = true; --> �̹� �ش� �˰����� �������� �����ϰ� �� �ε������̹Ƿ� ���� 
                            break;
                        }
                    }
                }
            }
            if(!duplicated) boardInfo[i] = tmp;
            else i--;
        }
        
    }//setGameBoardInfo()


    /*******************************/
    /******* '��' ���� ���ϱ� ********/
    /*******************************/

    private boolean stateBg(Card a, Card b, Card c){

        boolean state = false;
        if ((a.getBackgroundColor() == b.getBackgroundColor()) && (b.getBackgroundColor() == c.getBackgroundColor()))
            state = true;
        if ((a.getBackgroundColor() != b.getBackgroundColor()) && (b.getBackgroundColor() != c.getBackgroundColor()) && (a.getBackgroundColor() != c.getBackgroundColor()))
            state = true;

        return state;
    }

    private boolean stateShape(Card a, Card b, Card c){

        boolean state = false;
        if ((a.getShape() == b.getShape()) && (b.getShape() == c.getShape()))
            state = true;
        if ((a.getShape() != b.getShape()) && (b.getShape() != c.getShape()) && (a.getShape() != c.getShape()))
            state = true;

        return state;
    }

    private boolean stateColor(Card a, Card b, Card c){
        boolean state = false;
        if ((a.getCardColor() == b.getCardColor()) && (b.getCardColor() == c.getCardColor()))
            state = true;
        if ((a.getCardColor() != b.getCardColor()) && (b.getCardColor() != c.getCardColor()) && (a.getCardColor()!= c.getCardColor()))
            state = true;

        return state;
    }

    private boolean isHap(Card a, Card b, Card c){
        return(stateBg(a,b,c)&&stateColor(a,b,c)&&stateShape(a,b,c));
    }


    public void calculateHap(){
        hapset = new ArrayList<>();
        int hapNum = 0;

        //ī�� �� ���� ������ ���� �� ã��
        for (int i = 0; i < 7; i++) {
            for (int j = i + 1; j < 8; j++){
                for(int k = j + 1; k < 9 ; k++){
                    if(isHap(boardInfo[i],boardInfo[j],boardInfo[k])){
                        HashSet<Integer> set = new HashSet<Integer>(3);
                        set.add(i+1);
                        set.add(j+1);
                        set.add(k+1);
                        hapset.add(set);
                        hapNum++;
                    }
                }
            }
        }


        //�� ���� �³� ������
        if(hapNum == 0){
            System.out.println("NONE");
        }
        else{
            for(HashSet obj : hapset){
                System.out.println(obj);
            }
        }

    }

    /*******************************/
    /********* ���� ����ϱ� *********/
    /*******************************/

    // ---------------------��Ʈ��ũ--------------------------------------
    public void geulScore() {
    	
    	if(hapset.isEmpty()){
            _score += 3;
            lblState.setText("'��' �Դϴ�. ¦¦¦!!! 3�� ȹ���ϼ̽��ϴ�!");
            lblState.setForeground(Color.BLUE);
            restartGame();
        }
        else{
            _score -= 1;
            lblState.setText("���� ���� �� �� �ִ� ��찡 ���ҽ��ϴ�.");
            lblState.setForeground(Color.RED);
        }  	
    	
    	lblScore.setText("���� : " + _score);
    }
    
    public void hapScore(String userInput) {
    	
    	int answer = 0;

        //���� 3���� �Էµ� ��찡 �ƴ� ��(���� ���� ��ȣ�� �� ���)
    	try{
            //String userInput = userinput.getText();
            answer = Integer.parseInt(userInput);
            System.out.println(answer);
            if(answer>=1000||answer<100) lblState.setText("1 ~ 9���� ���� �ٸ� ���� 3���� �Է��ϼ���"); //���ڰ� 3������ �۰ų� ���� ���
            else{
                HashSet<Integer> userAnswerSet = new HashSet<Integer>(3);
                int num1, num2, num3;
                num1 = answer/100;
                num2 = answer%100/10;
                num3 = answer%10;
                if(num1 == 0 || num2 == 0 || num3 == 0) lblState.setText("1 ~ 9���� ���� �ٸ� ���� 3���� �Է��ϼ���"); //0�� ���� ���
                else{
                    userAnswerSet.add(num1);
                    userAnswerSet.add(num2);
                    userAnswerSet.add(num3);
                }


                if(userAnswerSet.size() != 3) lblState.setText("1 ~ 9���� ���� �ٸ� ���� 3���� �Է��ϼ���");
                else{
                    boolean isBefore = false;
                    for (HashSet i : deleteHapset){
                        if(userAnswerSet.equals(i)){
                            lblState.setText("ī�� " + userAnswerSet + " ��(��) �̹� ���Խ��ϴ�.");
                            lblState.setForeground(Color.RED);
                            _score--;
                            isBefore = true;
                            break;
                        }
                    }

                    if(!isBefore){ //������ ������ ���� ���
                        boolean isAnswer = false;
                        for(HashSet i : hapset){
                            if(userAnswerSet.equals(i)){
                                deleteHapset.add(i);
                                hapset.remove(i);
                                lblState.setText("ī�� " + userAnswerSet + " ��(��) '��' �Դϴ�.");
                                lblState.setForeground(Color.BLUE);
                                _score++;
                                isAnswer = true;
                                break;
                            }
                        }
                        if(!isAnswer){
                            _score--;
                            lblState.setText("ī�� " + userAnswerSet + " ��(��) ���� �ƴմϴ�.");
                            lblState.setForeground(Color.RED);
                        }
                    }

                }
            }

        }catch (NumberFormatException ex){
            lblState.setText("���� 3���� �Է��ϼ���");
        }finally {
            userinput.setText("");
        }
        
    	lblScore.setText("���� : " + _score);
    }
    
    class renewScore implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
        	
        	 if (e.getSource() == btnGeul) {
        		 cl.sendMessage("[GEULCOMBINATION]");
        	 }
        	 else if (e.getSource() == btnHap) {
        		 String userInput = userinput.getText();
        		 cl.sendMessage("[HAPCOMBINATION]" + userInput);
        	 }
        }
    }
    // ---------------------------------------------������--------------------------------------------
    
    public void restartGame(){
        String[] option = {"�ٽ� ����","����"};
        int select = JOptionPane.showOptionDialog(this,"������ ����Ǿ����ϴ�","��������",0,JOptionPane.INFORMATION_MESSAGE,null,option,option[0]);

        if(select == 0){ //�ٽ� ����
        	cl.sendMessage("[COMBINATIONINIT]");
        }else{ //���� ������ �ý��� ����
        	main.createGameSelector();
        	main.addMainPanel();
            //�����ϱ� ���� ���� �гο� �Լ� ������ �� �� ����
        }
    }
}

