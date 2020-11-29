package combination;


import tool.*;
import frame_panel.*;
import socket.ClientGame;

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
    private JLabel lblState;
    private int _score;
    private GameBoardPanel gameBoard;

    private renewScore update; //actionListener class
    private Player nowPlayer;

    //배경 이미지 만들기
    private ImageIcon background;
    private JLabel back;
    private Image resizeimg;

    //폰트 지정
    private Customfont makeFnt;
    Font fnt;

    //player
    private JTextArea scoreArea;
    private JLabel scoreBoard;
    private String scoreString;
    private int turn;
    private Player[] players;
    private int _playerNum;
    private int turntmp;
    
    //종료 시 게임선택화면 가기위해 이용할 객체
    private MainPanel main;
    
    // 네트워크 랜덤 카드 생성 인자
    private int[][] indexCombi = new int[9][3];
    // 초기화 버튼
 	private JButton btnReset;
 	
 	// 채팅구현
 	private JTextArea chatArea;
 	private JScrollPane scrollPane;
 	private JTextField chat;
 	private String nickName; 
 	    
 	private String name; 
 	private ClientGame cl;
 	private GameSelector gs;

    public Game(ClientGame c, GameSelector g, MainPanel m) {
      	
    	main = m; //종료 시 게임선택화면 가기위해 이용할 객체

        makeFnt = new Customfont();
    	
        gs = g;
    	cl = c;
    	
    	setBounds(125, 130, 800, 520); // Game 패널 크기 지정
        setLayout(null); // 레이아웃 매니저 끄기
        
        /**********************************/
        /*****인원 수 받아서 player 생성******/
        /**********************************/
        _playerNum = g.getPeopleNum();
        players = new Player[_playerNum];

        for (int i = 0; i < _playerNum; i++) { // 지금현재 4명임의 수  // >> 수정 personNum 만큼의 숫자로
            players[i] = new Player();
            players[i].setScore(0); // 플레이어 수 점수 전부 0점초기화
            players[i].setOrder(i + 1);
        }
        
        /**********************************/
        /************점수판 초기화***********/
        /**********************************/

        turn = 1;
        scoreString = "현재 순서는 " + turn + "번 플레이어입니다.\n\n";

        for (int i = 0; i < _playerNum; i++) {// >> 수정 personNum 만큼의 숫자로
            scoreString += players[i].getOrder() + "등 [Player " + (i + 1) + "]" + " : " + players[i].getScore() + "\n";
        }

        scoreArea = new JTextArea(scoreString) {
            {
                setOpaque(false);
            }
        };
        scoreArea.setEditable(false); // 점수판 편집 불가
        scoreArea.setForeground(Color.WHITE);
        scoreArea.setBounds(490, 120, 220, 250);
        fnt = makeFnt.getCustomFont("font/고양체.ttf", Font.BOLD, 16);
        scoreArea.setFont(fnt);
        add(scoreArea);

        _score = 0; // 점수 초기화
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
        fnt = makeFnt.getCustomFont("font/고양체.ttf", Font.BOLD, 24);
        lblState.setFont(fnt);

        // 위치 지정
        lblState.setBounds(170, 35, 500, 50);
        btnGeul.setBounds(480, 400, 70, 30);
        btnHap.setBounds(480, 440, 70, 30);
        userinput.setBounds(560, 420, 160, 30);

        // 초기화 버튼
     	btnReset = new JButton("초기화");
     	btnReset.setBounds(650, 35, 70, 30);
     	add(btnReset);
     	btnReset.addActionListener(new ResetListener());
        
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
        lblState.setText("게임을 시작합니다.");
        lblState.setForeground(Color.black);

        hapset = new ArrayList<>();
        deleteHapset = new ArrayList<>();

        boardInfo = new Card[9]; // 게임판 정보
        indexCombi = cl.getCombiIndex();  
        setGameBoardInfo(indexCombi); //게임 초기화
        
        gameBoard = new GameBoardPanel(boardInfo); // 게임판 만들기
        gameBoard.setBounds(50, 120, 350, 350); // 게임판 위치, 크기 지정

        add(gameBoard); // 게임판 추가
        repaint();

        calculateHap();
        initGame(); //사용자 점수 초기화
    }
    
    public void initGame() {
        turn = 1;

        for (int i = 0; i < _playerNum; i++) { // 지금현재 4명임의 수  // >> 수정 personNum 만큼의 숫자로
            players[i].setScore(0); // 플레이어 수 점수 전부 0점초기화
            players[i].setOrder(i + 1);
        }

        scoreString = "현재 순서는 " + turn + "번 플레이어입니다.\n\n";

        for (int i = 0; i < _playerNum; i++) {// >> 수정 personNum 만큼의 숫자로
            scoreString += players[i].getOrder() + "등 [Player " + (i + 1) + "]" + " : " + players[i].getScore() + "\n";
        }
        scoreArea.setText(scoreString);

    }

    /*******************************/
    /******** 게임 판 만들기 *********/
    /*******************************/


    public void setGameBoardInfo(int[][] index){
        for (int i = 0; i < 9; i++) {
            Card tmp = new Card(index[i]);

            boolean duplicated = false;
            //중복 없는 카드판 만들기
            for(int j = 0 ; j < i; j++){
                if(tmp.getCardColor() == boardInfo[j].getCardColor()){
                    if (tmp.getShape() == boardInfo[j].getShape()) {
                        if (tmp.getBackgroundColor() == boardInfo[j].getBackgroundColor()){
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
    /**** player 순위 계산하기 *******/
    /*******************************/

    public void sortPlayer() {

        scoreString = "현재 순서는 " + turn + "번 플레이어입니다.\n\n";

        int emp;
        int j;
        int[] arr;
        arr = new int[_playerNum]; // >> 수정 personNum 만큼의 숫자로

        for (int i = 0; i < _playerNum; i++) {// >> 수정 personNum 만큼의 숫자로
            arr[i] = players[i].getScore();
        }

        // 삽입 정렬을 이용한 sorting
        for (int i = 1; i < _playerNum; i++) {// >> 수정 personNum 만큼의 숫자로
            emp = arr[i];
            j = i - 1;
            while (j >= 0 && (arr[j] > emp)) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = emp;
        }

        for (int i = 0; i < _playerNum; i++) {// >> 수정 personNum 만큼의 숫자로
            for (j = 0; j < _playerNum; j++) {// >> 수정 personNum 만큼의 숫자로
                if (arr[j] == players[i].getScore()) players[i].setOrder(_playerNum - j);
            }
        }

        for (int i = 0; i < _playerNum; i++) {// >> 수정 personNum 만큼의 숫자로
            scoreString += players[i].getOrder() + "등 [Player " + (i + 1) + "]" + " : " + players[i].getScore() + "\n";
        }

        scoreArea.setText(scoreString);
    }
    

    // ---------------------네트워크--------------------------------------
    public void geulScore(int t) {
    	
    	nowPlayer = players[t - 1];
    	
    	if(hapset.isEmpty()){
    		nowPlayer.setScore(nowPlayer.getScore() + 3);
            lblState.setText("'결' 입니다. 짝짝짝!!! 3점 획득하셨습니다!");
            lblState.setForeground(new Color(31, 76, 224));
            sortPlayer();
            restartGame();
        }
        else{
        	nowPlayer.setScore(nowPlayer.getScore() - 1);
            lblState.setText("아직 합이 될 수 있는 경우가 남았습니다.");
            lblState.setForeground(new Color(184, 22, 22));
        }  	
    	
    	if (turn == _playerNum) turn = 1;
        else turn++;
        sortPlayer();
    	
    }
    
    public void hapScore(String userInput, int t) {
    	
    	nowPlayer = players[t - 1];
    	int answer = 0;

        //숫자 3개가 입력된 경우가 아닐 때(문자 등의 기호가 들어간 경우)
    	try{
            answer = Integer.parseInt(userInput);
            System.out.println(answer);
            if(answer>=1000||answer<100) {
            	lblState.setText("1 ~ 9사이 서로 다른 숫자 3개를 입력하세요"); //숫자가 3개보다 작거나 많을 경우
            	turn--;
            }
            else{
                HashSet<Integer> userAnswerSet = new HashSet<Integer>(3);
                int num1, num2, num3;
                num1 = answer/100;
                num2 = answer%100/10;
                num3 = answer%10;
                if(num1 == 0 || num2 == 0 || num3 == 0) {
                	lblState.setText("1 ~ 9사이 서로 다른 숫자 3개를 입력하세요"); //0이 섞인 경우
                	turn--;
                }
                else{
                    userAnswerSet.add(num1);
                    userAnswerSet.add(num2);
                    userAnswerSet.add(num3);
                }


                if(userAnswerSet.size() != 3) {
                	lblState.setText("1 ~ 9사이 서로 다른 숫자 3개를 입력하세요");
                	turn--;
                }
                else{
                    boolean isBefore = false;
                    for (HashSet i : deleteHapset){
                        if(userAnswerSet.equals(i)){
                            lblState.setText("카드 " + userAnswerSet + " 은(는) 이미 나왔습니다.");
                            lblState.setForeground(new Color(184, 22, 22));
                            nowPlayer.setScore(nowPlayer.getScore() - 1);
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
                                lblState.setForeground(new Color(31, 76, 224));
                                nowPlayer.setScore(nowPlayer.getScore() + 1);
                                isAnswer = true;
                                break;
                            }
                        }
                        if(!isAnswer){
                        	nowPlayer.setScore(nowPlayer.getScore() - 1);
                            lblState.setText("카드 " + userAnswerSet + " 은(는) 합이 아닙니다.");
                            lblState.setForeground(Color.RED);
                        }
                    }

                }
            }
            
            System.out.println(turn);
        }catch (NumberFormatException ex){
        	lblState.setForeground(Color.black);
            lblState.setText("숫자 3개를 입력하세요");
            turn--;
        }finally {
            userinput.setText("");
        }
    	
    	if (turn == _playerNum) turn = 1;
        else turn++;
        sortPlayer();
    	
    }
    
    /*******************************/
    /********* 점수 계산하기 *********/
    /*******************************/
    
    class renewScore implements ActionListener {
    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	
        	 if (e.getSource() == btnGeul) { // 결 버튼 누른경우
        		 cl.sendMessage("[GEULCOMBINATION]" + turn); // 서버에 해당 플레이어 순서정보와 결 처리 메세지 전달
        	 }
        	 else if (e.getSource() == btnHap || e.getSource() == userinput) { //합을 누른 경우, enter 친 경우
        		 String userInput = userinput.getText();
        		 cl.sendMessage("[HAPCOMBINATION]" + turn + userInput); // 서버에 플레이어 순서정보와 합 처리 메세지 전달
        	 }
        }
    }
    // ---------------------------------------------여까지--------------------------------------------
    
    public void restartGame(){
        String[] option = {"계속 하기","종료"};
        int select = JOptionPane.showOptionDialog(this,"게임이 종료되었습니다","게임종료",0,JOptionPane.INFORMATION_MESSAGE,null,option,option[0]);

        if(select == 0){ //다시 시작
        	gs.offIntro();
        	main.offMainIntro();
        }else{ //종료 누르면 시스템 종료
        	main.createGameSelector();
        	main.addMainPanel();
        	main.offMainIntro();
        	gs.offIntro();
        	gs.setgameNumZero();
            //종료하기 위한 상위 패널에 함수 만들어야 할 거 같음
        }
    }
}

