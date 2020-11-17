package combination;


import frame_panel.GameSelector;
import tool.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    private ImageIcon background;
    private JLabel back;
    private Image resizeimg;

    private Customfont makeFnt;
    Font fnt;

    public Game(GameSelector g) {
    	
    	setBounds(125, 130, 800, 540); // Game 패널 크기 지정
        setLayout(null); // 레이아웃 매니저 끄기


        /**********************************/
        /**********좌측 부분 만들기***********/
        /**********************************/

        boardInfo = new Card[9]; // 게임판 정보
        setGameBoardInfo(); //게임 초기화
        gameBoard = new GameBoardPanel(boardInfo); // 게임판 만들기
        gameBoard.setBounds(50,120,350,350); // 게임판 위치, 크기 지정
        gameBoard.setBackground(new Color(236, 191, 246)); // 게임판 배경색 지정
        add(gameBoard); // 게임판 추가



        /**********************************/
        /**********우측 부분 만들기***********/
        /**********************************/

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
        fnt = makeFnt.getCustomFont("font/고양체.ttf",Font.BOLD,24);
        lblState.setFont(fnt);
        fnt = makeFnt.getCustomFont("font/고양체.ttf",Font.PLAIN,22);
        lblScore.setFont(fnt);

        // 위치 지정
        lblState.setBounds(170,35,500,50);
        lblScore.setBounds(490,120,200,50);
        btnGeul.setBounds(480, 380, 70,30);
        btnHap.setBounds(480,420,70,30);
        userinput.setBounds(560,420, 160, 30);

        add(lblState);
        add(lblScore);
        add(btnHap);
        add(btnGeul);
        add(userinput);

        // 게임판 배경 설정
        ResizeImg img = new ResizeImg("images/bg_combination.jpg",800,540);
        resizeimg = img.getResizeImage();
        background = new ImageIcon(resizeimg);
        back = new JLabel("", background, SwingConstants.CENTER);
        back.setBounds(0, 0, 800, 540);
        add(back); // 배경 이미지 마지막에 배치 -> 가장 안쪽에 배치하기 위해

        calculateHap();

    }//constructor

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


        setGameBoardInfo(); //게임 초기화
        gameBoard = new GameBoardPanel(boardInfo);
        gameBoard.setBounds(50,120,500,500);
        add(gameBoard);
        repaint();



        calculateHap();
    }

    /*******************************/
    /******** 게임 판 만들기 *********/
    /*******************************/


    // 타일 중복 없이 게임판 만들기
    public void setGameBoardInfo(){
        for (int i = 0; i < 9; i++) {
            Card tmp = new Card();

            boolean duplicated = false;
            //중복 없는 카드판 만들기
            for(int j = 0 ; j < i; j++){
                if(tmp.getCardColor() == boardInfo[j].getCardColor()){
                    if (tmp.getShape() == boardInfo[j].getShape()) {
                        if (tmp.getBackgroundColor() == boardInfo[j].getBackgroundColor()){
                            System.out.println("same~!");
                            duplicated = true;
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

    // 합 계산하는 함수
    public void calculateHap(){
        hapset = new ArrayList<>(); //합 집합 저장 변수
        deleteHapset = new ArrayList<>(); //이미 입력된 합 집합 저장 변수
        
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
    } //calculateHap()

    
    /*******************************/
    /********* 점수 계산하기 *********/
    /*******************************/

    class renewScore implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == btnGeul){ //결 누른 경우
                if(hapset.isEmpty()){
                    _score += 3;
                    lblState.setText("'결' 입니다. 게임을 종료합니다!");
                    lblState.setForeground(new Color(31, 76, 224));
                    lblScore.setText("점수 : " + _score);

                    restartGame();
                }
                else{
                    _score -= 1;
                    lblState.setText("아직 합이 될 수 있는 경우가 남았습니다.");
                    lblState.setForeground(new Color(184, 22, 22));
                }
            }
            else if(e.getSource() == btnHap || e.getSource() == userinput){ //합을 누른 경우, enter 친 경우

                int answer = 0;

                //숫자 3개가 입력된 경우가 아닐 때(문자 등의 기호가 들어간 경우)
                try{
                    String userInput = userinput.getText();
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
                                    lblState.setForeground(new Color(184, 22, 22));
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
                                        lblState.setForeground(new Color(31, 76, 224));
                                        _score++;
                                        isAnswer = true;
                                        break;
                                    }
                                }
                                if(!isAnswer){
                                    _score--;
                                    lblState.setText("카드 " + userAnswerSet + " 은(는) 합이 아닙니다.");
                                    lblState.setForeground(new Color(184, 22, 22));
                                }
                            }

                        }
                    }

                }catch (NumberFormatException ex){
                    lblState.setForeground(Color.black);
                    lblState.setText("숫자 3개를 입력하세요");
                }finally {
                    userinput.setText("");
                }
            }
            lblScore.setText("점수 : " + _score);
        }
    }

    public void restartGame(){
        String[] option = {"다시 시작","종료"};
        int select = JOptionPane.showOptionDialog(this,"게임이 종료되었습니다","게임종료",JOptionPane.DEFAULT_OPTION ,JOptionPane.INFORMATION_MESSAGE,null,option,option[0]);

        if(select == 0){ //다시 시작
            init();
        }else{ //종료 누르면 시스템 종료
            setVisible(false);
            //종료하기 위한 상위 패널에 함수 만들어야 할 거 같음
        }
    }
}

