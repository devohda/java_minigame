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

    //배경 이미지 만들기
    private ImageIcon background;
    private JLabel back;
    private Image resizeimg;

    //폰트 지정
    private Customfont makeFnt;
    Font fnt;

    //player
    private Player[] players;
    private int _playerNum;
    
    //종료 시 게임선택화면 가기위해 이용할 객체
    private MainPanel main;
    
    // 네트워크 랜덤 카드 생성 인자
    private int[][] indexCombi = new int[9][3];
    // 네트워크 구현위한 버튼
 	private JButton btnReset;
 	
 	// 채팅구현
 	private JTextArea chatArea;
 	private JScrollPane scrollPane;
 	private JTextField chat;
 	private String nickName; 
 	    
 	private String name; 
 	private ClientGame cl;

    public Game(ClientGame c, GameSelector g, MainPanel m) {
    	
    	main = m; //종료 시 게임선택화면 가기위해 이용할 객체
    	
        _playerNum = g.getPeopleNum();
        System.out.println(_playerNum);
    	
    	cl = c;
    	
    	setBounds(125, 130, 800, 520); // Game 패널 크기 지정
        setLayout(null);

        _score = 0; // 점수 초기화
        lblScore = new JLabel("점수 : 0"); // lblScore 초기화
        lblState = new JLabel("게임을 시작합니다."); // lblState 초기화
        lblState.setHorizontalAlignment(JLabel.CENTER); //글자 가운데 정렬
        lblState.setForeground(Color.black);

        btnHap = new JButton("합");
        btnGeul = new JButton("결");
        userinput = new JTextField(5);

        //add action listener
        update = new renewScore();
        btnHap.addActionListener(update);
        btnGeul.addActionListener(update);
        userinput.addActionListener(update);

        /************* 폰트 지정 **************/
        makeFnt = new Customfont();
        fnt = makeFnt.getCustomFont("font/고양체.ttf", Font.BOLD, 24);
        lblState.setFont(fnt);
        fnt = makeFnt.getCustomFont("font/고양체.ttf", Font.PLAIN, 22);
        lblScore.setFont(fnt);

        // 위치 지정
        lblState.setBounds(170, 35, 500, 50);
        lblScore.setBounds(490, 120, 200, 50);
        btnGeul.setBounds(480, 380, 70, 30);
        btnHap.setBounds(480, 420, 70, 30);
        userinput.setBounds(560, 420, 160, 30);

        // 네트워크 구현위한 버튼
     	btnReset = new JButton("초기화");
     	btnReset.setBounds(650, 35, 70, 30);
     	add(btnReset);
     	btnReset.addActionListener(new ResetListener());
        
        // 채팅창 구현		
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
        
        // 게임판 배경 설정
        ResizeImg img = new ResizeImg("images/bg_combination.jpg", 800, 540);
        resizeimg = img.getResizeImage();
        background = new ImageIcon(resizeimg);
        back = new JLabel("", background, SwingConstants.CENTER);
        back.setBounds(0, 0, 800, 540);
        add(back); // 배경 이미지 마지막에 배치 -> 가장 안쪽에 배치하기 위해
        
        cl.setGuiCombination(this);
		name = cl.getNickname();
		
		init();
        
    }//constructor
    
    // ----------------------네트워크--------------------------------
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
	// ----------------------네트워크--------------------------------

    private Card[] boardInfo;
    private ArrayList<HashSet> hapset;
    private ArrayList <HashSet> deleteHapset;

    public void init(){

        _score = 0;
        lblScore.setText("점수 : " + _score);
        lblState.setText("게임을 시작합니다.");
        lblState.setForeground(Color.black);

        hapset = new ArrayList<>();
        deleteHapset = new ArrayList<>();

        boardInfo = new Card[9];
        System.out.println("1빠");
        indexCombi = cl.getCombiIndex();
        System.out.println("2빠");
        setGameBoardInfo(indexCombi); //게임 초기화 --> 이것을 서버에서 구현해서 클라이언트에 전해줄려했으나 인덱스만 서버에서 클라이언트에 주는걸로...
        System.out.println("4빠");
        
        System.out.println("5빠");
        
        gameBoard = new GameBoardPanel(boardInfo);
        gameBoard.setBounds(50, 120, 500, 500);
        add(gameBoard);
        repaint();

        System.out.println("6빠");
        calculateHap();
        System.out.println("7빠");
    }

    /*******************************/
    /******** 게임 판 만들기 *********/
    /*******************************/


    public void setGameBoardInfo(int[][] index){
    	System.out.println("3빠");
        for (int i = 0; i < 9; i++) {
            Card tmp = new Card(index[i]);

            boolean duplicated = false;
            //중복 없는 카드판 만들기
            for(int j = 0 ; j < i; j++){
                if(tmp.getCardColor() == boardInfo[j].getCardColor()){
                    if (tmp.getShape() == boardInfo[j].getShape()) {
                        if (tmp.getBackgroundColor() == boardInfo[j].getBackgroundColor()){
                            System.out.println("11same~!");
                            //duplicated = true; --> 이미 해당 알고리즘을 서버에서 수행하고 온 인덱스들이므로 생략 
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
    /******* '합' 집합 구하기 ********/
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

        //카드 세 개의 조합이 합인 것 찾기
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


        //합 개수 맞나 검증용
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
    /********* 점수 계산하기 *********/
    /*******************************/

    // ---------------------네트워크--------------------------------------
    public void geulScore() {
    	
    	if(hapset.isEmpty()){
            _score += 3;
            lblState.setText("'결' 입니다. 짝짝짝!!! 3점 획득하셨습니다!");
            lblState.setForeground(Color.BLUE);
            restartGame();
        }
        else{
            _score -= 1;
            lblState.setText("아직 합이 될 수 있는 경우가 남았습니다.");
            lblState.setForeground(Color.RED);
        }  	
    	
    	lblScore.setText("점수 : " + _score);
    }
    
    public void hapScore(String userInput) {
    	
    	int answer = 0;

        //숫자 3개가 입력된 경우가 아닐 때(문자 등의 기호가 들어간 경우)
    	try{
            //String userInput = userinput.getText();
            answer = Integer.parseInt(userInput);
            System.out.println(answer);
            if(answer>=1000||answer<100) lblState.setText("1 ~ 9사이 서로 다른 숫자 3개를 입력하세요"); //숫자가 3개보다 작거나 많을 경우
            else{
                HashSet<Integer> userAnswerSet = new HashSet<Integer>(3);
                int num1, num2, num3;
                num1 = answer/100;
                num2 = answer%100/10;
                num3 = answer%10;
                if(num1 == 0 || num2 == 0 || num3 == 0) lblState.setText("1 ~ 9사이 서로 다른 숫자 3개를 입력하세요"); //0이 섞인 경우
                else{
                    userAnswerSet.add(num1);
                    userAnswerSet.add(num2);
                    userAnswerSet.add(num3);
                }


                if(userAnswerSet.size() != 3) lblState.setText("1 ~ 9사이 서로 다른 숫자 3개를 입력하세요");
                else{
                    boolean isBefore = false;
                    for (HashSet i : deleteHapset){
                        if(userAnswerSet.equals(i)){
                            lblState.setText("카드 " + userAnswerSet + " 은(는) 이미 나왔습니다.");
                            lblState.setForeground(Color.RED);
                            _score--;
                            isBefore = true;
                            break;
                        }
                    }

                    if(!isBefore){ //이전에 나오지 않은 경우
                        boolean isAnswer = false;
                        for(HashSet i : hapset){
                            if(userAnswerSet.equals(i)){
                                deleteHapset.add(i);
                                hapset.remove(i);
                                lblState.setText("카드 " + userAnswerSet + " 은(는) '합' 입니다.");
                                lblState.setForeground(Color.BLUE);
                                _score++;
                                isAnswer = true;
                                break;
                            }
                        }
                        if(!isAnswer){
                            _score--;
                            lblState.setText("카드 " + userAnswerSet + " 은(는) 합이 아닙니다.");
                            lblState.setForeground(Color.RED);
                        }
                    }

                }
            }

        }catch (NumberFormatException ex){
            lblState.setText("숫자 3개를 입력하세요");
        }finally {
            userinput.setText("");
        }
        
    	lblScore.setText("점수 : " + _score);
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
    // ---------------------------------------------여까지--------------------------------------------
    
    public void restartGame(){
        String[] option = {"다시 시작","종료"};
        int select = JOptionPane.showOptionDialog(this,"게임이 종료되었습니다","게임종료",0,JOptionPane.INFORMATION_MESSAGE,null,option,option[0]);

        if(select == 0){ //다시 시작
        	cl.sendMessage("[COMBINATIONINIT]");
        }else{ //종료 누르면 시스템 종료
        	main.createGameSelector();
        	main.addMainPanel();
            //종료하기 위한 상위 패널에 함수 만들어야 할 거 같음
        }
    }
}

