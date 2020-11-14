

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

    private renewScore update; //actionListener class

    Font fnt = new Font("배달의민족 주아",Font.BOLD,20);

    public Game() {
    	
    	setBounds(275, 40, 500, 700);
        setPreferredSize(new Dimension(500,700));
        setBackground(new Color(223, 255, 188));
        setLayout(null);

        btnHap = new JButton("합");
        btnGeul = new JButton("결");
        userinput = new JTextField(5);
        lblScore = new JLabel("점수 : 0");
        lblState = new JLabel("게임을 시작합니다.");

        update = new renewScore();
        btnHap.addActionListener(update);
        btnGeul.addActionListener(update);

        lblScore.setFont(fnt);
        lblState.setFont(fnt);

        lblScore.setBounds(10,0,200,50);
        lblState.setBounds(10,50,500,50);
        btnGeul.setBounds(50, 635, 70,30);
        btnHap.setBounds(180,635,70,30);
        userinput.setBounds(260,635, 160, 30);

        add(lblScore);
        add(lblState);
        add(btnHap);
        add(btnGeul);
        add(userinput);
    }//constructor

    private Card[] boardInfo;
    private ArrayList<HashSet> hapset;
    private ArrayList <HashSet> deleteHapset;

    public void init(){

        _score = 0;
        lblScore.setText("점수 : " + _score);
        lblState.setText("게임을 시작합니다.");

        hapset = new ArrayList<>();
        deleteHapset = new ArrayList<>();

        boardInfo = new Card[9];
        setGameBoardInfo(); //게임 초기화
        GameBoardPanel gameBoard = new GameBoardPanel(boardInfo);
        gameBoard.setBounds(0,100,500,500);
        add(gameBoard);

        calculateHap();
    }

    /*******************************/
    /******** 게임 판 만들기 *********/
    /*******************************/


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

    class renewScore implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == btnGeul){ //결 누른 경우
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
            }
            else if(e.getSource() == btnHap){ //합을 누른 경우

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
            }
            lblScore.setText("점수 : " + _score);
        }
    }

    public void restartGame(){
        String[] option = {"다시 시작","종료"};
        int select = JOptionPane.showOptionDialog(this,"게임이 종료되었습니다","게임종료",0,JOptionPane.INFORMATION_MESSAGE,null,option,option[0]);

        if(select == 0){ //다시 시작
            init();
        }else{ //종료 누르면 시스템 종료
            System.out.println("종료");
            //종료하기 위한 상위 패널에 함수 만들어야 할 거 같음
        }
    }
}

